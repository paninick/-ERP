package com.ruoyi.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.demo.domain.DemoReport;
import java.util.List;

/**
 * 报工管理Service接口
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
public interface IDemoReportService extends IService<DemoReport> {
    /**
     * 查询报工列表（分页）
     * 
     * @param demoReport 查询条件
     * @param page 页码
     * @param size 每页大小
     * @return 报工列表
     */
    IPage<DemoReport> selectDemoReportPage(DemoReport demoReport, Integer page, Integer size);

    /**
     * 查询报工列表（不分页）
     * 
     * @param demoReport 查询条件
     * @return 报工列表
     */
    List<DemoReport> selectDemoReportList(DemoReport demoReport);

    /**
     * 查询报工详情
     * 
     * @param id 报工ID
     * @return 报工详情
     */
    DemoReport selectDemoReportById(Long id);

    /**
     * 新增报工
     * 
     * @param demoReport 报工信息
     * @return 新增结果
     */
    boolean insertDemoReport(DemoReport demoReport);

    /**
     * 修改报工
     * 
     * @param demoReport 报工信息
     * @return 修改结果
     */
    boolean updateDemoReport(DemoReport demoReport);

    /**
     * 删除报工
     * 
     * @param id 报工ID
     * @return 删除结果
     */
    boolean deleteDemoReportById(Long id);

    /**
     * 批量删除报工
     * 
     * @param ids 报工ID列表
     * @return 删除结果
     */
    boolean deleteDemoReportByIds(Long[] ids);
}
