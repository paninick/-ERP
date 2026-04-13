package com.ruoyi.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.demo.domain.DemoSchedule;
import com.ruoyi.demo.service.IDemoScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 生产排程管理控制器
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
@RestController
@RequestMapping("/demo/schedule")
public class DemoScheduleController {

    @Autowired
    private IDemoScheduleService demoScheduleService;

    /**
     * 查询生产排程列表（分页）
     */
    @GetMapping("/list")
    public TableDataInfo list(DemoSchedule demoSchedule) {
        IPage<DemoSchedule> page = demoScheduleService.selectDemoSchedulePage(demoSchedule,
                demoSchedule.getPageNum(), demoSchedule.getPageSize());
        return getDataTable(page);
    }

    /**
     * 查询生产排程列表（不分页）
     */
    @GetMapping("/all")
    public AjaxResult all(DemoSchedule demoSchedule) {
        List<DemoSchedule> list = demoScheduleService.selectDemoScheduleList(demoSchedule);
        return AjaxResult.success(list);
    }

    /**
     * 导出生产排程列表
     */
    @GetMapping("/export")
    public AjaxResult export(DemoSchedule demoSchedule) {
        List<DemoSchedule> list = demoScheduleService.selectDemoScheduleList(demoSchedule);
        ExcelUtil<DemoSchedule> util = new ExcelUtil<>(DemoSchedule.class);
        return util.exportExcel(list, "生产排程数据");
    }

    /**
     * 获取生产排程详情
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(demoScheduleService.selectDemoScheduleById(id));
    }

    /**
     * 新增生产排程
     */
    @PostMapping
    public AjaxResult add(@RequestBody DemoSchedule demoSchedule) {
        return toAjax(demoScheduleService.insertDemoSchedule(demoSchedule));
    }

    /**
     * 修改生产排程
     */
    @PutMapping
    public AjaxResult edit(@RequestBody DemoSchedule demoSchedule) {
        return toAjax(demoScheduleService.updateDemoSchedule(demoSchedule));
    }

    /**
     * 删除生产排程
     */
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(demoScheduleService.deleteDemoScheduleByIds(ids));
    }

    /**
     * 判断生产排程是否爆满
     */
    @GetMapping("/isFullLoad/{id}")
    public AjaxResult isFullLoad(@PathVariable("id") Long id) {
        DemoSchedule demoSchedule = demoScheduleService.selectDemoScheduleById(id);
        if (demoSchedule == null) {
            return AjaxResult.error("生产排程不存在");
        }
        
        boolean isFullLoad = demoScheduleService.isFullLoad(demoSchedule);
        return AjaxResult.success(isFullLoad);
    }

    /**
     * 判断生产排程是否空闲
     */
    @GetMapping("/isIdleLoad/{id}")
    public AjaxResult isIdleLoad(@PathVariable("id") Long id) {
        DemoSchedule demoSchedule = demoScheduleService.selectDemoScheduleById(id);
        if (demoSchedule == null) {
            return AjaxResult.error("生产排程不存在");
        }
        
        boolean isIdleLoad = demoScheduleService.isIdleLoad(demoSchedule);
        return AjaxResult.success(isIdleLoad);
    }
}
