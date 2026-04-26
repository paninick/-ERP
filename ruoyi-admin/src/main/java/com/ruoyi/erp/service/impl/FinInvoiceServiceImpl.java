package com.ruoyi.erp.service.impl;

import java.math.BigDecimal;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.erp.domain.FinInvoice;
import com.ruoyi.erp.domain.FinReconciliation;
import com.ruoyi.erp.mapper.FinInvoiceMapper;
import com.ruoyi.erp.mapper.FinReconciliationMapper;
import com.ruoyi.erp.service.ErpRealtimePushService;
import com.ruoyi.erp.service.IFinInvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FinInvoiceServiceImpl implements IFinInvoiceService {

    private static final Logger log = LoggerFactory.getLogger(FinInvoiceServiceImpl.class);

    @Autowired
    private FinInvoiceMapper invoiceMapper;
    @Autowired
    private FinReconciliationMapper reconciliationMapper;
    @Autowired
    private ErpRealtimePushService pushService;

    @Override
    public List<FinInvoice> selectList(FinInvoice query) {
        return invoiceMapper.selectList(query);
    }

    @Override
    public FinInvoice selectById(Long id) {
        return invoiceMapper.selectById(id);
    }

    @Override
    public int insert(FinInvoice invoice) {
        invoice.setCreateBy(SecurityUtils.getUsername());
        invoice.setCreateTime(DateUtils.getNowDate());
        if (invoice.getSettledAmount() == null) invoice.setSettledAmount(BigDecimal.ZERO);
        if (invoice.getStatus() == null) invoice.setStatus("PENDING");
        return invoiceMapper.insert(invoice);
    }

    @Override
    public int update(FinInvoice invoice) {
        invoice.setUpdateBy(SecurityUtils.getUsername());
        invoice.setUpdateTime(DateUtils.getNowDate());
        return invoiceMapper.update(invoice);
    }

    @Override
    public int deleteById(Long id) {
        return invoiceMapper.deleteById(id);
    }

    /**
     * 核销：将入库单金额核销到发票上
     * 事务保证：reconciliation 插入 + invoice settled_amount 更新 原子完成
     */
    @Transactional(rollbackFor = Exception.class)
    public FinReconciliation reconcile(Long invoiceId, Long stockInId, BigDecimal amount) {
        FinInvoice invoice = invoiceMapper.selectById(invoiceId);
        if (invoice == null) throw new RuntimeException("发票不存在");
        if ("RED_ISSUED".equals(invoice.getStatus())) throw new RuntimeException("已红冲的发票不可核销");
        if ("SETTLED".equals(invoice.getStatus())) throw new RuntimeException("发票已全额核销");

        BigDecimal newSettled = invoice.getSettledAmount().add(amount);
        if (newSettled.compareTo(invoice.getTotalAmount()) > 0) {
            throw new RuntimeException("核销金额超出发票余额（剩余可核销：" +
                invoice.getTotalAmount().subtract(invoice.getSettledAmount()) + "）");
        }

        FinReconciliation rec = new FinReconciliation();
        rec.setInvoiceId(invoiceId);
        rec.setStockInId(stockInId);
        rec.setReconcileAmount(amount);
        rec.setStatus("ACTIVE");
        rec.setCreateBy(SecurityUtils.getUsername());
        rec.setCreateTime(DateUtils.getNowDate());
        reconciliationMapper.insert(rec);

        String newStatus = newSettled.compareTo(invoice.getTotalAmount()) == 0 ? "SETTLED" : "PARTIAL";
        invoiceMapper.updateSettledAmount(invoiceId, newSettled, newStatus);

        log.info("发票核销完成: invoiceId={}, amount={}, newSettled={}, status={}", invoiceId, amount, newSettled, newStatus);
        return rec;
    }

    /**
     * 红冲回滚：撤销发票的所有核销记录，重置 settled_amount
     * 事务保证：所有 reconciliation 标记 REVERSED + invoice 状态改 RED_ISSUED 原子完成
     */
    @Transactional(rollbackFor = Exception.class)
    public void redIssue(Long invoiceId, String reason) {
        FinInvoice invoice = invoiceMapper.selectById(invoiceId);
        if (invoice == null) throw new RuntimeException("发票不存在");
        if ("RED_ISSUED".equals(invoice.getStatus())) throw new RuntimeException("发票已红冲，不可重复操作");

        List<FinReconciliation> activeRecs = reconciliationMapper.selectActiveByInvoiceId(invoiceId);
        for (FinReconciliation rec : activeRecs) {
            reconciliationMapper.updateStatus(rec.getId(), "REVERSED");
        }

        invoiceMapper.updateSettledAmount(invoiceId, BigDecimal.ZERO, "RED_ISSUED");

        log.info("发票红冲完成: invoiceId={}, reversedCount={}, reason={}", invoiceId, activeRecs.size(), reason);
        try {
            pushService.pushAlert("WARNING", "发票 " + invoice.getInvoiceNo() + " 已红冲：" + reason);
        } catch (Exception e) {
            log.warn("WebSocket 推送失败（非致命）", e);
        }
    }

    /**
     * 撤销单条核销记录
     */
    @Transactional(rollbackFor = Exception.class)
    public void reverseReconciliation(Long reconciliationId) {
        FinReconciliation rec = reconciliationMapper.selectById(reconciliationId);
        if (rec == null) throw new RuntimeException("核销记录不存在");
        if ("REVERSED".equals(rec.getStatus())) throw new RuntimeException("该记录已撤销");

        reconciliationMapper.updateStatus(reconciliationId, "REVERSED");

        FinInvoice invoice = invoiceMapper.selectById(rec.getInvoiceId());
        BigDecimal newSettled = invoice.getSettledAmount().subtract(rec.getReconcileAmount());
        if (newSettled.compareTo(BigDecimal.ZERO) < 0) newSettled = BigDecimal.ZERO;
        String newStatus = newSettled.compareTo(BigDecimal.ZERO) == 0 ? "PENDING" : "PARTIAL";
        invoiceMapper.updateSettledAmount(rec.getInvoiceId(), newSettled, newStatus);

        log.info("核销撤销完成: recId={}, amount={}, invoiceNewSettled={}", reconciliationId, rec.getReconcileAmount(), newSettled);
    }

    /**
     * 看板统计
     */
    public java.util.Map<String, Object> getBoardStats() {
        Long factoryId = SecurityUtils.getLoginUser().getUser().getDeptId();
        return invoiceMapper.selectBoardStats(factoryId);
    }
}
