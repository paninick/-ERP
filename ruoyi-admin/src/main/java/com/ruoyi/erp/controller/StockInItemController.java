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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.erp.domain.StockInItem;
import com.ruoyi.erp.service.IStockInItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 入库明细Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping("/erp/stockIn/item")
public class StockInItemController extends BaseController {
    @Autowired
    private IStockInItemService stockInItemService;

    /**
     * 查询入库明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:list')")
    @GetMapping("/list")
    public TableDataInfo list(StockInItem stockInItem) {
        startPage();
        List<StockInItem> list = stockInItemService.selectStockInItemList(stockInItem);
        return getDataTable(list);
    }

    /**
     * 导出入库明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:export')")
    @Log(title = "入库明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StockInItem stockInItem) {
        List<StockInItem> list = stockInItemService.selectStockInItemList(stockInItem);
        ExcelUtil<StockInItem> util = new ExcelUtil<StockInItem>(StockInItem.class);
        util.exportExcel(response, list, "入库明细数据");
    }

    /**
     * 获取入库明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(stockInItemService.selectStockInItemById(id));
    }

    /**
     * 新增入库明细
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:add')")
    @Log(title = "入库明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StockInItem stockInItem) {
        return toAjax(stockInItemService.insertStockInItem(stockInItem));
    }

    /**
     * 修改入库明细
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:edit')")
    @Log(title = "入库明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StockInItem stockInItem) {
        return toAjax(stockInItemService.updateStockInItem(stockInItem));
    }

    /**
     * 删除入库明细
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:remove')")
    @Log(title = "入库明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(stockInItemService.deleteStockInItemByIds(ids));
    }
}
