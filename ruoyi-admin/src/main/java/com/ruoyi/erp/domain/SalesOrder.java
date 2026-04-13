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

    /** 销售类型 */
    @Excel(name = "销售类型")
    private String salesType;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String customerName;

    /** 大货款号 */
    @Excel(name = "大货款号")
    private String bulkOrderNo;

    /** 打样款号 */
    @Excel(name = "打样款号")
    private String sampleStyleNo;

    /** 款式/品类 */
    @Excel(name = "款式/品类")
    private String styleCategory;

    /** 打样编号 */
    @Excel(name = "打样编号")
    private String sampleNo;

    /** 业务员 */
    @Excel(name = "业务员")
    private String salesName;

    /** 打样通知id */
    private Long noticeId;

    /** 工艺书id */
    private Long techId;

    /** 销售单号 */
    @Excel(name = "销售单号")
    private String salesNo;

    /** 销售日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "销售日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date salesDate;

    /** 业务员id */
    private Long salesId;

    /** 客户id */
    private Long customerId;

    /** 交货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "交货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDate;

    /** 数量 */
    private BigDecimal quantity;

    /** 订单金额 */
    private BigDecimal amount;

    /** 付款金额 */
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 大货意见 */
    @Excel(name = "大货意见")
    private String bulkOpinion;

    /** 排产需求是否超出 */
    @Excel(name = "排产需求是否超出")
    private String productionExceed;

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
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setBulkOrderNo(String bulkOrderNo) {
        this.bulkOrderNo = bulkOrderNo;
    }

    public String getBulkOrderNo() {
        return bulkOrderNo;
    }
    public void setSampleStyleNo(String sampleStyleNo) {
        this.sampleStyleNo = sampleStyleNo;
    }

    public String getSampleStyleNo() {
        return sampleStyleNo;
    }
    public void setStyleCategory(String styleCategory) {
        this.styleCategory = styleCategory;
    }

    public String getStyleCategory() {
        return styleCategory;
    }
    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getSampleNo() {
        return sampleNo;
    }
    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public String getSalesName() {
        return salesName;
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

    public void setBulkOpinion(String bulkOpinion) {
        this.bulkOpinion = bulkOpinion;
    }

    public String getBulkOpinion() {
        return bulkOpinion;
    }

    public void setProductionExceed(String productionExceed) {
        this.productionExceed = productionExceed;
    }

    public String getProductionExceed() {
        return productionExceed;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("salesType", getSalesType())
            .append("customerName", getCustomerName())
            .append("bulkOrderNo", getBulkOrderNo())
            .append("sampleStyleNo", getSampleStyleNo())
            .append("styleCategory", getStyleCategory())
            .append("sampleNo", getSampleNo())
            .append("salesName", getSalesName())
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
            .append("bulkOpinion", getBulkOpinion())
            .append("productionExceed", getProductionExceed())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
