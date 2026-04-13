package com.ruoyi.erp.controller;

import java.util.List;
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
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.erp.domain.DataImport;
import com.ruoyi.erp.service.IDataImportService;

@RestController
@RequestMapping("/erp/dataimport")
public class DataImportController extends BaseController
{
    @Autowired
    private IDataImportService dataImportService;

    @GetMapping("/list")
    public TableDataInfo list(DataImport dataImport)
    {
        startPage();
        List<DataImport> list = dataImportService.selectDataImportList(dataImport);
        return getDataTable(list);
    }

    @GetMapping("/{importId}")
    public AjaxResult getInfo(@PathVariable Long importId)
    {
        return success(dataImportService.selectDataImportByImportId(importId));
    }

    @Log(title = "数据导入", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DataImport dataImport)
    {
        dataImport.setCreateBy(getUsername());
        return toAjax(dataImportService.insertDataImport(dataImport));
    }

    @Log(title = "数据导入", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DataImport dataImport)
    {
        dataImport.setUpdateBy(getUsername());
        return toAjax(dataImportService.updateDataImport(dataImport));
    }

    @Log(title = "数据导入", businessType = BusinessType.DELETE)
    @DeleteMapping("/{importIds}")
    public AjaxResult remove(@PathVariable Long[] importIds)
    {
        return toAjax(dataImportService.deleteDataImportByImportIds(importIds));
    }
}
