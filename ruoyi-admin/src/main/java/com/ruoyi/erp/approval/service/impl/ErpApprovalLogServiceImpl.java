package com.ruoyi.erp.approval.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.erp.approval.domain.ErpApprovalLog;
import com.ruoyi.erp.approval.mapper.ErpApprovalLogMapper;
import com.ruoyi.erp.approval.service.IErpApprovalLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ErpApprovalLogServiceImpl implements IErpApprovalLogService {

    @Autowired
    private ErpApprovalLogMapper approvalLogMapper;

    @Override
    public int writeLog(String businessType, Long businessId, String businessNo,
                        String nodeCode, String actionType,
                        String fromStatus, String toStatus,
                        String actionBy, String actionRemark, Long factoryId) {
        ErpApprovalLog log = new ErpApprovalLog();
        log.setBusinessType(businessType);
        log.setBusinessId(businessId);
        log.setBusinessNo(businessNo);
        log.setNodeCode(nodeCode);
        log.setActionType(actionType);
        log.setFromStatus(fromStatus);
        log.setToStatus(toStatus);
        log.setActionBy(actionBy);
        log.setActionTime(new Date());
        log.setActionRemark(actionRemark);
        log.setFactoryId(factoryId);
        return approvalLogMapper.insertApprovalLog(log);
    }

    @Override
    public List<ErpApprovalLog> selectByBusiness(String businessType, Long businessId) {
        return approvalLogMapper.selectByBusiness(businessType, businessId);
    }

    @Override
    public List<ErpApprovalLog> selectApprovalLogList(ErpApprovalLog log) {
        return approvalLogMapper.selectApprovalLogList(log);
    }
}
