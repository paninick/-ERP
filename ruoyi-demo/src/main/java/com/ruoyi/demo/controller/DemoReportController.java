package com.ruoyi.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.demo.domain.DemoReport;
import com.ruoyi.demo.service.IDemoReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 报工管理控制器
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
@RestController
@RequestMapping("/demo/report")
public class DemoReportController {

    @Autowired
    private IDemoReportService demoReportService;

    /**
     * 查询报工列表（分页）
     */
    @GetMapping("/list")
    public TableDataInfo list(DemoReport demoReport) {
        IPage<DemoReport> page = demoReportService.selectDemoReportPage(demoReport,
                demoReport.getPageNum(), demoReport.getPageSize());
        return getDataTable(page);
    }

    /**
     * 查询报工列表（不分页）
     */
    @GetMapping("/all")
    public AjaxResult all(DemoReport demoReport) {
        List<DemoReport> list = demoReportService.selectDemoReportList(demoReport);
        return AjaxResult.success(list);
    }

    /**
     * 导出报工列表
     */
    @GetMapping("/export")
    public AjaxResult export(DemoReport demoReport) {
        List<DemoReport> list = demoReportService.selectDemoReportList(demoReport);
        ExcelUtil<DemoReport> util = new ExcelUtil<>(DemoReport.class);
        return util.exportExcel(list, "报工数据");
    }

    /**
     * 获取报工详情
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(demoReportService.selectDemoReportById(id));
    }

    /**
     * 新增报工
     */
    @PostMapping
    public AjaxResult add(@RequestBody DemoReport demoReport) {
        return toAjax(demoReportService.insertDemoReport(demoReport));
    }

    /**
     * 修改报工
     */
    @PutMapping
    public AjaxResult edit(@RequestBody DemoReport demoReport) {
        return toAjax(demoReportService.updateDemoReport(demoReport));
    }

    /**
     * 删除报工
     */
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(demoReportService.deleteDemoReportByIds(ids));
    }
}
