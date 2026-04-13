package com.ruoyi.flowable.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.flowable.domain.FlowableDefinition;
import com.ruoyi.flowable.service.IFlowableDefinitionService;

@RestController
@RequestMapping("/flowable/definition")
public class FlowableDefinitionController extends BaseController {

    @Autowired
    private IFlowableDefinitionService definitionService;

    @GetMapping("/list")
    public TableDataInfo list(FlowableDefinition definition) {
        startPage();
        List<FlowableDefinition> list = definitionService.selectDefinitionList(definition);
        return getDataTable(list);
    }

    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(definitionService.selectDefinitionById(id));
    }

    @Log(title = "流程定义", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FlowableDefinition definition) {
        return toAjax(definitionService.insertDefinition(definition));
    }

    @Log(title = "流程定义", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FlowableDefinition definition) {
        return toAjax(definitionService.updateDefinition(definition));
    }

    @Log(title = "流程定义", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(definitionService.deleteDefinitionByIds(ids));
    }

    @Log(title = "流程定义", businessType = BusinessType.UPDATE)
    @PutMapping("/activate/{id}")
    public AjaxResult activate(@PathVariable("id") String id) {
        return toAjax(definitionService.activateDefinition(id));
    }

    @Log(title = "流程定义", businessType = BusinessType.UPDATE)
    @PutMapping("/suspend/{id}")
    public AjaxResult suspend(@PathVariable("id") String id) {
        return toAjax(definitionService.suspendDefinition(id));
    }

    @Log(title = "流程定义", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FlowableDefinition definition) {
        List<FlowableDefinition> list = definitionService.selectDefinitionList(definition);
        ExcelUtil<FlowableDefinition> util = new ExcelUtil<FlowableDefinition>(FlowableDefinition.class);
        util.exportExcel(response, list, "流程定义数据");
    }
}
