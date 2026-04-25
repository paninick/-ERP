package com.ruoyi.erp.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.erp.domain.base.ErpBaseBillEntity;

/**
 * 入库单对象 t_erp_stock_in
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public class StockIn extends ErpBaseBillEntity {

    /** 主键 */
    private Long id;

    /** 入库单号 */
    @NotBlank(message = "入库单号不能为空")
    @Excel(name = "入库单号")
    private String sn;

    /** 入库日期 */
    @NotNull(message = "入库日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inDate;

    /** 类型1：采购入库, 2：其他入库 */
    @Excel(name = "类型1：采购入库, 2：其他入库")
    private Long inType;

    /** 入库人 */
    @Excel(name = "入库人")
    private Long chargeUserId;

    /** 入库金额，单位：元 */
    private BigDecimal totalPrice;

    /** 确认状态（0待确认, 1已确认） */
    @Excel(name = "确认状态", readConverterExp = "0=待确认,,1=已确认")
    private String confirmStatus;

    /** 关联采购单id */
    private Long purchaseId;

    /** 关联采购单号 */
    @Excel(name = "关联采购单号")
    private String purchaseSn;

    /** 大货款号 */
    @Excel(name = "大货款号")
    private String bulkOrderNo;

    /** 入库简介 */
    @Excel(name = "入库简介")
    private String inDescription;

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
    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Date getInDate() {
        return inDate;
    }
    public void setInType(Long inType) {
        this.inType = inType;
    }

    public Long getInType() {
        return inType;
    }
    public void setChargeUserId(Long chargeUserId) {
        this.chargeUserId = chargeUserId;
    }

    public Long getChargeUserId() {
        return chargeUserId;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
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
    public void setBulkOrderNo(String bulkOrderNo) {
        this.bulkOrderNo = bulkOrderNo;
    }

    public String getBulkOrderNo() {
        return bulkOrderNo;
    }
    public void setInDescription(String inDescription) {
        this.inDescription = inDescription;
    }

    public String getInDescription() {
        return inDescription;
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
            .append("inDate", getInDate())
            .append("inType", getInType())
            .append("chargeUserId", getChargeUserId())
            .append("totalPrice", getTotalPrice())
            .append("confirmStatus", getConfirmStatus())
            .append("purchaseId", getPurchaseId())
            .append("purchaseSn", getPurchaseSn())
            .append("bulkOrderNo", getBulkOrderNo())
            .append("inDescription", getInDescription())
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
