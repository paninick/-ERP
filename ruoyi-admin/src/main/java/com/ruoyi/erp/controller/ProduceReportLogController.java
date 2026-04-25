package com.ruoyi.erp.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.erp.domain.ProduceReportLog;
import com.ruoyi.erp.service.IProduceReportLogService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/erp/produceReportLog")
public class ProduceReportLogController extends BaseController {

    @Autowired
    private IProduceReportLogService produceReportLogService;

    @PreAuthorize("@ss.hasPermi('erp:produceReportLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProduceReportLog produceReportLog) {
        startPage();
        List<ProduceReportLog> list = produceReportLogService.selectProduceReportLogList(produceReportLog);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('erp:produceReportLog:export')")
    @Log(title = "生产报工流水", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProduceReportLog produceReportLog) {
        List<ProduceReportLog> list = produceReportLogService.selectProduceReportLogList(produceReportLog);
        ExcelUtil<ProduceReportLog> util = new ExcelUtil<ProduceReportLog>(ProduceReportLog.class);
        util.exportExcel(response, list, "生产报工流水数据");
    }

    @PreAuthorize("@ss.hasPermi('erp:produceReportLog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(produceReportLogService.selectProduceReportLogById(id));
    }

    @PreAuthorize("@ss.hasPermi('erp:produceReportLog:add')")
    @Log(title = "生产报工", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProduceReportLog produceReportLog) {
        return toAjax(produceReportLogService.insertProduceReportLog(produceReportLog));
    }

    @PreAuthorize("@ss.hasPermi('erp:produceReportLog:add')")
    @Log(title = "批量生产报工", businessType = BusinessType.INSERT)
    @PostMapping("/batchReport")
    public AjaxResult batchReport(@RequestBody List<ProduceReportLog> reportLogs) {
        return toAjax(produceReportLogService.batchReport(reportLogs));
    }

    @PreAuthorize("@ss.hasPermi('erp:produceReportLog:edit')")
    @Log(title = "生产报工流水", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProduceReportLog produceReportLog) {
        return toAjax(produceReportLogService.updateProduceReportLog(produceReportLog));
    }

    @PreAuthorize("@ss.hasPermi('erp:produceReportLog:remove')")
    @Log(title = "生产报工流水", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(produceReportLogService.deleteProduceReportLogByIds(ids));
    }
}
