package com.ruoyi.erp.approval.mapper;

import java.util.List;
import com.ruoyi.erp.approval.domain.ErpApprovalLog;

public interface ErpApprovalLogMapper {
    int insertApprovalLog(ErpApprovalLog log);
    List<ErpApprovalLog> selectByBusiness(String businessType, Long businessId);
    List<ErpApprovalLog> selectApprovalLogList(ErpApprovalLog log);
}
