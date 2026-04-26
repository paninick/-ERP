package com.ruoyi.erp.controller;

import java.util.List;
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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.erp.domain.QcDefectRecord;
import com.ruoyi.erp.service.IQcDefectRecordService;

@RestController
@RequestMapping("/erp/qcDefect")
public class QcDefectRecordController extends BaseController {

    @Autowired
    private IQcDefectRecordService qcDefectRecordService;

    @PreAuthorize("@ss.hasPermi('erp:qc:list')")
    @GetMapping("/list")
    public TableDataInfo list(QcDefectRecord record) {
        startPage();
        List<QcDefectRecord> list = qcDefectRecordService.selectList(record);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('erp:qc:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return success(qcDefectRecordService.selectById(id));
    }

    @PreAuthorize("@ss.hasPermi('erp:qc:add')")
    @Log(title = "质检缺陷", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QcDefectRecord record) {
        return toAjax(qcDefectRecordService.insert(record));
    }

    @PreAuthorize("@ss.hasPermi('erp:qc:edit')")
    @Log(title = "质检缺陷", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QcDefectRecord record) {
        return toAjax(qcDefectRecordService.update(record));
    }

    @PreAuthorize("@ss.hasPermi('erp:qc:remove')")
    @Log(title = "质检缺陷", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(qcDefectRecordService.deleteByIds(ids));
    }
}
