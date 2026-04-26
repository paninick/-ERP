package com.ruoyi.erp.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.erp.domain.StockInItem;
import com.ruoyi.erp.service.IStockInItemService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Stock-in item controller.
 */
@RestController
@RequestMapping("/erp/stockIn/item")
public class StockInItemController extends BaseController {
    @Autowired
    private IStockInItemService stockInItemService;

    @PreAuthorize("@ss.hasPermi('erp:stock:list')")
    @GetMapping("/list")
    public TableDataInfo list(StockInItem stockInItem) {
        startPage();
        List<StockInItem> list = stockInItemService.selectStockInItemList(stockInItem);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('erp:stock:export')")
    @Log(title = "入库明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StockInItem stockInItem) {
        List<StockInItem> list = stockInItemService.selectStockInItemList(stockInItem);
        ExcelUtil<StockInItem> util = new ExcelUtil<>(StockInItem.class);
        util.exportExcel(response, list, "stock_in_item");
    }

    @PreAuthorize("@ss.hasPermi('erp:stock:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(stockInItemService.selectStockInItemById(id));
    }

    @PreAuthorize("@ss.hasPermi('erp:stock:query')")
    @GetMapping("/listByIn/{inId}")
    public AjaxResult listByIn(@PathVariable("inId") Long inId) {
        return success(stockInItemService.selectStockInItemByInId(inId));
    }

    @PreAuthorize("@ss.hasPermi('erp:stock:add')")
    @Log(title = "入库明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StockInItem stockInItem) {
        return toAjax(stockInItemService.insertStockInItem(stockInItem));
    }

    @PreAuthorize("@ss.hasPermi('erp:stock:edit')")
    @Log(title = "入库明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StockInItem stockInItem) {
        return toAjax(stockInItemService.updateStockInItem(stockInItem));
    }

    @PreAuthorize("@ss.hasPermi('erp:stock:remove')")
    @Log(title = "入库明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(stockInItemService.deleteStockInItemByIds(ids));
    }
}
