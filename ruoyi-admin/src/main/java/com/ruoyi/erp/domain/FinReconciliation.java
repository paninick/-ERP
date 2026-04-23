package com.ruoyi.erp.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

public class FinReconciliation extends BaseEntity {
    private Long id;
    private Long invoiceId;
    private Long stockInId;
    private BigDecimal reconcileAmount;
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Long invoiceId) { this.invoiceId = invoiceId; }
    public Long getStockInId() { return stockInId; }
    public void setStockInId(Long stockInId) { this.stockInId = stockInId; }
    public BigDecimal getReconcileAmount() { return reconcileAmount; }
    public void setReconcileAmount(BigDecimal reconcileAmount) { this.reconcileAmount = reconcileAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
