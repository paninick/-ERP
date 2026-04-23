package com.ruoyi.erp.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.ProducePlanMapper;
import com.ruoyi.erp.domain.ProducePlan;
import com.ruoyi.erp.service.IProducePlanService;
import com.ruoyi.erp.utils.BillNoGenerator;

/**
 * 生产计划Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@Service
public class ProducePlanServiceImpl implements IProducePlanService {
    @Autowired
    private ProducePlanMapper producePlanMapper;

    @Autowired
    private BillNoGenerator billNoGenerator;

    /**
     * 查询生产计划
     *
     * @param id 生产计划主键
     * @return 生产计划
     */
    @Override
    public ProducePlan selectProducePlanById(Long id) {
        return producePlanMapper.selectProducePlanById(id);
    }

    /**
     * 查询生产计划列表
     *
     * @param producePlan 生产计划
     * @return 生产计划
     */
    @Override
    public List<ProducePlan> selectProducePlanList(ProducePlan producePlan) {
        return producePlanMapper.selectProducePlanList(producePlan);
    }

    /**
     * 新增生产计划
     *
     * @param producePlan 生产计划
     * @return 结果
     */
    @Override
    public int insertProducePlan(ProducePlan producePlan) {
        if (producePlan.getPlanNo() == null || producePlan.getPlanNo().isEmpty()) {
            producePlan.setPlanNo(billNoGenerator.generate("PP"));
        }
        producePlan.setCreateBy(SecurityUtils.getUserId().toString());
        producePlan.setCreateTime(DateUtils.getNowDate());
        return producePlanMapper.insertProducePlan(producePlan);
    }

    /**
     * 修改生产计划
     *
     * @param producePlan 生产计划
     * @return 结果
     */
    @Override
    public int updateProducePlan(ProducePlan producePlan) {
        producePlan.setUpdateTime(DateUtils.getNowDate());
        return producePlanMapper.updateProducePlan(producePlan);
    }

    /**
     * 批量删除生产计划
     *
     * @param ids 需要删除的生产计划主键
     * @return 结果
     */
    @Override
    public int deleteProducePlanByIds(Long[] ids) {
        return producePlanMapper.deleteProducePlanByIds(ids);
    }

    /**
     * 删除生产计划信息
     *
     * @param id 生产计划主键
     * @return 结果
     */
    @Override
    public int deleteProducePlanById(Long id) {
        return producePlanMapper.deleteProducePlanById(id);
    }

    /**
     * 批量新增生产计划
     *
     * @param list 生产计划
     * @return 结果
     */
    @Override
    public int insertProducePlanBatch(List<ProducePlan> list) {
        return producePlanMapper.insertProducePlanBatch(list);
    }

    @Override
    public List<com.ruoyi.erp.domain.vo.GanttTaskVO> selectGanttTasks() {
        return producePlanMapper.selectGanttTasks();
    }

    @Override
    public int updatePlanDates(Long id, java.util.Date startDate, java.util.Date dueDate) {
        return producePlanMapper.updatePlanDates(id, startDate, dueDate);
    }

    @Override
    public boolean reschedule(Long id, String newStartDate, String newDueDate) {
        ProducePlan plan = producePlanMapper.selectProducePlanById(id);
        if (plan == null) return false;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (newStartDate != null && !newStartDate.isEmpty()) {
            plan.setStartDate(java.sql.Date.valueOf(LocalDate.parse(newStartDate, fmt)));
        }
        if (newDueDate != null && !newDueDate.isEmpty()) {
            plan.setDueDate(java.sql.Date.valueOf(LocalDate.parse(newDueDate, fmt)));
        }
        plan.setPlanStatus("1"); // 重置为待生产
        plan.setUpdateBy(SecurityUtils.getUsername());
        plan.setUpdateTime(DateUtils.getNowDate());
        return producePlanMapper.updateProducePlan(plan) > 0;
    }

    @Override
    public int batchDetectConflicts() {
        List<ProducePlan> list = producePlanMapper.selectProducePlanList(new ProducePlan());
        int count = 0;
        Date today = DateUtils.parseDate(DateUtils.getDate());
        for (ProducePlan plan : list) {
            if ("9".equals(plan.getPlanStatus()) || "10".equals(plan.getPlanStatus())) {
                continue;
            }
            if (plan.getDueDate() != null && plan.getDueDate().before(today)) {
                count++;
            }
        }
        return count;
    }
}
