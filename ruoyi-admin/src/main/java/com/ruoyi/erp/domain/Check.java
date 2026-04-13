package com.ruoyi.erp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 大货核版对象 t_erp_check
 * 
 * @author ruoyi
 * @date 2026-04-10
 */
public class Check extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 核版ID */
    private Long checkId;

    /** 核版编号 */
    @Excel(name = "核版编号")
    private String checkNo;

    /** 打样编号 */
    @Excel(name = "打样编号")
    private String sampleNo;

    /** 工艺书编号 */
    @Excel(name = "工艺书编号")
    private String techNo;

    /** 打样类型 */
    @Excel(name = "打样类型")
    private String sampleType;

    /** 样品款式 */
    @Excel(name = "样品款式")
    private String styleType;

    /** 样品种类 */
    @Excel(name = "样品种类")
    private String sampleCategoryType;

    /** 款号 */
    @Excel(name = "款号")
    private String styleNo;

    /** 大货款号 */
    @Excel(name = "大货款号")
    private String bulkOrderNo;

    /** 客户ID */
    private Long customerId;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String customerName;

    /** 业务员ID */
    private Long salesId;

    /** 业务员名称 */
    @Excel(name = "业务员")
    private String salesName;

    /** 要求交期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "要求交期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDate;

    /** 进行状态 */
    @Excel(name = "进行状态")
    private String progressStatus;

    /** 审批状态 */
    @Excel(name = "审批状态")
    private String auditStatus;

    /** 审批人ID */
    private Long auditBy;

    /** 审批人名称 */
    @Excel(name = "审批人")
    private String auditByName;

    /** 审批时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date auditTime;

    /** 数量 */
    @Excel(name = "数量")
    private Integer quantity;

    /** 核版说明 */
    @Excel(name = "核版说明")
    private String checkDescription;

    /** 核版结果 */
    @Excel(name = "核版结果")
    private String checkResult;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    public void setCheckId(Long checkId)
    {
        this.checkId = checkId;
    }

    public Long getCheckId()
    {
        return checkId;
    }
    public void setCheckNo(String checkNo)
    {
        this.checkNo = checkNo;
    }

    public String getCheckNo()
    {
        return checkNo;
    }
    public void setSampleNo(String sampleNo)
    {
        this.sampleNo = sampleNo;
    }

    public String getSampleNo()
    {
        return sampleNo;
    }
    public void setTechNo(String techNo)
    {
        this.techNo = techNo;
    }

    public String getTechNo()
    {
        return techNo;
    }
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }

    public String getSampleType()
    {
        return sampleType;
    }
    public void setStyleType(String styleType)
    {
        this.styleType = styleType;
    }

    public String getStyleType()
    {
        return styleType;
    }
    public void setSampleCategoryType(String sampleCategoryType)
    {
        this.sampleCategoryType = sampleCategoryType;
    }

    public String getSampleCategoryType()
    {
        return sampleCategoryType;
    }
    public void setStyleNo(String styleNo)
    {
        this.styleNo = styleNo;
    }

    public String getStyleNo()
    {
        return styleNo;
    }
    public void setBulkOrderNo(String bulkOrderNo)
    {
        this.bulkOrderNo = bulkOrderNo;
    }

    public String getBulkOrderNo()
    {
        return bulkOrderNo;
    }
    public void setCustomerId(Long customerId)
    {
        this.customerId = customerId;
    }

    public Long getCustomerId()
    {
        return customerId;
    }
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getCustomerName()
    {
        return customerName;
    }
    public void setSalesId(Long salesId)
    {
        this.salesId = salesId;
    }

    public Long getSalesId()
    {
        return salesId;
    }
    public void setSalesName(String salesName)
    {
        this.salesName = salesName;
    }

    public String getSalesName()
    {
        return salesName;
    }
    public void setDueDate(Date dueDate)
    {
        this.dueDate = dueDate;
    }

    public Date getDueDate()
    {
        return dueDate;
    }
    public void setProgressStatus(String progressStatus)
    {
        this.progressStatus = progressStatus;
    }

    public String getProgressStatus()
    {
        return progressStatus;
    }
    public void setAuditStatus(String auditStatus)
    {
        this.auditStatus = auditStatus;
    }

    public String getAuditStatus()
    {
        return auditStatus;
    }
    public void setAuditBy(Long auditBy)
    {
        this.auditBy = auditBy;
    }

    public Long getAuditBy()
    {
        return auditBy;
    }
    public void setAuditByName(String auditByName)
    {
        this.auditByName = auditByName;
    }

    public String getAuditByName()
    {
        return auditByName;
    }
    public void setAuditTime(Date auditTime)
    {
        this.auditTime = auditTime;
    }

    public Date getAuditTime()
    {
        return auditTime;
    }
    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    public Integer getQuantity()
    {
        return quantity;
    }
    public void setCheckDescription(String checkDescription)
    {
        this.checkDescription = checkDescription;
    }

    public String getCheckDescription()
    {
        return checkDescription;
    }
    public void setCheckResult(String checkResult)
    {
        this.checkResult = checkResult;
    }

    public String getCheckResult()
    {
        return checkResult;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("checkId", getCheckId())
            .append("checkNo", getCheckNo())
            .append("sampleNo", getSampleNo())
            .append("techNo", getTechNo())
            .append("sampleType", getSampleType())
            .append("styleType", getStyleType())
            .append("sampleCategoryType", getSampleCategoryType())
            .append("styleNo", getStyleNo())
            .append("bulkOrderNo", getBulkOrderNo())
            .append("customerId", getCustomerId())
            .append("customerName", getCustomerName())
            .append("salesId", getSalesId())
            .append("salesName", getSalesName())
            .append("dueDate", getDueDate())
            .append("progressStatus", getProgressStatus())
            .append("auditStatus", getAuditStatus())
            .append("auditBy", getAuditBy())
            .append("auditByName", getAuditByName())
            .append("auditTime", getAuditTime())
            .append("quantity", getQuantity())
            .append("checkDescription", getCheckDescription())
            .append("checkResult", getCheckResult())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
