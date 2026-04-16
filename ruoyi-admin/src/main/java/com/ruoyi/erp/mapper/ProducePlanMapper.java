package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.ProducePlan;

/**
 * 生产计划Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface ProducePlanMapper {
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
     * 删除生产计划
     *
     * @param id 生产计划主键
     * @return 结果
     */
    int deleteProducePlanById(Long id);

    /**
     * 批量删除生产计划
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteProducePlanByIds(Long[] ids);

    /**
     * 批量新增生产计划批量
     *
     * @param list 生产计划
     * @return 结果
     */
    int insertProducePlanBatch(List<ProducePlan> list);

    /**
     * 统计总数
     *
     * @return 总数
     */
    int countAll();

    /**
     * 按状态统计
     *
     * @param status 状态
     * @return 数量
     */
    int countByStatus(String status);

    /**
     * 按状态列表统计
     *
     * @param statusList 状态列表
     * @return 数量
     */
    int countByStatusIn(List<String> statusList);

    /**
     * 统计指定日期完成的数量
     *
     * @param date 日期
     * @return 数量
     */
    int countCompletedByDate(java.util.Date date);

    /**
     * 统计时间段内完成的数量
     *
     * @param start 开始日期
     * @param end 结束日期
     * @return 数量
     */
    int countCompletedBetween(java.util.Date start, java.util.Date end);

    /**
     * 统计时间段内完成总数
     *
     * @param start 开始日期
     * @param end 结束日期
     * @return 数量
     */
    int countCompletedInPeriod(java.util.Date start, java.util.Date end);

    /**
     * 统计准时完成数量
     *
     * @return 数量
     */
    int countOnTimeCompleted();

    /**
     * 查询甘特图任务列表
     *
     * @return 甘特图任务列表
     */
    java.util.List<com.ruoyi.erp.domain.vo.GanttTaskVO> selectGanttTasks();

    /**
     * 更新计划开始和结束日期
     *
     * @param id 计划ID
     * @param startDate 开始日期
     * @param dueDate 截止日期
     * @return 结果
     */
    int updatePlanDates(Long id, java.util.Date startDate, java.util.Date dueDate);
}
