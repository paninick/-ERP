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
import com.ruoyi.flowable.domain.FlowListener;
import com.ruoyi.flowable.service.IFlowListenerService;
import jakarta.servlet.http.HttpServletResponse;
import com.ruoyi.common.utils.poi.ExcelUtil;
@RestController
@RequestMapping("/flowable/listener")
public class FlowListenerController extends BaseController {
    
    @Autowired
    private IFlowListenerService flowListenerService;

    @PreAuthorize("@ss.hasPermi('flowable:listener:list')")
    @GetMapping("/list")
    public TableDataInfo list(FlowListener flowListener) {
        startPage();
        List<FlowListener> list = flowListenerService.selectFlowListenerList(flowListener);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('flowable:listener:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(flowListenerService.selectFlowListenerById(id));
    }

    @PreAuthorize("@ss.hasPermi('flowable:listener:add')")
    @Log(title = "流程监听", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FlowListener flowListener) {
        return toAjax(flowListenerService.insertFlowListener(flowListener));
    }

    @PreAuthorize("@ss.hasPermi('flowable:listener:edit')")
    @Log(title = "流程监听", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FlowListener flowListener) {
        return toAjax(flowListenerService.updateFlowListener(flowListener));
    }

    @PreAuthorize("@ss.hasPermi('flowable:listener:remove')")
    @Log(title = "流程监听", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(flowListenerService.deleteFlowListenerByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('flowable:listener:export')")
    @Log(title = "流程监听", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FlowListener flowListener) {
        List<FlowListener> list = flowListenerService.selectFlowListenerList(flowListener);
        ExcelUtil<FlowListener> util = new ExcelUtil<FlowListener>(FlowListener.class);
        util.exportExcel(response, list, "流程监听数据");
    }
}
