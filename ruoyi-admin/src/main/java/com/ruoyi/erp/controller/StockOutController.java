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
import com.ruoyi.erp.domain.StockOut;
import com.ruoyi.erp.service.IStockOutService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 出库单Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping("/erp/stockOut")
public class StockOutController extends BaseController {
    @Autowired
    private IStockOutService stockOutService;

    /**
     * 查询出库单列表
     */
    @PreAuthorize("@ss.hasPermi('erp:stockout:list')")
    @GetMapping("/list")
    public TableDataInfo list(StockOut stockOut) {
        startPage();
        List<StockOut> list = stockOutService.selectStockOutList(stockOut);
        return getDataTable(list);
    }

    /**
     * 导出出库单列表
     */
    @PreAuthorize("@ss.hasPermi('erp:stockout:export')")
    @Log(title = "出库单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StockOut stockOut) {
        List<StockOut> list = stockOutService.selectStockOutList(stockOut);
        ExcelUtil<StockOut> util = new ExcelUtil<StockOut>(StockOut.class);
        util.exportExcel(response, list, "出库单数据");
    }

    /**
     * 获取出库单详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:stockout:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(stockOutService.selectStockOutById(id));
    }

    /**
     * 新增出库单
     */
    @PreAuthorize("@ss.hasPermi('erp:stockout:add')")
    @Log(title = "出库单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody StockOut stockOut) {
        return toAjax(stockOutService.insertStockOut(stockOut));
    }

    /**
     * 修改出库单
     */
    @PreAuthorize("@ss.hasPermi('erp:stockout:edit')")
    @Log(title = "出库单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody StockOut stockOut) {
        return toAjax(stockOutService.updateStockOut(stockOut));
    }

    /**
     * 删除出库单
     */
    @PreAuthorize("@ss.hasPermi('erp:stockout:remove')")
    @Log(title = "出库单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(stockOutService.deleteStockOutByIds(ids));
    }
}
