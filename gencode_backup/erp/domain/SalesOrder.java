package com.ruoyi.erp.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 销售订单对象 t_erp_sales_order
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public class SalesOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 销售订单类型 */
    @Excel(name = "销售订单类型")
    private String salesType;

    /** 打样通知id */
    @Excel(name = "打样通知id")
    private Long noticeId;

    /** 工艺书id */
    @Excel(name = "工艺书id")
    private Long techId;

    /** 销售单号 */
    @Excel(name = "销售单号")
    private String salesNo;

    /** 销售日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "销售日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date salesDate;

    /** 业务员id */
    @Excel(name = "业务员id")
    private Long salesId;

    /** 客户id */
    @Excel(name = "客户id")
    private Long customerId;

    /** 交货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "交货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDate;

    /** 数量 */
    @Excel(name = "数量")
    private BigDecimal quantity;

    /** 订单金额 */
    @Excel(name = "订单金额")
    private BigDecimal amount;

    /** 付款金额 */
    @Excel(name = "付款金额")
    private BigDecimal payAmount;

    /** 订单状态 */
    @Excel(name = "订单状态")
    private String orderStatus;

    /** 审批状态 */
    @Excel(name = "审批状态")
    private String auditStatus;

    /** 审批人 */
    private String auditBy;

    /** 审批时间 */
    private Date auditTime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }

    public String getSalesType() {
        return salesType;
    }
    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getNoticeId() {
        return noticeId;
    }
    public void setTechId(Long techId) {
        this.techId = techId;
    }

    public Long getTechId() {
        return techId;
    }
    public void setSalesNo(String salesNo) {
        this.salesNo = salesNo;
    }

    public String getSalesNo() {
        return salesNo;
    }
    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }

    public Date getSalesDate() {
        return salesDate;
    }
    public void setSalesId(Long salesId) {
        this.salesId = salesId;
    }

    public Long getSalesId() {
        return salesId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
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
            .append("salesType", getSalesType())
            .append("noticeId", getNoticeId())
            .append("techId", getTechId())
            .append("salesNo", getSalesNo())
            .append("salesDate", getSalesDate())
            .append("salesId", getSalesId())
            .append("customerId", getCustomerId())
            .append("dueDate", getDueDate())
            .append("quantity", getQuantity())
            .append("amount", getAmount())
            .append("payAmount", getPayAmount())
            .append("orderStatus", getOrderStatus())
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
