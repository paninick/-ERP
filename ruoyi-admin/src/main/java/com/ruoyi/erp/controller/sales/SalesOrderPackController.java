package com.ruoyi.erp.controller.sales;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
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
import com.ruoyi.erp.domain.SalesOrderPack;
import com.ruoyi.erp.service.ISalesOrderPackService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 大货订单包装纸箱明细Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping("/erp/sales/pack")
public class SalesOrderPackController extends BaseController {
    @Autowired
    private ISalesOrderPackService salesOrderPackService;

    /**
     * 查询大货订单包装纸箱明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:sales:list')")
    @GetMapping("/list")
    public TableDataInfo list(SalesOrderPack salesOrderPack) {
        startPage();
        List<SalesOrderPack> list = salesOrderPackService.selectSalesOrderPackList(salesOrderPack);
        return getDataTable(list);
    }

    /**
     * 导出大货订单包装纸箱明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:sales:export')")
    @Log(title = "大货订单包装纸箱明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SalesOrderPack salesOrderPack) {
        List<SalesOrderPack> list = salesOrderPackService.selectSalesOrderPackList(salesOrderPack);
        ExcelUtil<SalesOrderPack> util = new ExcelUtil<SalesOrderPack>(SalesOrderPack.class);
        util.exportExcel(response, list, "大货订单包装纸箱明细数据");
    }

    /**
     * 获取大货订单包装纸箱明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:sales:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(salesOrderPackService.selectSalesOrderPackById(id));
    }

    /**
     * 新增大货订单包装纸箱明细
     */
    @PreAuthorize("@ss.hasPermi('erp:sales:add')")
    @Log(title = "大货订单包装纸箱明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SalesOrderPack salesOrderPack) {
        return toAjax(salesOrderPackService.insertSalesOrderPack(salesOrderPack));
    }

    /**
     * 修改大货订单包装纸箱明细
     */
    @PreAuthorize("@ss.hasPermi('erp:sales:edit')")
    @Log(title = "大货订单包装纸箱明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SalesOrderPack salesOrderPack) {
        return toAjax(salesOrderPackService.updateSalesOrderPack(salesOrderPack));
    }

    /**
     * 删除大货订单包装纸箱明细
     */
    @PreAuthorize("@ss.hasPermi('erp:sales:remove')")
    @Log(title = "大货订单包装纸箱明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(salesOrderPackService.deleteSalesOrderPackByIds(ids));
    }
}
