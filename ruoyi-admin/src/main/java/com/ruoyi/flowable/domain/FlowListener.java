package com.ruoyi.flowable.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class FlowListener extends BaseEntity {
    private static final long serialVersionUID = 1L;
    
    private Long id;

    @Excel(name = "监听器名称")
    private String listenerName;

    @Excel(name = "监听器类型")
    private String listenerType;

    @Excel(name = "监听器值")
    private String listenerValue;

    @Excel(name = "值类型")
    private String valueType;

    @Excel(name = "实现类")
    private String implementation;

    @Excel(name = "事件类型")
    private String eventType;
    private String processDefId;
    private String status;
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getListenerName() {
        return listenerName;
    }

    public void setListenerName(String listenerName) {
        this.listenerName = listenerName;
    }

    public String getListenerType() {
        return listenerType;
    }

    public void setListenerType(String listenerType) {
        this.listenerType = listenerType;
    }

    public String getListenerValue() {
        return listenerValue;
    }

    public void setListenerValue(String listenerValue) {
        this.listenerValue = listenerValue;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getImplementation() {
        return implementation;
    }

    public void setImplementation(String implementation) {
        this.implementation = implementation;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getProcessDefId() {
        return processDefId;
    }

    public void setProcessDefId(String processDefId) {
        this.processDefId = processDefId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
