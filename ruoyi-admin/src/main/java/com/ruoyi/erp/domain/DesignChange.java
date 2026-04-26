package com.ruoyi.erp.domain;

import java.util.Date;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class DesignChange extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "销售订单ID")
    private Long salesOrderId;

    @Excel(name = "销售单号")
    private String salesNo;

    @Excel(name = "变更类型")
    private String changeType;

    @Excel(name = "变更前")
    private String beforeValue;

    @Excel(name = "变更后")
    private String afterValue;

    @Excel(name = "变更原因")
    private String changeReason;

    @Excel(name = "申请人")
    private String changeBy;

    private Date changeTime;

    @Excel(name = "审批人")
    private String approvedBy;

    private Date approvedTime;

    @Excel(name = "状态")
    private String status;

    private Long factoryId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getSalesOrderId() { return salesOrderId; }
    public void setSalesOrderId(Long v) { this.salesOrderId = v; }
    public String getSalesNo() { return salesNo; }
    public void setSalesNo(String v) { this.salesNo = v; }
    public String getChangeType() { return changeType; }
    public void setChangeType(String v) { this.changeType = v; }
    public String getBeforeValue() { return beforeValue; }
    public void setBeforeValue(String v) { this.beforeValue = v; }
    public String getAfterValue() { return afterValue; }
    public void setAfterValue(String v) { this.afterValue = v; }
    public String getChangeReason() { return changeReason; }
    public void setChangeReason(String v) { this.changeReason = v; }
    public String getChangeBy() { return changeBy; }
    public void setChangeBy(String v) { this.changeBy = v; }
    public Date getChangeTime() { return changeTime; }
    public void setChangeTime(Date v) { this.changeTime = v; }
    public String getApprovedBy() { return approvedBy; }
    public void setApprovedBy(String v) { this.approvedBy = v; }
    public Date getApprovedTime() { return approvedTime; }
    public void setApprovedTime(Date v) { this.approvedTime = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
    public Long getFactoryId() { return factoryId; }
    public void setFactoryId(Long v) { this.factoryId = v; }
}
