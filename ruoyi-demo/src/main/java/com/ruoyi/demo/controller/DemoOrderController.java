package com.ruoyi.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.demo.domain.DemoOrder;
import com.ruoyi.demo.service.IDemoOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 订单管理控制器
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
@RestController
@RequestMapping("/demo/order")
public class DemoOrderController {

    @Autowired
    private IDemoOrderService demoOrderService;

    /**
     * 查询订单列表（分页）
     */
    @GetMapping("/list")
    public TableDataInfo list(DemoOrder demoOrder) {
        IPage<DemoOrder> page = demoOrderService.selectDemoOrderPage(demoOrder,
                demoOrder.getPageNum(), demoOrder.getPageSize());
        return getDataTable(page);
    }

    /**
     * 查询订单列表（不分页）
     */
    @GetMapping("/all")
    public AjaxResult all(DemoOrder demoOrder) {
        List<DemoOrder> list = demoOrderService.selectDemoOrderList(demoOrder);
        return AjaxResult.success(list);
    }

    /**
     * 导出订单列表
     */
    @GetMapping("/export")
    public AjaxResult export(DemoOrder demoOrder) {
        List<DemoOrder> list = demoOrderService.selectDemoOrderList(demoOrder);
        ExcelUtil<DemoOrder> util = new ExcelUtil<>(DemoOrder.class);
        return util.exportExcel(list, "订单数据");
    }

    /**
     * 获取订单详情
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(demoOrderService.selectDemoOrderById(id));
    }

    /**
     * 新增订单
     */
    @PostMapping
    public AjaxResult add(@RequestBody DemoOrder demoOrder) {
        return toAjax(demoOrderService.insertDemoOrder(demoOrder));
    }

    /**
     * 修改订单
     */
    @PutMapping
    public AjaxResult edit(@RequestBody DemoOrder demoOrder) {
        return toAjax(demoOrderService.updateDemoOrder(demoOrder));
    }

    /**
     * 删除订单
     */
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(demoOrderService.deleteDemoOrderByIds(ids));
    }
}
