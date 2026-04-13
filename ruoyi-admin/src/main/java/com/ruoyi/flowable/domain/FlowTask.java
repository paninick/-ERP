package com.ruoyi.flowable.domain;

import com.ruoyi.common.core.domain.BaseEntity;

public class FlowTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long taskId;
    private String taskName;
    private String processDefinitionId;
    private String processInstanceId;
    private String processName;
    private String initiator;
    private String initiatorName;
    private String nodeName;
    private String priority;
    private String status;
    private String claimTime;
    private String endTime;
    private String duration;

    public Long getTaskId()
    {
        return taskId;
    }

    public void setTaskId(Long taskId)
    {
        this.taskId = taskId;
    }

    public String getTaskName()
    {
        return taskName;
    }

    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }

    public String getProcessDefinitionId()
    {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId)
    {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessInstanceId()
    {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId)
    {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessName()
    {
        return processName;
    }

    public void setProcessName(String processName)
    {
        this.processName = processName;
    }

    public String getInitiator()
    {
        return initiator;
    }

    public void setInitiator(String initiator)
    {
        this.initiator = initiator;
    }

    public String getInitiatorName()
    {
        return initiatorName;
    }

    public void setInitiatorName(String initiatorName)
    {
        this.initiatorName = initiatorName;
    }

    public String getNodeName()
    {
        return nodeName;
    }

    public void setNodeName(String nodeName)
    {
        this.nodeName = nodeName;
    }

    public String getPriority()
    {
        return priority;
    }

    public void setPriority(String priority)
    {
        this.priority = priority;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getClaimTime()
    {
        return claimTime;
    }

    public void setClaimTime(String claimTime)
    {
        this.claimTime = claimTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public String getDuration()
    {
        return duration;
    }

    public void setDuration(String duration)
    {
        this.duration = duration;
    }
}
