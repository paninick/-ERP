package com.ruoyi.erp.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 生产物料消耗记录对象 t_erp_produce_material_consume
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public class ProduceMaterialConsume extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 消耗记录ID */
    private Long id;

    /** 生产计划ID */
    @Excel(name = "生产计划ID")
    private Long producePlanId;

    /** 订单ID */
    @Excel(name = "订单ID")
    private Long orderId;

    /** 工序ID */
    @Excel(name = "工序ID")
    private Long processId;

    /** 工序名称 */
    @Excel(name = "工序名称")
    private String processName;

    /** 物料ID */
    @Excel(name = "物料ID")
    private Long materialId;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String materialCode;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialName;

    /** BOM理论用量 */
    @Excel(name = "BOM理论用量")
    private BigDecimal bomQty;

    /** 实际领用数量 */
    @Excel(name = "实际领用数量")
    private BigDecimal actualQty;

    /** 标准损耗率(%) */
    @Excel(name = "标准损耗率")
    private BigDecimal standardLossRate;

    /** 理论损耗限额 */
    @Excel(name = "理论损耗限额")
    private BigDecimal limitLossQty;

    /** 实际损耗数量 */
    @Excel(name = "实际损耗数量")
    private BigDecimal actualLossQty;

    /** 是否超限额 */
    @Excel(name = "是否超限额", readConverterExp = "0=否,1=是")
    private String isOverLimit;

    /** 超限额原因 */
    @Excel(name = "超限额原因")
    private String overLimitReason;

    /** 是否审批状态 0无需审批 1待审批 2已批准 2已拒绝 */
    @Excel(name = "审批状态", readConverterExp = "0=无需,1=待审,2=批准,3=拒绝")
    private String approvalStatus;

    /** 审批人ID */
    private Long approvalById;

    /** 审批人姓名 */
    private String approvalByName;

    /** 审批时间 */
    private java.util.Date approvalTime;

    /** 审批备注 */
    private String approvalRemark;

    /** 物料单位 */
    private String unit;

    /** 备注 */
    private String remark;

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

    public java.util.Date getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(java.util.Date approvalTime) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("producePlanId", getProducePlanId())
            .append("orderId", getOrderId())
            .append("processId", getProcessId())
            .append("processName", getProcessName())
            .append("materialId", getMaterialId())
            .append("materialCode", getMaterialCode())
            .append("materialName", getMaterialName())
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
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
