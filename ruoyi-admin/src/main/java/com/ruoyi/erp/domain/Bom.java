package com.ruoyi.erp.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class Bom extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "打样编号")
    private String sampleNo;

    @Excel(name = "打样类型")
    private String sampleType;

    @Excel(name = "客户id")
    private Long customerId;

    @Excel(name = "款式大类")
    private String styleCategory;

    @Excel(name = "款式小类")
    private String styleSubCategory;

    @Excel(name = "样品款式")
    private String styleType;

    @Excel(name = "样品种类")
    private String sampleCategoryType;

    @Excel(name = "打样款号")
    private String styleCode;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "要求交期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDate;

    @Excel(name = "紧急程度")
    private String emergencyType;

    @Excel(name = "款式图片")
    private String pictureUrl;

    @Excel(name = "审批状态")
    private String auditStatus;

    @Excel(name = "审批人id")
    private String auditBy;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date auditTime;

    @Excel(name = "流程实例id")
    private String processInstanceId;

    @Excel(name = "大货款号")
    private String bulkOrderNo;

    private String customerName;

    private String auditByNickName;

    @Excel(name = "业务员ID")
    private Long salesId;

    @Excel(name = "业务员")
    private String salesName;

    @Excel(name = "进行状态")
    private String progressStatus;

    @Excel(name = "工艺书编号")
    private String techNo;

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

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }

    public String getTechNo() {
        return techNo;
    }

    public void setTechNo(String techNo) {
        this.techNo = techNo;
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
            .append("progressStatus", getProgressStatus())
            .append("techNo", getTechNo())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
