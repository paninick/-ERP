package com.ruoyi.flowable.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class FlowableDefinition extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "流程ID")
    private String id;

    @Excel(name = "流程名称")
    private String name;

    @Excel(name = "流程KEY")
    private String key;

    @Excel(name = "版本")
    private Integer version;

    @Excel(name = "状态", readConverterExp = "true=激活,false=挂起")
    private Boolean suspended;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "部署时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date deploymentTime;

    @Excel(name = "描述")
    private String description;

    @Excel(name = "分类")
    private String category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public Date getDeploymentTime() {
        return deploymentTime;
    }

    public void setDeploymentTime(Date deploymentTime) {
        this.deploymentTime = deploymentTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
