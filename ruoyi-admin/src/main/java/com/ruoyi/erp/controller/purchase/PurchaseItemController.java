package com.ruoyi.erp.controller.purchase;

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
import com.ruoyi.erp.domain.PurchaseItem;
import com.ruoyi.erp.service.IPurchaseItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 采购明细Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping("/erp/purchase/item")
public class PurchaseItemController extends BaseController {
    @Autowired
    private IPurchaseItemService purchaseItemService;

    /**
     * 查询采购明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:purchase:list')")
    @GetMapping("/list")
    public TableDataInfo list(PurchaseItem purchaseItem) {
        startPage();
        List<PurchaseItem> list = purchaseItemService.selectPurchaseItemList(purchaseItem);
        return getDataTable(list);
    }

    /**
     * 导出采购明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:purchase:export')")
    @Log(title = "采购明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PurchaseItem purchaseItem) {
        List<PurchaseItem> list = purchaseItemService.selectPurchaseItemList(purchaseItem);
        ExcelUtil<PurchaseItem> util = new ExcelUtil<PurchaseItem>(PurchaseItem.class);
        util.exportExcel(response, list, "采购明细数据");
    }

    /**
     * 获取采购明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:purchase:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(purchaseItemService.selectPurchaseItemById(id));
    }

    /**
     * 新增采购明细
     */
    @PreAuthorize("@ss.hasPermi('erp:purchase:add')")
    @Log(title = "采购明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PurchaseItem purchaseItem) {
        return toAjax(purchaseItemService.insertPurchaseItem(purchaseItem));
    }

    /**
     * 修改采购明细
     */
    @PreAuthorize("@ss.hasPermi('erp:purchase:edit')")
    @Log(title = "采购明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PurchaseItem purchaseItem) {
        return toAjax(purchaseItemService.updatePurchaseItem(purchaseItem));
    }

    /**
     * 删除采购明细
     */
    @PreAuthorize("@ss.hasPermi('erp:purchase:remove')")
    @Log(title = "采购明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(purchaseItemService.deletePurchaseItemByIds(ids));
    }
}
