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
import com.ruoyi.erp.domain.StockOutItem;
import com.ruoyi.erp.service.IStockOutItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 出库明细Controller
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
@RestController
@RequestMapping("/erp/stock")
public class StockOutItemController extends BaseController {
    @Autowired
    private IStockOutItemService stockOutItemService;

    /**
     * 查询出库明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:list')")
    @GetMapping("/list")
    public TableDataInfo list(StockOutItem stockOutItem) {
        startPage();
        List<StockOutItem> list = stockOutItemService.selectStockOutItemList(stockOutItem);
        return getDataTable(list);
    }

    /**
     * 导出出库明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:export')")
    @Log(title = "出库明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StockOutItem stockOutItem) {
        List<StockOutItem> list = stockOutItemService.selectStockOutItemList(stockOutItem);
        ExcelUtil<StockOutItem> util = new ExcelUtil<StockOutItem>(StockOutItem.class);
        util.exportExcel(response, list, "出库明细数据");
    }

    /**
     * 获取出库明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(stockOutItemService.selectStockOutItemById(id));
    }

    /**
     * 新增出库明细
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:add')")
    @Log(title = "出库明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StockOutItem stockOutItem) {
        return toAjax(stockOutItemService.insertStockOutItem(stockOutItem));
    }

    /**
     * 修改出库明细
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:edit')")
    @Log(title = "出库明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StockOutItem stockOutItem) {
        return toAjax(stockOutItemService.updateStockOutItem(stockOutItem));
    }

    /**
     * 删除出库明细
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:remove')")
    @Log(title = "出库明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(stockOutItemService.deleteStockOutItemByIds(ids));
    }
}
