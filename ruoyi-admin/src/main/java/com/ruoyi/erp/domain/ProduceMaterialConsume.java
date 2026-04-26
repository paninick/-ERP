package com.ruoyi.erp.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Production material consume record.
 */
public class ProduceMaterialConsume extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "producePlanId")
    private Long producePlanId;

    @Excel(name = "jobId")
    private Long jobId;

    @Excel(name = "jobProcessId")
    private Long jobProcessId;

    @Excel(name = "reportLogId")
    private Long reportLogId;

    @Excel(name = "stockOutId")
    private Long stockOutId;

    @Excel(name = "stockOutItemId")
    private Long stockOutItemId;

    @Excel(name = "batchNo")
    private String batchNo;

    @Excel(name = "orderId")
    private Long orderId;

    @Excel(name = "processId")
    private Long processId;

    @Excel(name = "processName")
    private String processName;

    @Excel(name = "materialId")
    private Long materialId;

    @Excel(name = "materialCode")
    private String materialCode;

    @Excel(name = "materialName")
    private String materialName;

    @Excel(name = "materialType")
    private String materialType;

    @Excel(name = "bomQty")
    private BigDecimal bomQty;

    @Excel(name = "actualQty")
    private BigDecimal actualQty;

    @Excel(name = "standardLossRate")
    private BigDecimal standardLossRate;

    @Excel(name = "limitLossQty")
    private BigDecimal limitLossQty;

    @Excel(name = "actualLossQty")
    private BigDecimal actualLossQty;

    @Excel(name = "isOverLimit")
    private String isOverLimit;

    @Excel(name = "overLimitReason")
    private String overLimitReason;

    @Excel(name = "approvalStatus")
    private String approvalStatus;

    private Long approvalById;
    private String approvalByName;
    private Date approvalTime;
    private String approvalRemark;
    private String unit;

    @Excel(name = "unitPrice")
    private BigDecimal unitPrice;

    @Excel(name = "theoreticalCost")
    private BigDecimal theoreticalCost;

    @Excel(name = "actualCost")
    private BigDecimal actualCost;

    @Excel(name = "costDiff")
    private BigDecimal costDiff;

    /** 单位成本 */
    @Excel(name = "单位成本")
    private BigDecimal unitCost;

    /** 消耗成本金额 */
    @Excel(name = "消耗成本")
    private BigDecimal costAmount;

    /** 来源 MANUAL/REPORT_LOG/STOCK_OUT */
    private String eventSource;

    public BigDecimal getUnitCost() { return unitCost; }
    public void setUnitCost(BigDecimal unitCost) { this.unitCost = unitCost; }
    public BigDecimal getCostAmount() { return costAmount; }
    public void setCostAmount(BigDecimal costAmount) { this.costAmount = costAmount; }
    public String getEventSource() { return eventSource; }
    public void setEventSource(String eventSource) { this.eventSource = eventSource; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProducePlanId() {
        return producePlanId;
    }

    public void setProducePlanId(Long producePlanId) {
        this.producePlanId = producePlanId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getJobProcessId() {
        return jobProcessId;
    }

    public void setJobProcessId(Long jobProcessId) {
        this.jobProcessId = jobProcessId;
    }

    public Long getReportLogId() {
        return reportLogId;
    }

    public void setReportLogId(Long reportLogId) {
        this.reportLogId = reportLogId;
    }

    public Long getStockOutId() {
        return stockOutId;
    }

    public void setStockOutId(Long stockOutId) {
        this.stockOutId = stockOutId;
    }

    public Long getStockOutItemId() {
        return stockOutItemId;
    }

    public void setStockOutItemId(Long stockOutItemId) {
        this.stockOutItemId = stockOutItemId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public BigDecimal getBomQty() {
        return bomQty;
    }

    public void setBomQty(BigDecimal bomQty) {
        this.bomQty = bomQty;
    }

    public BigDecimal getActualQty() {
        return actualQty;
    }

    public void setActualQty(BigDecimal actualQty) {
        this.actualQty = actualQty;
    }

    public BigDecimal getStandardLossRate() {
        return standardLossRate;
    }

    public void setStandardLossRate(BigDecimal standardLossRate) {
        this.standardLossRate = standardLossRate;
    }

    public BigDecimal getLimitLossQty() {
        return limitLossQty;
    }

    public void setLimitLossQty(BigDecimal limitLossQty) {
        this.limitLossQty = limitLossQty;
    }

    public BigDecimal getActualLossQty() {
        return actualLossQty;
    }

    public void setActualLossQty(BigDecimal actualLossQty) {
        this.actualLossQty = actualLossQty;
    }

    public String getIsOverLimit() {
        return isOverLimit;
    }

    public void setIsOverLimit(String isOverLimit) {
        this.isOverLimit = isOverLimit;
    }

    public String getOverLimitReason() {
        return overLimitReason;
    }

    public void setOverLimitReason(String overLimitReason) {
        this.overLimitReason = overLimitReason;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Long getApprovalById() {
        return approvalById;
    }

    public void setApprovalById(Long approvalById) {
        this.approvalById = approvalById;
    }

    public String getApprovalByName() {
        return approvalByName;
    }

    public void setApprovalByName(String approvalByName) {
        this.approvalByName = approvalByName;
    }

    public Date getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Date approvalTime) {
        this.approvalTime = approvalTime;
    }

    public String getApprovalRemark() {
        return approvalRemark;
    }

    public void setApprovalRemark(String approvalRemark) {
        this.approvalRemark = approvalRemark;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTheoreticalCost() {
        return theoreticalCost;
    }

    public void setTheoreticalCost(BigDecimal theoreticalCost) {
        this.theoreticalCost = theoreticalCost;
    }

    public BigDecimal getActualCost() {
        return actualCost;
    }

    public void setActualCost(BigDecimal actualCost) {
        this.actualCost = actualCost;
    }

    public BigDecimal getCostDiff() {
        return costDiff;
    }

    public void setCostDiff(BigDecimal costDiff) {
        this.costDiff = costDiff;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("producePlanId", getProducePlanId())
            .append("jobId", getJobId())
            .append("jobProcessId", getJobProcessId())
            .append("reportLogId", getReportLogId())
            .append("stockOutId", getStockOutId())
            .append("stockOutItemId", getStockOutItemId())
            .append("batchNo", getBatchNo())
            .append("orderId", getOrderId())
            .append("processId", getProcessId())
            .append("processName", getProcessName())
            .append("materialId", getMaterialId())
            .append("materialCode", getMaterialCode())
            .append("materialName", getMaterialName())
            .append("materialType", getMaterialType())
            .append("bomQty", getBomQty())
            .append("actualQty", getActualQty())
            .append("standardLossRate", getStandardLossRate())
            .append("limitLossQty", getLimitLossQty())
            .append("actualLossQty", getActualLossQty())
            .append("isOverLimit", getIsOverLimit())
            .append("overLimitReason", getOverLimitReason())
            .append("approvalStatus", getApprovalStatus())
            .append("approvalById", getApprovalById())
            .append("approvalByName", getApprovalByName())
            .append("approvalTime", getApprovalTime())
            .append("approvalRemark", getApprovalRemark())
            .append("unit", getUnit())
            .append("unitPrice", getUnitPrice())
            .append("theoreticalCost", getTheoreticalCost())
            .append("actualCost", getActualCost())
            .append("costDiff", getCostDiff())
            .append("unitCost", getUnitCost())
            .append("costAmount", getCostAmount())
            .append("eventSource", getEventSource())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
