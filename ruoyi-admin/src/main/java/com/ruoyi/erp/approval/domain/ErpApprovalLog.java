package com.ruoyi.erp.approval.domain;

import java.util.Date;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class ErpApprovalLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "业务类型不能为空")
    @Excel(name = "业务类型")
    private String businessType;

    @NotNull(message = "业务ID不能为空")
    private Long businessId;

    @Excel(name = "业务单号")
    private String businessNo;

    @NotBlank(message = "审批节点不能为空")
    @Excel(name = "审批节点")
    private String nodeCode;

    @NotBlank(message = "操作类型不能为空")
    @Excel(name = "操作类型")
    private String actionType;

    @Excel(name = "变更前状态")
    private String fromStatus;

    @Excel(name = "变更后状态")
    private String toStatus;

    @NotBlank(message = "操作人不能为空")
    @Excel(name = "操作人")
    private String actionBy;

    @Excel(name = "操作时间")
    private Date actionTime;

    @Excel(name = "审批意见")
    private String actionRemark;

    private Long factoryId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBusinessType() { return businessType; }
    public void setBusinessType(String businessType) { this.businessType = businessType; }
    public Long getBusinessId() { return businessId; }
    public void setBusinessId(Long businessId) { this.businessId = businessId; }
    public String getBusinessNo() { return businessNo; }
    public void setBusinessNo(String businessNo) { this.businessNo = businessNo; }
    public String getNodeCode() { return nodeCode; }
    public void setNodeCode(String nodeCode) { this.nodeCode = nodeCode; }
    public String getActionType() { return actionType; }
    public void setActionType(String actionType) { this.actionType = actionType; }
    public String getFromStatus() { return fromStatus; }
    public void setFromStatus(String fromStatus) { this.fromStatus = fromStatus; }
    public String getToStatus() { return toStatus; }
    public void setToStatus(String toStatus) { this.toStatus = toStatus; }
    public String getActionBy() { return actionBy; }
    public void setActionBy(String actionBy) { this.actionBy = actionBy; }
    public Date getActionTime() { return actionTime; }
    public void setActionTime(Date actionTime) { this.actionTime = actionTime; }
    public String getActionRemark() { return actionRemark; }
    public void setActionRemark(String actionRemark) { this.actionRemark = actionRemark; }
    public Long getFactoryId() { return factoryId; }
    public void setFactoryId(Long factoryId) { this.factoryId = factoryId; }
}
