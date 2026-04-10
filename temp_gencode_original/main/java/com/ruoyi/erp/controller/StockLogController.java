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
import com.ruoyi.erp.domain.StockLog;
import com.ruoyi.erp.service.IStockLogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 出入库流水Controller
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
@RestController
@RequestMapping("/erp/stock")
public class StockLogController extends BaseController {
    @Autowired
    private IStockLogService stockLogService;

    /**
     * 查询出入库流水列表
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:list')")
    @GetMapping("/list")
    public TableDataInfo list(StockLog stockLog) {
        startPage();
        List<StockLog> list = stockLogService.selectStockLogList(stockLog);
        return getDataTable(list);
    }

    /**
     * 导出出入库流水列表
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:export')")
    @Log(title = "出入库流水", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StockLog stockLog) {
        List<StockLog> list = stockLogService.selectStockLogList(stockLog);
        ExcelUtil<StockLog> util = new ExcelUtil<StockLog>(StockLog.class);
        util.exportExcel(response, list, "出入库流水数据");
    }

    /**
     * 获取出入库流水详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(stockLogService.selectStockLogById(id));
    }

    /**
     * 新增出入库流水
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:add')")
    @Log(title = "出入库流水", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StockLog stockLog) {
        return toAjax(stockLogService.insertStockLog(stockLog));
    }

    /**
     * 修改出入库流水
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:edit')")
    @Log(title = "出入库流水", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StockLog stockLog) {
        return toAjax(stockLogService.updateStockLog(stockLog));
    }

    /**
     * 删除出入库流水
     */
    @PreAuthorize("@ss.hasPermi('erp:stock:remove')")
    @Log(title = "出入库流水", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(stockLogService.deleteStockLogByIds(ids));
    }
}
