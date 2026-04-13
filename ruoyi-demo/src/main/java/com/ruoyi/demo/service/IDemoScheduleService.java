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
}
