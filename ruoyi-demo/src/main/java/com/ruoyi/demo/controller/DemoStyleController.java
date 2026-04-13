package com.ruoyi.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.demo.domain.DemoStyle;
import com.ruoyi.demo.service.IDemoStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 款式管理控制器
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
@RestController
@RequestMapping("/demo/style")
public class DemoStyleController {

    @Autowired
    private IDemoStyleService demoStyleService;

    /**
     * 查询款式列表（分页）
     */
    @GetMapping("/list")
    public TableDataInfo list(DemoStyle demoStyle) {
        IPage<DemoStyle> page = demoStyleService.selectDemoStylePage(demoStyle,
                demoStyle.getPageNum(), demoStyle.getPageSize());
        return getDataTable(page);
    }

    /**
     * 查询款式列表（不分页）
     */
    @GetMapping("/all")
    public AjaxResult all(DemoStyle demoStyle) {
        List<DemoStyle> list = demoStyleService.selectDemoStyleList(demoStyle);
        return AjaxResult.success(list);
    }

    /**
     * 导出款式列表
     */
    @GetMapping("/export")
    public AjaxResult export(DemoStyle demoStyle) {
        List<DemoStyle> list = demoStyleService.selectDemoStyleList(demoStyle);
        ExcelUtil<DemoStyle> util = new ExcelUtil<>(DemoStyle.class);
        return util.exportExcel(list, "款式数据");
    }

    /**
     * 获取款式详情
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(demoStyleService.selectDemoStyleById(id));
    }

    /**
     * 新增款式
     */
    @PostMapping
    public AjaxResult add(@RequestBody DemoStyle demoStyle) {
        return toAjax(demoStyleService.insertDemoStyle(demoStyle));
    }

    /**
     * 修改款式
     */
    @PutMapping
    public AjaxResult edit(@RequestBody DemoStyle demoStyle) {
        return toAjax(demoStyleService.updateDemoStyle(demoStyle));
    }

    /**
     * 删除款式
     */
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(demoStyleService.deleteDemoStyleByIds(ids));
    }
}
