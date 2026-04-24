package com.ruoyi.flowable.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.flowable.domain.FlowTask;
import com.ruoyi.flowable.service.IFlowableTodoService;

@RestController
@RequestMapping("/flowable/task/todo")
public class FlowableTodoController extends BaseController
{
    @Autowired
    private IFlowableTodoService todoService;

    @GetMapping("/list")
    public TableDataInfo list(FlowTask task)
    {
        startPage();
        List<FlowTask> list = todoService.selectTodoList(task);
        return getDataTable(list);
    }

    @PostMapping("/approve")
    public AjaxResult approve(@RequestBody Map<String, Object> payload)
    {
        return toAjax(handleBatchAction(payload, true));
    }

    @PostMapping("/reject")
    public AjaxResult reject(@RequestBody Map<String, Object> payload)
    {
        return toAjax(handleBatchAction(payload, false));
    }

    private boolean handleBatchAction(Map<String, Object> payload, boolean approve)
    {
        if (payload == null || payload.get("taskId") == null)
        {
            return false;
        }
        String comment = payload.get("comment") == null ? "" : String.valueOf(payload.get("comment"));
        String[] taskIds = String.valueOf(payload.get("taskId")).split(",");
        boolean success = false;
        for (String rawId : taskIds)
        {
            String taskIdText = rawId == null ? "" : rawId.trim();
            if (taskIdText.isEmpty())
            {
                continue;
            }
            Long taskId = Long.valueOf(taskIdText);
            int rows = approve ? todoService.complete(taskId, comment) : todoService.back(taskId, comment);
            success = success || rows > 0;
        }
        return success;
    }
}
