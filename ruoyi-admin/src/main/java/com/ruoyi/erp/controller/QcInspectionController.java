package com.ruoyi.erp.controller;

import java.util.List;
import java.util.Map;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.erp.domain.QcInspection;
import com.ruoyi.erp.service.impl.QcInspectionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erp/qc")
public class QcInspectionController extends BaseController {

    @Autowired
    private QcInspectionServiceImpl qcService;

    @PreAuthorize("@ss.hasPermi('erp:qc:list')")
    @GetMapping("/list")
    public TableDataInfo list(QcInspection query) {
        startPage();
        return getDataTable(qcService.selectList(query));
    }

    @PreAuthorize("@ss.hasPermi('erp:qc:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(qcService.selectById(id));
    }

    @PreAuthorize("@ss.hasPermi('erp:qc:add')")
    @PostMapping
    public AjaxResult add(@RequestBody QcInspection inspection) {
        return toAjax(qcService.insert(inspection));
    }

    @PreAuthorize("@ss.hasPermi('erp:qc:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody QcInspection inspection) {
        return toAjax(qcService.update(inspection));
    }

    @PreAuthorize("@ss.hasPermi('erp:qc:edit')")
    @PostMapping("/reject/{id}")
    public AjaxResult reject(@PathVariable Long id, @RequestBody(required = false) Map<String, String> body) {
        String reason = body != null ? body.get("reason") : null;
        qcService.reject(id, reason);
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('erp:qc:query')")
    @GetMapping("/stats")
    public AjaxResult stats() {
        return AjaxResult.success(qcService.getStats());
    }

    @PreAuthorize("@ss.hasPermi('erp:qc:query')")
    @GetMapping("/defectReasons")
    public AjaxResult defectReasons() {
        return AjaxResult.success(qcService.getDefectReasons());
    }
}
