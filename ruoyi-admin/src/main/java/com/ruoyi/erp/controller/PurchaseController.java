package com.ruoyi.erp.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.erp.domain.Purchase;
import com.ruoyi.erp.service.IPurchaseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 采购单Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping("/erp/purchase")
public class PurchaseController extends BaseController {
    @Autowired
    private IPurchaseService purchaseService;

    /**
     * 查询采购单列表
     */
    @PreAuthorize("@ss.hasPermi('erp:purchase:list')")
    @GetMapping("/list")
    public TableDataInfo list(Purchase purchase) {
        startPage();
        List<Purchase> list = purchaseService.selectPurchaseList(purchase);
        return getDataTable(list);
    }

    /**
     * 导出采购单列表
     */
    @PreAuthorize("@ss.hasPermi('erp:purchase:export')")
    @Log(title = "采购单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Purchase purchase) {
        List<Purchase> list = purchaseService.selectPurchaseList(purchase);
        ExcelUtil<Purchase> util = new ExcelUtil<Purchase>(Purchase.class);
        util.exportExcel(response, list, "采购单数据");
    }

    /**
     * 获取采购单详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:purchase:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(purchaseService.selectPurchaseById(id));
    }

    /**
     * 新增采购单
     */
    @PreAuthorize("@ss.hasPermi('erp:purchase:add')")
    @Log(title = "采购单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Purchase purchase) {
        return toAjax(purchaseService.insertPurchase(purchase));
    }

    /**
     * 修改采购单
     */
    @PreAuthorize("@ss.hasPermi('erp:purchase:edit')")
    @Log(title = "采购单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Purchase purchase) {
        return toAjax(purchaseService.updatePurchase(purchase));
    }

    /**
     * 删除采购单
     */
    @PreAuthorize("@ss.hasPermi('erp:purchase:remove')")
    @Log(title = "采购单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(purchaseService.deletePurchaseByIds(ids));
    }
}
