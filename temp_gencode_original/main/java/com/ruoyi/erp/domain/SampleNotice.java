package com.ruoyi.erp.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 打样通知对象 t_erp_sample_notice
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
public class SampleNotice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 打样类型 */
    @Excel(name = "打样类型")
    private String sampleType;

    /** 客户id */
    @Excel(name = "客户id")
    private Long customerId;

    /** 样品款式 */
    @Excel(name = "样品款式")
    private String styleType;

    /** 样品种类 */
    @Excel(name = "样品种类")
    private String sampleCategoryType;

    /** 款号 */
    @Excel(name = "款号")
    private String styleCode;

    /** 要求交期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "要求交期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDate;

    /** 紧急程度 */
    @Excel(name = "紧急程度")
    private String emergencyType;

    /** 款式图片 */
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sampleType", getSampleType())
            .append("customerId", getCustomerId())
            .append("styleType", getStyleType())
            .append("sampleCategoryType", getSampleCategoryType())
            .append("styleCode", getStyleCode())
            .append("dueDate", getDueDate())
            .append("emergencyType", getEmergencyType())
            .append("pictureUrl", getPictureUrl())
            .append("auditStatus", getAuditStatus())
            .append("auditBy", getAuditBy())
            .append("auditTime", getAuditTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
