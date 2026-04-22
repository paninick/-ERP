package com.ruoyi.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.demo.domain.DemoSchedule;
import java.math.BigDecimal;
import java.util.List;

/**
 * 生产排程管理Service接口
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
public interface IDemoScheduleService extends IService<DemoSchedule> {
    /**
     * 查询生产排程列表（分页）
     * 
     * @param demoSchedule 查询条件
     * @param page 页码
     * @param size 每页大小
     * @return 生产排程列表
     */
    IPage<DemoSchedule> selectDemoSchedulePage(DemoSchedule demoSchedule, Integer page, Integer size);

    /**
     * 查询生产排程列表（不分页）
     * 
     * @param demoSchedule 查询条件
     * @return 生产排程列表
     */
    List<DemoSchedule> selectDemoScheduleList(DemoSchedule demoSchedule);

    /**
     * 查询生产排程详情
     * 
     * @param id 生产排程ID
     * @return 生产排程详情
     */
    DemoSchedule selectDemoScheduleById(Long id);

    /**
     * 新增生产排程
     * 
     * @param demoSchedule 生产排程信息
     * @return 新增结果
     */
    boolean insertDemoSchedule(DemoSchedule demoSchedule);

    /**
     * 修改生产排程
     * 
     * @param demoSchedule 生产排程信息
     * @return 修改结果
     */
    boolean updateDemoSchedule(DemoSchedule demoSchedule);

    /**
     * 删除生产排程
     * 
     * @param id 生产排程ID
     * @return 删除结果
     */
    boolean deleteDemoScheduleById(Long id);

    /**
     * 批量删除生产排程
     * 
     * @param ids 生产排程ID列表
     * @return 删除结果
     */
    boolean deleteDemoScheduleByIds(Long[] ids);

    /**
     * 计算生产排程的负载率
     * 
     * @param demoSchedule 生产排程信息
     * @return 负载率
     */
    BigDecimal calculateLoadRate(DemoSchedule demoSchedule);

    /**
     * 判断生产排程是否爆满
     * 
     * @param demoSchedule 生产排程信息
     * @return 是否爆满
     */
    boolean isFullLoad(DemoSchedule demoSchedule);

    /**
     * 判断生产排程是否空闲
     * 
     * @param demoSchedule 生产排程信息
     * @return 是否空闲
     */
    boolean isIdleLoad(DemoSchedule demoSchedule);

    /**
     * 计算生产排程的产能利用率
     *
     * @param demoSchedule 生产排程信息
     * @return 产能利用率
     */
    BigDecimal calculateCapacityUtilization(DemoSchedule demoSchedule);

    /**
     * 查询甘特图数据（按日期范围+工序过滤）
     *
     * @param startDate 开始日期（yyyy-MM-dd）
     * @param endDate   结束日期（yyyy-MM-dd）
     * @param process   工序名称（模糊匹配，可为空）
     * @return 排程列表，按 priority ASC, startDate ASC 排序
     */
    List<DemoSchedule> selectGanttList(String startDate, String endDate, String process);

    /**
     * 检测单条排程是否有冲突并更新 conflict_flag
     * 1=产能冲突（load>100），2=日期冲突（dueDate<today 且未完成），0=无冲突
     *
     * @param id 排程ID
     * @return 是否存在冲突
     */
    boolean detectAndMarkConflicts(Long id);

    /**
     * 批量检测所有非已完成排程的冲突
     *
     * @return 发现的冲突总数
     */
    int batchDetectConflicts();

    /**
     * 重新排期：更新 startDate/dueDate，重置状态并重新检测冲突
     *
     * @param id           排程ID
     * @param newStartDate 新开始日期（yyyy-MM-dd）
     * @param newDueDate   新截止日期（yyyy-MM-dd）
     * @return 操作是否成功
     */
    boolean reschedule(Long id, String newStartDate, String newDueDate);
}
