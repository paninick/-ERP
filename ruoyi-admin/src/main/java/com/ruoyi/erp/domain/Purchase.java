package com.ruoyi.erp.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.erp.domain.base.ErpBaseBillEntity;

/**
 * 采购单对象 t_erp_purchase
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public class Purchase extends ErpBaseBillEntity {

    /** 编码 */
    private Long id;

    /** 采购单号 */
    @Excel(name = "采购单号")
    private String sn;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 大货款号 */
    @Excel(name = "大货款号")
    private String bulkOrderNo;

    /** 说明 */
    @Excel(name = "说明")
    private String description;

    /** 采购日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "采购日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date purchaseDate;

    /** 预计到货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预计到货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expectedDeliveryDate;

    /** 采购员(文本) */
    @Excel(name = "采购员")
    private String purchaseName;

    /** 采购员ID */
    private Long purchaseUserId;

    /** 确认时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "确认时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date confirmTime;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 供应商编号 */
    @Excel(name = "供应商编号")
    private Long supplierId;

    /** 订单金额 */
    private BigDecimal amount;

    /** 配送类型1供应商配送，2我司派车 */
    private String deliveryType;

    /** 状态（0待确认, 1已确认） */
    @Excel(name = "状态", readConverterExp = "0=待确认,,1=已确认")
    private String confirmStatus;

    /** 是否已付款 */
    private Integer isPay;

    /** 付款日期 */
    private Date payDate;

    /** 付款金额 */
    private BigDecimal payAmount;

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
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    public void setBulkOrderNo(String bulkOrderNo) {
        this.bulkOrderNo = bulkOrderNo;
    }
    public String getBulkOrderNo() {
        return bulkOrderNo;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }
    public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public Date getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }
    public void setPurchaseUserId(Long purchaseUserId) {
        this.purchaseUserId = purchaseUserId;
    }

    public Long getPurchaseUserId() {
        return purchaseUserId;
    }
    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
    }
    public String getPurchaseName() {
        return purchaseName;
    }
    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }
    public Date getConfirmTime() {
        return confirmTime;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() {
        return supplierId;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryType() {
        return deliveryType;
    }
    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }
    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public Integer getIsPay() {
        return isPay;
    }
    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getPayDate() {
        return payDate;
    }
    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sn", getSn())
            .append("type", getType())
            .append("bulkOrderNo", getBulkOrderNo())
            .append("description", getDescription())
            .append("purchaseDate", getPurchaseDate())
            .append("expectedDeliveryDate", getExpectedDeliveryDate())
            .append("purchaseName", getPurchaseName())
            .append("purchaseUserId", getPurchaseUserId())
            .append("confirmTime", getConfirmTime())
            .append("status", getStatus())
            .append("supplierId", getSupplierId())
            .append("amount", getAmount())
            .append("deliveryType", getDeliveryType())
            .append("confirmStatus", getConfirmStatus())
            .append("isPay", getIsPay())
            .append("payDate", getPayDate())
            .append("payAmount", getPayAmount())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
