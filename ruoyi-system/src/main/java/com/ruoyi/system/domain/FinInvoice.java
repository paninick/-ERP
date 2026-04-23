package com.ruoyi.system.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.type.EncryptTypeHandler;

/**
 * 财务发票主表 t_erp_fin_invoice
 *
 * @author ruoyi
 */
public class FinInvoice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "发票号")
    private String invoiceNo;

    private Long customerId;

    @Excel(name = "客户名称")
    private String customerName;

    @Excel(name = "发票金额", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal totalAmount;

    /** 已核销金额，累计对账后更新 */
    private BigDecimal settledAmount;

    /**
     * 税号 — 敏感字段，持久层通过 EncryptTypeHandler 透明加解密。
     * 在 Mapper XML 中配置 typeHandler，业务层拿到的是明文。
     */
    private String taxNumber;

    /** 状态：PENDING / PARTIAL / SETTLED / RED_ISSUED */
    @Excel(name = "状态")
    private String status;

    private String invoiceDate;

    private Long factoryId;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getInvoiceNo() { return invoiceNo; }
    public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public BigDecimal getSettledAmount() { return settledAmount; }
    public void setSettledAmount(BigDecimal settledAmount) { this.settledAmount = settledAmount; }

    public String getTaxNumber() { return taxNumber; }
    public void setTaxNumber(String taxNumber) { this.taxNumber = taxNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(String invoiceDate) { this.invoiceDate = invoiceDate; }

    public Long getFactoryId() { return factoryId; }
    public void setFactoryId(Long factoryId) { this.factoryId = factoryId; }
}
