package com.ruoyi.erp.service.impl;

import com.ruoyi.erp.domain.PieceWage;
import com.ruoyi.erp.domain.PieceWageDetail;
import com.ruoyi.erp.mapper.PieceWageMapper;
import com.ruoyi.erp.mapper.PieceWageDetailMapper;
import com.ruoyi.erp.service.IPieceWageService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 计件工资汇总Service业务层处理
 *
 * @author zhangmingjian
 */
@Service
public class PieceWageServiceImpl implements IPieceWageService {

    @Autowired
    private PieceWageMapper pieceWageMapper;

    @Autowired
    private PieceWageDetailMapper pieceWageDetailMapper;

    /**
     * 查询计件工资汇总
     *
     * @param id 计件工资汇总ID
     * @return 计件工资汇总
     */
    @Override
    public PieceWage selectPieceWageById(Long id) {
        return pieceWageMapper.selectPieceWageById(id);
    }

    /**
     * 查询计件工资汇总列表
     *
     * @param pieceWage 计件工资汇总
     * @return 计件工资汇总
     */
    @Override
    public List<PieceWage> selectPieceWageList(PieceWage pieceWage) {
        return pieceWageMapper.selectPieceWageList(pieceWage);
    }

    /**
     * 新增计件工资汇总
     *
     * @param pieceWage 计件工资汇总
     * @return 结果
     */
    @Override
    public int insertPieceWage(PieceWage pieceWage) {
        pieceWage.setCreateBy(SecurityUtils.getUsername());
        pieceWage.setCreateTime(DateUtils.getNowDate());
        pieceWage.setUpdateBy(SecurityUtils.getUsername());
        pieceWage.setUpdateTime(DateUtils.getNowDate());
        return pieceWageMapper.insertPieceWage(pieceWage);
    }

    /**
     * 修改计件工资汇总
     *
     * @param pieceWage 计件工资汇总
     * @return 结果
     */
    @Override
    public int updatePieceWage(PieceWage pieceWage) {
        pieceWage.setUpdateBy(SecurityUtils.getUsername());
        pieceWage.setUpdateTime(DateUtils.getNowDate());
        return pieceWageMapper.updatePieceWage(pieceWage);
    }

    /**
     * 批量删除计件工资汇总
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePieceWageByIds(Long[] ids) {
        return pieceWageMapper.deletePieceWageByIds(ids);
    }

    /**
     * 删除计件工资汇总信息
     *
     * @param id 计件工资汇总ID
     * @return 结果
     */
    @Override
    public int deletePieceWageById(Long id) {
        return pieceWageMapper.deletePieceWageById(id);
    }

    @Override
    public int countByEmployeeAndMonth(Long employeeId, String wageMonth) {
        return pieceWageMapper.countByEmployeeAndMonth(employeeId, wageMonth);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int autoGenerateWageByMonth(String wageMonth) {
        List<Map<String, Object>> summaryRows = pieceWageMapper.selectEventSummaryByMonth(wageMonth);
        if (summaryRows == null || summaryRows.isEmpty()) {
            return 0;
        }
        // 按 employee_id 分组
        Map<Long, List<Map<String, Object>>> byEmployee = summaryRows.stream()
            .collect(Collectors.groupingBy(r -> ((Number) r.get("employee_id")).longValue()));

        int created = 0;
        for (Map.Entry<Long, List<Map<String, Object>>> entry : byEmployee.entrySet()) {
            Long employeeId = entry.getKey();
            // 幂等：同月同员工已有工资单则跳过
            if (countByEmployeeAndMonth(employeeId, wageMonth) > 0) continue;

            List<Map<String, Object>> details = entry.getValue();
            BigDecimal totalShould = details.stream()
                .map(r -> r.get("should_wage") == null ? BigDecimal.ZERO
                         : new BigDecimal(r.get("should_wage").toString()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            int totalOkQty = details.stream()
                .mapToInt(r -> r.get("total_ok_qty") == null ? 0
                              : ((Number) r.get("total_ok_qty")).intValue())
                .sum();

            // 1. 插主表
            PieceWage wage = new PieceWage();
            wage.setEmployeeId(employeeId);
            wage.setEmployeeName((String) details.get(0).get("employee_name"));
            wage.setWageMonth(wageMonth);
            wage.setTotalOkQty(totalOkQty);
            wage.setTotalProcessCount(details.size());
            wage.setTotalDefectQty(details.stream()
                .mapToInt(r -> r.get("total_defect_qty") == null ? 0 : ((Number) r.get("total_defect_qty")).intValue())
                .sum());
            wage.setShouldWage(totalShould);
            wage.setActualWage(totalShould);
            wage.setStatus("0");
            wage.setRemark("AUTO_GENERATED_FROM_PRODUCE_REPORT_LOG");
            wage.setCreateBy("system");
            wage.setCreateTime(DateUtils.getNowDate());
            pieceWageMapper.insertPieceWage(wage);

            // 2. 插明细
            for (Map<String, Object> row : details) {
                PieceWageDetail detail = new PieceWageDetail();
                detail.setWageId(wage.getId());
                detail.setEmployeeId(employeeId);
                detail.setProcessId(((Number) row.get("process_id")).longValue());
                detail.setProcessName((String) row.get("process_name"));
                detail.setJobId(row.get("job_id") == null ? null : ((Number) row.get("job_id")).longValue());
                detail.setOkQty(row.get("total_ok_qty") == null ? 0
                                : ((Number) row.get("total_ok_qty")).intValue());
                detail.setDefectQty(row.get("total_defect_qty") == null ? 0
                                    : ((Number) row.get("total_defect_qty")).intValue());
                detail.setProcessPrice(row.get("unit_price") == null ? BigDecimal.ZERO
                                       : new BigDecimal(row.get("unit_price").toString()));
                BigDecimal sw = row.get("should_wage") == null ? BigDecimal.ZERO
                               : new BigDecimal(row.get("should_wage").toString());
                detail.setShouldWage(sw);
                detail.setActualWage(sw);
                detail.setRemark("AUTO_GENERATED_FROM_PRODUCE_REPORT_LOG");
                detail.setCreateBy("system");
                detail.setCreateTime(DateUtils.getNowDate());
                pieceWageDetailMapper.insertPieceWageDetail(detail);
            }
            created++;
        }
        return created;
    }
}
