package com.ruoyi.flowable.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.flowable.domain.FlowTask;
import com.ruoyi.flowable.service.IFlowableTodoService;

@RestController
@RequestMapping("/flowable/task/finished")
public class FlowableFinishedController extends BaseController
{
    @Autowired
    private IFlowableTodoService todoService;

    @GetMapping("/list")
    public TableDataInfo list(FlowTask task)
    {
        startPage();
        List<FlowTask> list = todoService.selectFinishedList(task);
        return getDataTable(list);
    }
}
