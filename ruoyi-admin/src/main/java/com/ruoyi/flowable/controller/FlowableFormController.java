package com.ruoyi.flowable.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.flowable.domain.Form;
import com.ruoyi.flowable.service.IFormService;
import jakarta.servlet.http.HttpServletResponse;
import com.ruoyi.common.utils.poi.ExcelUtil;
@RestController
@RequestMapping("/flowable/form")
public class FlowableFormController extends BaseController {
    
    @Autowired
    private IFormService formService;

    @PreAuthorize("@ss.hasPermi('flowable:form:list')")
    @GetMapping("/list")
    public TableDataInfo list(Form form) {
        startPage();
        List<Form> list = formService.selectFormList(form);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('flowable:form:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(formService.selectFormById(id));
    }

    @PreAuthorize("@ss.hasPermi('flowable:form:add')")
    @Log(title = "表单配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Form form) {
        return toAjax(formService.insertForm(form));
    }

    @PreAuthorize("@ss.hasPermi('flowable:form:edit')")
    @Log(title = "表单配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Form form) {
        return toAjax(formService.updateForm(form));
    }

    @PreAuthorize("@ss.hasPermi('flowable:form:remove')")
    @Log(title = "表单配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(formService.deleteFormByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('flowable:form:export')")
    @Log(title = "表单配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Form form) {
        List<Form> list = formService.selectFormList(form);
        ExcelUtil<Form> util = new ExcelUtil<Form>(Form.class);
        util.exportExcel(response, list, "表单配置数据");
    }
}
