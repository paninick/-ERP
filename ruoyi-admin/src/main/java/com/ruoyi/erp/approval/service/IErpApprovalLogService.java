package com.ruoyi.erp.approval.service;

import java.util.List;
import com.ruoyi.erp.approval.domain.ErpApprovalLog;

public interface IErpApprovalLogService {
    int writeLog(String businessType, Long businessId, String businessNo,
                 String nodeCode, String actionType,
                 String fromStatus, String toStatus,
                 String actionBy, String actionRemark, Long factoryId);

    List<ErpApprovalLog> selectByBusiness(String businessType, Long businessId);
    List<ErpApprovalLog> selectApprovalLogList(ErpApprovalLog log);
}
