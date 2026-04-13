package com.ruoyi.flowable.service;

import java.util.List;
import com.ruoyi.flowable.domain.FlowTask;

public interface IFlowableTodoService
{
    public List<FlowTask> selectTodoList(FlowTask task);

    public List<FlowTask> selectFinishedList(FlowTask task);

    public List<FlowTask> selectMyProcessList(FlowTask task);

    public int claim(Long taskId);

    public int complete(Long taskId, String comment);

    public int back(Long taskId, String comment);
}
