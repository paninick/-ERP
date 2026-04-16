package com.ruoyi.erp.service.impl;

import com.ruoyi.erp.domain.PieceWage;
import com.ruoyi.erp.mapper.PieceWageMapper;
import com.ruoyi.erp.service.IPieceWageService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 计件工资汇总Service业务层处理
 *
 * @author zhangmingjian
 */
@Service
public class PieceWageServiceImpl implements IPieceWageService {

    @Autowired
    private PieceWageMapper pieceWageMapper;

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
}
