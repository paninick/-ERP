package com.ruoyi.demo.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.demo.domain.DemoOutsource;
import com.ruoyi.demo.domain.DemoOutsourceExtra;
import com.ruoyi.demo.service.IDemoOutsourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 外发加工管理控制器
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
@RestController
@RequestMapping("/demo/outsource")
public class DemoOutsourceController extends BaseController {

    @Autowired
    private IDemoOutsourceService demoOutsourceService;

    /**
     * 查询外发加工订单列表（分页）
     */
    @PreAuthorize("@ss.hasPermi('demo:outsource:list')")
    @GetMapping("/list")
    public TableDataInfo list(DemoOutsource demoOutsource) {
        startPage();
        List<DemoOutsource> list = demoOutsourceService.selectDemoOutsourceList(demoOutsource);
        return getDataTable(list);
    }

    /**
     * 查询外发加工订单列表（不分页）
     */
    @PreAuthorize("@ss.hasPermi('demo:outsource:list')")
    @GetMapping("/all")
    public AjaxResult all(DemoOutsource demoOutsource) {
        List<DemoOutsource> list = demoOutsourceService.selectDemoOutsourceList(demoOutsource);
        return AjaxResult.success(list);
    }

    /**
     * 导出外发加工订单列表
     */
    @PreAuthorize("@ss.hasPermi('demo:outsource:export')")
    @GetMapping("/export")
    public AjaxResult export(DemoOutsource demoOutsource) {
        List<DemoOutsource> list = demoOutsourceService.selectDemoOutsourceList(demoOutsource);
        ExcelUtil<DemoOutsource> util = new ExcelUtil<>(DemoOutsource.class);
        return util.exportExcel(list, "外发加工订单数据");
    }

    /**
     * 获取外发加工订单详情
     */
    @PreAuthorize("@ss.hasPermi('demo:outsource:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(demoOutsourceService.selectDemoOutsourceById(id));
    }

    /**
     * 新增外发加工订单
     */
    @PreAuthorize("@ss.hasPermi('demo:outsource:add')")
    @PostMapping
    public AjaxResult add(@RequestBody DemoOutsource demoOutsource) {
        return toAjax(demoOutsourceService.insertDemoOutsource(demoOutsource));
    }

    /**
     * 修改外发加工订单
     */
    @PreAuthorize("@ss.hasPermi('demo:outsource:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody DemoOutsource demoOutsource) {
        return toAjax(demoOutsourceService.updateDemoOutsource(demoOutsource));
    }

    /**
     * 删除外发加工订单
     */
    @PreAuthorize("@ss.hasPermi('demo:outsource:remove')")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(demoOutsourceService.deleteDemoOutsourceByIds(ids));
    }

    /**
     * 查询外发加工补料记录列表
     */
    @PreAuthorize("@ss.hasPermi('demo:outsource:list')")
    @GetMapping("/extra/list")
    public TableDataInfo extraList(DemoOutsourceExtra demoOutsourceExtra) {
        startPage();
        List<DemoOutsourceExtra> list = demoOutsourceService.selectDemoOutsourceExtraList(demoOutsourceExtra);
        return getDataTable(list);
    }

    /**
     * 获取外发加工补料记录详情
     */
    @PreAuthorize("@ss.hasPermi('demo:outsource:query')")
    @GetMapping(value = "/extra/{id}")
    public AjaxResult getExtraInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(demoOutsourceService.selectDemoOutsourceExtraById(id));
    }

    /**
     * 新增外发加工补料记录
     */
    @PreAuthorize("@ss.hasPermi('demo:outsource:add')")
    @PostMapping("/extra")
    public AjaxResult addExtra(@RequestBody DemoOutsourceExtra demoOutsourceExtra) {
        return toAjax(demoOutsourceService.insertDemoOutsourceExtra(demoOutsourceExtra));
    }

    /**
     * 审批外发加工补料记录
     */
    @PreAuthorize("@ss.hasPermi('demo:outsource:edit')")
    @PutMapping("/extra/approve")
    public AjaxResult approveExtra(@RequestParam("id") Long id, @RequestParam("approved") String approved) {
        return toAjax(demoOutsourceService.approveDemoOutsourceExtra(id, approved));
    }
}
