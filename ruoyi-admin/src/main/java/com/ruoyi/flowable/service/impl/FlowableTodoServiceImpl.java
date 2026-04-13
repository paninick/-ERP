package com.ruoyi.flowable.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.flowable.domain.FlowTask;
import com.ruoyi.flowable.service.IFlowableTodoService;

@Service
public class FlowableTodoServiceImpl implements IFlowableTodoService
{
    @Override
    public List<FlowTask> selectTodoList(FlowTask task)
    {
        List<FlowTask> list = new ArrayList<>();
        
        FlowTask t1 = new FlowTask();
        t1.setTaskId(1001L);
        t1.setTaskName("采购申请审批");
        t1.setProcessName("采购申请流程");
        t1.setInitiator("zhangsan");
        t1.setInitiatorName("张三");
        t1.setNodeName("部门经理审批");
        t1.setPriority("1");
        t1.setStatus("待签收");
        list.add(t1);
        
        FlowTask t2 = new FlowTask();
        t2.setTaskId(1002L);
        t2.setTaskName("报销金额审批");
        t2.setProcessName("报销流程");
        t2.setInitiator("lisi");
        t2.setInitiatorName("李四");
        t2.setNodeName("财务审核");
        t2.setPriority("2");
        t2.setStatus("待签收");
        list.add(t2);
        
        return list;
    }

    @Override
    public List<FlowTask> selectFinishedList(FlowTask task)
    {
        List<FlowTask> list = new ArrayList<>();
        
        FlowTask t1 = new FlowTask();
        t1.setTaskId(2001L);
        t1.setTaskName("请假申请审批");
        t1.setProcessName("请假流程");
        t1.setInitiator("wangwu");
        t1.setInitiatorName("王五");
        t1.setNodeName("部门经理审批");
        t1.setPriority("1");
        t1.setStatus("已完成");
        t1.setEndTime("2026-04-06 09:30:00");
        t1.setDuration("30分钟");
        list.add(t1);
        
        return list;
    }

    @Override
    public List<FlowTask> selectMyProcessList(FlowTask task)
    {
        List<FlowTask> list = new ArrayList<>();
        
        FlowTask t1 = new FlowTask();
        t1.setTaskId(3001L);
        t1.setTaskName("我的请假申请");
        t1.setProcessName("请假流程");
        t1.setInitiator("admin");
        t1.setInitiatorName("系统管理员");
        t1.setNodeName("审批中");
        t1.setPriority("1");
        t1.setStatus("运行中");
        list.add(t1);
        
        return list;
    }

    @Override
    public int claim(Long taskId)
    {
        return 1;
    }

    @Override
    public int complete(Long taskId, String comment)
    {
        return 1;
    }

    @Override
    public int back(Long taskId, String comment)
    {
        return 1;
    }
}
