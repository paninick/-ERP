package com.ruoyi.erp.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.ruoyi.erp.domain.FinInvoice;
import com.ruoyi.erp.domain.FinReconciliation;

public interface IFinInvoiceService {
    List<FinInvoice> selectList(FinInvoice query);
    FinInvoice selectById(Long id);
    int insert(FinInvoice invoice);
    int update(FinInvoice invoice);
    int deleteById(Long id);
    FinReconciliation reconcile(Long invoiceId, Long stockInId, BigDecimal amount);
    void redIssue(Long invoiceId, String reason);
    void reverseReconciliation(Long reconciliationId);
    Map<String, Object> getBoardStats();
}
