package com.ruoyi.erp.approval.controller;

import java.util.List;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.erp.approval.domain.ErpApprovalLog;
import com.ruoyi.erp.approval.service.IErpApprovalLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erp/approvalLog")
public class ErpApprovalLogController extends BaseController {

    @Autowired
    private IErpApprovalLogService approvalLogService;

    @PreAuthorize("@ss.hasPermi('erp:approvalLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpApprovalLog log) {
        startPage();
        return getDataTable(approvalLogService.selectApprovalLogList(log));
    }

    @PreAuthorize("@ss.hasPermi('erp:approvalLog:list')")
    @GetMapping("/business/{businessType}/{businessId}")
    public AjaxResult getByBusiness(@PathVariable String businessType,
                                    @PathVariable Long businessId) {
        List<ErpApprovalLog> logs = approvalLogService.selectByBusiness(businessType, businessId);
        return success(logs);
    }
}
