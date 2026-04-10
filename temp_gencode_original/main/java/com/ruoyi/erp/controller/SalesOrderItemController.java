package com.ruoyi.erp.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.erp.domain.SalesOrderItem;
import com.ruoyi.erp.service.ISalesOrderItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 销售订单明细Controller
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
@RestController
@RequestMapping("/erp/salesitem")
public class SalesOrderItemController extends BaseController {
    @Autowired
    private ISalesOrderItemService salesOrderItemService;

    /**
     * 查询销售订单明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:salesitem:list')")
    @GetMapping("/list")
    public TableDataInfo list(SalesOrderItem salesOrderItem) {
        startPage();
        List<SalesOrderItem> list = salesOrderItemService.selectSalesOrderItemList(salesOrderItem);
        return getDataTable(list);
    }

    /**
     * 导出销售订单明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:salesitem:export')")
    @Log(title = "销售订单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SalesOrderItem salesOrderItem) {
        List<SalesOrderItem> list = salesOrderItemService.selectSalesOrderItemList(salesOrderItem);
        ExcelUtil<SalesOrderItem> util = new ExcelUtil<SalesOrderItem>(SalesOrderItem.class);
        util.exportExcel(response, list, "销售订单明细数据");
    }

    /**
     * 获取销售订单明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:salesitem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(salesOrderItemService.selectSalesOrderItemById(id));
    }

    /**
     * 新增销售订单明细
     */
    @PreAuthorize("@ss.hasPermi('erp:salesitem:add')")
    @Log(title = "销售订单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SalesOrderItem salesOrderItem) {
        return toAjax(salesOrderItemService.insertSalesOrderItem(salesOrderItem));
    }

    /**
     * 修改销售订单明细
     */
    @PreAuthorize("@ss.hasPermi('erp:salesitem:edit')")
    @Log(title = "销售订单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SalesOrderItem salesOrderItem) {
        return toAjax(salesOrderItemService.updateSalesOrderItem(salesOrderItem));
    }

    /**
     * 删除销售订单明细
     */
    @PreAuthorize("@ss.hasPermi('erp:salesitem:remove')")
    @Log(title = "销售订单明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(salesOrderItemService.deleteSalesOrderItemByIds(ids));
    }
}
