package com.ruoyi.erp.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.erp.domain.base.ErpBaseBillEntity;

/**
 * 打样通知对象 t_erp_sample_notice
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public class SampleNotice extends ErpBaseBillEntity {

    /** 主键 */
    private Long id;

    /** 打样编号 */
    @Excel(name = "打样编号")
    private String sampleNo;
    private Integer roundNumber;
    private String customerApproved;
    private String customerFeedback;
    private String factoryConfirmed;
    private Date factoryConfirmDate;

    /** 打样类型 */
    @Excel(name = "打样类型")
    private String sampleType;

    /** 客户id */
    @Excel(name = "客户id")
    private Long customerId;

    /** 款式大类 */
    @Excel(name = "款式大类")
    private String styleCategory;

    /** 款式小类 */
    @Excel(name = "款式小类")
    private String styleSubCategory;

    /** 样品款式 */
    @Excel(name = "样品款式")
    private String styleType;

    /** 样品种类 */
    @Excel(name = "样品种类")
    private String sampleCategoryType;

    /** 打样款号 */
    @Excel(name = "打样款号")
    private String styleCode;

    /** 要求交期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "要求交期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDate;

    /** 紧急程度 */
    @Excel(name = "紧急程度")
    private String emergencyType;

    /** 款式图片 */
    @Excel(name = "款式图片")
    private String pictureUrl;

    /** 审批状态 */
    @Excel(name = "审批状态")
    private String auditStatus;

    /** 审批人id */
    @Excel(name = "审批人id")
    private String auditBy;

    /** 审批时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date auditTime;

    /** 流程实例id */
    @Excel(name = "流程实例id")
    private String processInstanceId;

    /** 大货款号 */
    @Excel(name = "大货款号")
    private String bulkOrderNo;

    /** 客户名称 */
    private String customerName;

    /** 审批人昵称 */
    private String auditByNickName;

    /** 业务员ID */
    @Excel(name = "业务员ID")
    private Long salesId;

    /** 业务员名称 */
    @Excel(name = "业务员")
    private String salesName;

    /** 颜色确认状态 01待提交 02客户签样 03车间比对 04允许生产 */
    @Excel(name = "颜色确认状态")
    private String colorConfirmStatus;

    /** 确认人ID */
    private Long confirmById;

    /** 确认时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date confirmTime;

    /** 颜色对比照片（多个逗号分隔） */
    private String colorConfirmImages;

    /** 确认人数字签名图片 */
    private String digitalSignature;

    /** 客户允差ΔE */
    private java.math.BigDecimal customerAcceptDeltaE;

    /** 拍摄光源类型 */
    private String lightSourceType;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getSampleType() {
        return sampleType;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }
    public void setStyleType(String styleType) {
        this.styleType = styleType;
    }

    public String getStyleType() {
        return styleType;
    }
    public void setSampleCategoryType(String sampleCategoryType) {
        this.sampleCategoryType = sampleCategoryType;
    }

    public String getSampleCategoryType() {
        return sampleCategoryType;
    }
    public void setStyleCode(String styleCode) {
        this.styleCode = styleCode;
    }

    public String getStyleCode() {
        return styleCode;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }
    public void setEmergencyType(String emergencyType) {
        this.emergencyType = emergencyType;
    }

    public String getEmergencyType() {
        return emergencyType;
    }
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }
    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditStatus() {
        return auditStatus;
    }
    public void setAuditBy(String auditBy) {
        this.auditBy = auditBy;
    }

    public String getAuditBy() {
        return auditBy;
    }
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public Integer getRoundNumber() { return roundNumber; }
    public void setRoundNumber(Integer v) { this.roundNumber = v; }
    public String getCustomerApproved() { return customerApproved; }
    public void setCustomerApproved(String v) { this.customerApproved = v; }
    public String getCustomerFeedback() { return customerFeedback; }
    public void setCustomerFeedback(String v) { this.customerFeedback = v; }
    public String getFactoryConfirmed() { return factoryConfirmed; }
    public void setFactoryConfirmed(String v) { this.factoryConfirmed = v; }
    public Date getFactoryConfirmDate() { return factoryConfirmDate; }
    public void setFactoryConfirmDate(Date v) { this.factoryConfirmDate = v; }

    public String getStyleCategory() {
        return styleCategory;
    }

    public void setStyleCategory(String styleCategory) {
        this.styleCategory = styleCategory;
    }

    public String getStyleSubCategory() {
        return styleSubCategory;
    }

    public void setStyleSubCategory(String styleSubCategory) {
        this.styleSubCategory = styleSubCategory;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getBulkOrderNo() {
        return bulkOrderNo;
    }

    public void setBulkOrderNo(String bulkOrderNo) {
        this.bulkOrderNo = bulkOrderNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAuditByNickName() {
        return auditByNickName;
    }

    public void setAuditByNickName(String auditByNickName) {
        this.auditByNickName = auditByNickName;
    }

    public Long getSalesId() {
        return salesId;
    }

    public void setSalesId(Long salesId) {
        this.salesId = salesId;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public String getColorConfirmStatus() {
        return colorConfirmStatus;
    }

    public void setColorConfirmStatus(String colorConfirmStatus) {
        this.colorConfirmStatus = colorConfirmStatus;
    }

    public Long getConfirmById() {
        return confirmById;
    }

    public void setConfirmById(Long confirmById) {
        this.confirmById = confirmById;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getColorConfirmImages() {
        return colorConfirmImages;
    }

    public void setColorConfirmImages(String colorConfirmImages) {
        this.colorConfirmImages = colorConfirmImages;
    }

    public String getDigitalSignature() {
        return digitalSignature;
    }

    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    public java.math.BigDecimal getCustomerAcceptDeltaE() {
        return customerAcceptDeltaE;
    }

    public void setCustomerAcceptDeltaE(java.math.BigDecimal customerAcceptDeltaE) {
        this.customerAcceptDeltaE = customerAcceptDeltaE;
    }

    public String getLightSourceType() {
        return lightSourceType;
    }

    public void setLightSourceType(String lightSourceType) {
        this.lightSourceType = lightSourceType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sampleNo", getSampleNo())
            .append("sampleType", getSampleType())
            .append("customerId", getCustomerId())
            .append("customerName", getCustomerName())
            .append("styleCategory", getStyleCategory())
            .append("styleSubCategory", getStyleSubCategory())
            .append("styleType", getStyleType())
            .append("sampleCategoryType", getSampleCategoryType())
            .append("styleCode", getStyleCode())
            .append("dueDate", getDueDate())
            .append("emergencyType", getEmergencyType())
            .append("pictureUrl", getPictureUrl())
            .append("auditStatus", getAuditStatus())
            .append("auditBy", getAuditBy())
            .append("auditByNickName", getAuditByNickName())
            .append("auditTime", getAuditTime())
            .append("processInstanceId", getProcessInstanceId())
            .append("bulkOrderNo", getBulkOrderNo())
            .append("salesId", getSalesId())
            .append("salesName", getSalesName())
            .append("colorConfirmStatus", getColorConfirmStatus())
            .append("confirmById", getConfirmById())
            .append("confirmTime", getConfirmTime())
            .append("colorConfirmImages", getColorConfirmImages())
            .append("digitalSignature", getDigitalSignature())
            .append("customerAcceptDeltaE", getCustomerAcceptDeltaE())
            .append("lightSourceType", getLightSourceType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
