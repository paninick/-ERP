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
import com.ruoyi.flowable.domain.FlowExpression;
import com.ruoyi.flowable.service.IFlowExpressionService;
import jakarta.servlet.http.HttpServletResponse;
import com.ruoyi.common.utils.poi.ExcelUtil;
@RestController
@RequestMapping("/flowable/expression")
public class FlowExpressionController extends BaseController {
    
    @Autowired
    private IFlowExpressionService flowExpressionService;

    @PreAuthorize("@ss.hasPermi('flowable:expression:list')")
    @GetMapping("/list")
    public TableDataInfo list(FlowExpression flowExpression) {
        startPage();
        List<FlowExpression> list = flowExpressionService.selectFlowExpressionList(flowExpression);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('flowable:expression:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(flowExpressionService.selectFlowExpressionById(id));
    }

    @PreAuthorize("@ss.hasPermi('flowable:expression:add')")
    @Log(title = "流程表达式", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FlowExpression flowExpression) {
        return toAjax(flowExpressionService.insertFlowExpression(flowExpression));
    }

    @PreAuthorize("@ss.hasPermi('flowable:expression:edit')")
    @Log(title = "流程表达式", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FlowExpression flowExpression) {
        return toAjax(flowExpressionService.updateFlowExpression(flowExpression));
    }

    @PreAuthorize("@ss.hasPermi('flowable:expression:remove')")
    @Log(title = "流程表达式", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(flowExpressionService.deleteFlowExpressionByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('flowable:expression:export')")
    @Log(title = "流程表达式", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FlowExpression flowExpression) {
        List<FlowExpression> list = flowExpressionService.selectFlowExpressionList(flowExpression);
        ExcelUtil<FlowExpression> util = new ExcelUtil<FlowExpression>(FlowExpression.class);
        util.exportExcel(response, list, "流程表达式数据");
    }
}
