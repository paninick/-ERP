package com.ruoyi.erp.service.impl;

import java.util.List;
import java.util.Map;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.erp.domain.QcInspection;
import com.ruoyi.erp.mapper.QcInspectionMapper;
import com.ruoyi.erp.service.ErpRealtimePushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QcInspectionServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(QcInspectionServiceImpl.class);

    @Autowired
    private QcInspectionMapper inspectionMapper;
    @Autowired
    private ErpRealtimePushService pushService;

    public List<QcInspection> selectList(QcInspection query) {
        return inspectionMapper.selectList(query);
    }

    public QcInspection selectById(Long id) {
        return inspectionMapper.selectById(id);
    }

    public int insert(QcInspection inspection) {
        inspection.setCreateBy(SecurityUtils.getUsername());
        inspection.setCreateTime(DateUtils.getNowDate());
        if (inspection.getStatus() == null) inspection.setStatus("ACTIVE");
        return inspectionMapper.insert(inspection);
    }

    public int update(QcInspection inspection) {
        inspection.setUpdateBy(SecurityUtils.getUsername());
        inspection.setUpdateTime(DateUtils.getNowDate());
        return inspectionMapper.update(inspection);
    }

    @Transactional(rollbackFor = Exception.class)
    public void reject(Long id, String reason) {
        QcInspection qc = inspectionMapper.selectById(id);
        if (qc == null) throw new RuntimeException("质检单不存在");
        if ("REJECTED".equals(qc.getStatus())) throw new RuntimeException("已打回，不可重复操作");

        inspectionMapper.updateStatus(id, "REJECTED", reason);
        log.info("质检打回: id={}, batchNo={}, reason={}", id, qc.getBatchNo(), reason);

        try {
            pushService.pushAlert("ERROR", "质检批次 " + qc.getBatchNo() + " 致命缺陷打回：" + reason);
        } catch (Exception e) {
            log.warn("WebSocket 推送失败（非致命）", e);
        }
    }

    public Map<String, Object> getStats() {
        return inspectionMapper.selectStats();
    }

    public List<Map<String, Object>> getDefectReasons() {
        return inspectionMapper.selectDefectReasons();
    }
}
