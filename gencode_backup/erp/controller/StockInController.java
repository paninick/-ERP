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
import com.ruoyi.erp.domain.StockIn;
import com.ruoyi.erp.service.IStockInService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 入库单Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping("/erp/stockIn")
public class StockInController extends BaseController {
    @Autowired
    private IStockInService stockInService;

    /**
     * 查询入库单列表
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:list')")
    @GetMapping("/list")
    public TableDataInfo list(StockIn stockIn) {
        startPage();
        List<StockIn> list = stockInService.selectStockInList(stockIn);
        return getDataTable(list);
    }

    /**
     * 导出入库单列表
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:export')")
    @Log(title = "入库单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StockIn stockIn) {
        List<StockIn> list = stockInService.selectStockInList(stockIn);
        ExcelUtil<StockIn> util = new ExcelUtil<StockIn>(StockIn.class);
        util.exportExcel(response, list, "入库单数据");
    }

    /**
     * 获取入库单详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(stockInService.selectStockInById(id));
    }

    /**
     * 新增入库单
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:add')")
    @Log(title = "入库单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StockIn stockIn) {
        return toAjax(stockInService.insertStockIn(stockIn));
    }

    /**
     * 修改入库单
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:edit')")
    @Log(title = "入库单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StockIn stockIn) {
        return toAjax(stockInService.updateStockIn(stockIn));
    }

    /**
     * 删除入库单
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:remove')")
    @Log(title = "入库单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(stockInService.deleteStockInByIds(ids));
    }
}
