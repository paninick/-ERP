package com.ruoyi.erp.controller;

import java.math.BigDecimal;
import java.util.Map;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.erp.domain.FinInvoice;
import com.ruoyi.erp.service.impl.FinInvoiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erp/finInvoice")
public class FinInvoiceController extends BaseController {

    @Autowired
    private FinInvoiceServiceImpl invoiceService;

    @PreAuthorize("@ss.hasPermi('erp:finInvoice:list')")
    @GetMapping("/list")
    public TableDataInfo list(FinInvoice query) {
        startPage();
        return getDataTable(invoiceService.selectList(query));
    }

    @PreAuthorize("@ss.hasPermi('erp:finInvoice:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(invoiceService.selectById(id));
    }

    @PreAuthorize("@ss.hasPermi('erp:finInvoice:add')")
    @PostMapping
    public AjaxResult add(@RequestBody FinInvoice invoice) {
        return toAjax(invoiceService.insert(invoice));
    }

    @PreAuthorize("@ss.hasPermi('erp:finInvoice:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody FinInvoice invoice) {
        return toAjax(invoiceService.update(invoice));
    }

    @PreAuthorize("@ss.hasPermi('erp:finInvoice:remove')")
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        return toAjax(invoiceService.deleteById(id));
    }

    @PreAuthorize("@ss.hasPermi('erp:finInvoice:edit')")
    @PostMapping("/reconcile")
    public AjaxResult reconcile(@RequestBody Map<String, Object> body) {
        Long invoiceId = Long.valueOf(body.get("invoiceId").toString());
        Long stockInId = body.get("stockInId") != null ? Long.valueOf(body.get("stockInId").toString()) : null;
        BigDecimal amount = new BigDecimal(body.get("amount").toString());
        return AjaxResult.success(invoiceService.reconcile(invoiceId, stockInId, amount));
    }

    @PreAuthorize("@ss.hasPermi('erp:finInvoice:edit')")
    @PostMapping("/redIssue/{id}")
    public AjaxResult redIssue(@PathVariable Long id, @RequestBody(required = false) Map<String, String> body) {
        String reason = body != null ? body.get("reason") : null;
        invoiceService.redIssue(id, reason);
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('erp:finInvoice:edit')")
    @PostMapping("/reverseReconciliation/{recId}")
    public AjaxResult reverseReconciliation(@PathVariable Long recId) {
        invoiceService.reverseReconciliation(recId);
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('erp:finInvoice:query')")
    @GetMapping("/boardStats")
    public AjaxResult boardStats() {
        return AjaxResult.success(invoiceService.getBoardStats());
    }
}
