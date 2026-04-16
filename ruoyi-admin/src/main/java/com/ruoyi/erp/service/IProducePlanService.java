package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.ProducePlan;

/**
 * 生产计划Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface IProducePlanService {
    /**
     * 查询生产计划
     *
     * @param id 生产计划主键
     * @return 生产计划
     */
    ProducePlan selectProducePlanById(Long id);

    /**
     * 查询生产计划列表
     *
     * @param producePlan 生产计划
     * @return 生产计划集合
     */
    List<ProducePlan> selectProducePlanList(ProducePlan producePlan);

    /**
     * 新增生产计划
     *
     * @param producePlan 生产计划
     * @return 结果
     */
    int insertProducePlan(ProducePlan producePlan);

    /**
     * 修改生产计划
     *
     * @param producePlan 生产计划
     * @return 结果
     */
    int updateProducePlan(ProducePlan producePlan);

    /**
     * 批量删除生产计划
     *
     * @param ids 需要删除的生产计划主键集合
     * @return 结果
     */
    int deleteProducePlanByIds(Long[] ids);

    /**
     * 删除生产计划信息
     *
     * @param id 生产计划主键
     * @return 结果
     */
    int deleteProducePlanById(Long id);

    /**
     * 新增生产计划批量
     *
     * @param list 生产计划
     * @return 结果
     */
    int insertProducePlanBatch(List<ProducePlan> list);

    /**
     * 查询甘特图任务列表
     *
     * @return 甘特图任务列表
     */
    List<com.ruoyi.erp.domain.vo.GanttTaskVO> selectGanttTasks();

    /**
     * 更新计划日期
     *
     * @param id 计划ID
     * @param startDate 开始日期
     * @param dueDate 截止日期
     * @return 结果
     */
    int updatePlanDates(Long id, java.util.Date startDate, java.util.Date dueDate);
}
