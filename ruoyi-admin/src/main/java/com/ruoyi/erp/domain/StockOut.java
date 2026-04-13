package com.ruoyi.erp.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 出库单对象 t_erp_stock_out
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public class StockOut extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 出库单号 */
    @Excel(name = "出库单号")
    private String sn;

    /** 出库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date outDate;

    /** 类型1：材料出库, 2：成品出库 */
    @Excel(name = "类型1：材料出库, 2：成品出库")
    private Long outType;

    /** 出库人（领料人） */
    @Excel(name = "出库人", readConverterExp = "领=料人")
    private Long chargeUserId;

    /** 确认状态（0待确认, 1已确认） */
    @Excel(name = "确认状态", readConverterExp = "0=待确认,,1=已确认")
    private String confirmStatus;

    /** 关联采购单id */
    private Long purchaseId;

    /** 关联采购单号 */
    @Excel(name = "关联采购单号")
    private String purchaseSn;

    /** 生产计划id（成品入库时） */
    private Long planId;

    /** 大货款号 */
    @Excel(name = "大货款号")
    private String bulkOrderNo;

    /** 出库简介 */
    @Excel(name = "出库简介")
    private String outDescription;

    /** 申请人 */
    @Excel(name = "申请人")
    private String applicant;

    /** 申请日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申请日期", width = 30, dateFormat = "yyyy-MM-dd")
    private java.util.Date applyDate;

    /** 确认人 */
    @Excel(name = "确认人")
    private String confirmBy;

    /** 确认时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "确认时间", width = 30, dateFormat = "yyyy-MM-dd")
    private java.util.Date confirmTime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getSn() {
        return sn;
    }
    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public Date getOutDate() {
        return outDate;
    }
    public void setOutType(Long outType) {
        this.outType = outType;
    }

    public Long getOutType() {
        return outType;
    }
    public void setChargeUserId(Long chargeUserId) {
        this.chargeUserId = chargeUserId;
    }

    public Long getChargeUserId() {
        return chargeUserId;
    }
    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }
    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }
    public void setPurchaseSn(String purchaseSn) {
        this.purchaseSn = purchaseSn;
    }

    public String getPurchaseSn() {
        return purchaseSn;
    }
    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getPlanId() {
        return planId;
    }
    public void setBulkOrderNo(String bulkOrderNo) {
        this.bulkOrderNo = bulkOrderNo;
    }

    public String getBulkOrderNo() {
        return bulkOrderNo;
    }
    public void setOutDescription(String outDescription) {
        this.outDescription = outDescription;
    }

    public String getOutDescription() {
        return outDescription;
    }
    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getApplicant() {
        return applicant;
    }
    public void setApplyDate(java.util.Date applyDate) {
        this.applyDate = applyDate;
    }

    public java.util.Date getApplyDate() {
        return applyDate;
    }
    public void setConfirmBy(String confirmBy) {
        this.confirmBy = confirmBy;
    }

    public String getConfirmBy() {
        return confirmBy;
    }
    public void setConfirmTime(java.util.Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public java.util.Date getConfirmTime() {
        return confirmTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sn", getSn())
            .append("outDate", getOutDate())
            .append("outType", getOutType())
            .append("chargeUserId", getChargeUserId())
            .append("confirmStatus", getConfirmStatus())
            .append("purchaseId", getPurchaseId())
            .append("purchaseSn", getPurchaseSn())
            .append("planId", getPlanId())
            .append("bulkOrderNo", getBulkOrderNo())
            .append("outDescription", getOutDescription())
            .append("applicant", getApplicant())
            .append("applyDate", getApplyDate())
            .append("confirmBy", getConfirmBy())
            .append("confirmTime", getConfirmTime())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
