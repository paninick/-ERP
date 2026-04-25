package com.ruoyi.erp.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.erp.domain.ProduceJob;
import com.ruoyi.erp.domain.ProduceJobProcess;
import com.ruoyi.erp.domain.ProduceReportLog;
import com.ruoyi.erp.mapper.ProduceJobMapper;
import com.ruoyi.erp.mapper.ProduceJobProcessMapper;
import com.ruoyi.erp.mapper.ProduceReportLogMapper;
import com.ruoyi.erp.service.ErpRealtimePushService;
import com.ruoyi.erp.service.IProduceReportLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProduceReportLogServiceImpl implements IProduceReportLogService {

    private static final String VALIDATION_PASS = "PASS";
    private static final String DEFAULT_REPORT_TYPE = "WORKSHOP";
    private static final String DEFAULT_REPORT_SOURCE = "PAPER_BATCH";

    @Autowired
    private ProduceReportLogMapper produceReportLogMapper;

    @Autowired
    private ProduceJobProcessMapper produceJobProcessMapper;

    @Autowired
    private ProduceJobMapper produceJobMapper;

    @Autowired
    private ErpRealtimePushService erpRealtimePushService;

    @Override
    public ProduceReportLog selectProduceReportLogById(Long id) {
        return produceReportLogMapper.selectProduceReportLogById(id);
    }

    @Override
    public List<ProduceReportLog> selectProduceReportLogList(ProduceReportLog produceReportLog) {
        return produceReportLogMapper.selectProduceReportLogList(produceReportLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertProduceReportLog(ProduceReportLog produceReportLog) {
        normalizeAndValidate(produceReportLog);

        ProduceJobProcess current = produceJobProcessMapper.selectProduceJobProcessById(produceReportLog.getJobProcessId());
        if (current == null) {
            throw new ServiceException("生产工序不存在");
        }
        if (current.getJobId() == null || current.getProcessId() == null || current.getProcessSeq() == null) {
            throw new ServiceException("生产工序快照数据不完整");
        }

        ProduceJob job = produceJobMapper.selectProduceJobById(current.getJobId());
        if (job == null) {
            throw new ServiceException("生产工单不存在");
        }

        fillSnapshotFields(produceReportLog, current);
        validatePool(produceReportLog, current, job);

        produceReportLog.setValidationStatus(VALIDATION_PASS);
        produceReportLog.setValidationMessage("OK");
        produceReportLog.setCreateBy(SecurityUtils.getUsername());
        produceReportLog.setCreateTime(DateUtils.getNowDate());
        produceReportLog.setUpdateBy(SecurityUtils.getUsername());
        produceReportLog.setUpdateTime(DateUtils.getNowDate());
        if (produceReportLog.getEventTime() == null) {
            produceReportLog.setEventTime(DateUtils.getNowDate());
        }

        int rows = produceReportLogMapper.insertProduceReportLog(produceReportLog);
        if (rows > 0) {
            writeBackSnapshot(current, produceReportLog);
            erpRealtimePushService.pushProduceBoardRefresh();
        }
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchReport(List<ProduceReportLog> reportLogs) {
        if (reportLogs == null || reportLogs.isEmpty()) {
            throw new ServiceException("批量报工不能为空");
        }
        int rows = 0;
        for (ProduceReportLog reportLog : reportLogs) {
            rows += insertProduceReportLog(reportLog);
        }
        return rows;
    }

    @Override
    public int updateProduceReportLog(ProduceReportLog produceReportLog) {
        produceReportLog.setUpdateBy(SecurityUtils.getUsername());
        produceReportLog.setUpdateTime(DateUtils.getNowDate());
        return produceReportLogMapper.updateProduceReportLog(produceReportLog);
    }

    @Override
    public int deleteProduceReportLogByIds(Long[] ids) {
        return produceReportLogMapper.deleteProduceReportLogByIds(ids);
    }

    @Override
    public int deleteProduceReportLogById(Long id) {
        return produceReportLogMapper.deleteProduceReportLogById(id);
    }

    private void normalizeAndValidate(ProduceReportLog reportLog) {
        if (reportLog.getJobProcessId() == null || reportLog.getJobProcessId() <= 0) {
            throw new ServiceException("生产工序ID不能为空");
        }
        reportLog.setCompletedQty(defaultNumber(reportLog.getCompletedQty()));
        reportLog.setDefectQty(defaultNumber(reportLog.getDefectQty()));
        reportLog.setLossQty(defaultNumber(reportLog.getLossQty()));
        if (reportLog.getReportQty() == null) {
            reportLog.setReportQty(reportLog.getCompletedQty() + reportLog.getDefectQty() + reportLog.getLossQty());
        }
        if (reportLog.getReportQty() <= 0) {
            throw new ServiceException("报工数量必须大于0");
        }
        if (reportLog.getCompletedQty() < 0 || reportLog.getDefectQty() < 0 || reportLog.getLossQty() < 0) {
            throw new ServiceException("合格、次品、损耗数量不能为负数");
        }
        if (reportLog.getCompletedQty() + reportLog.getDefectQty() + reportLog.getLossQty() != reportLog.getReportQty()) {
            throw new ServiceException("报工数量必须等于合格数、次品数和损耗数之和");
        }
        if (reportLog.getReportType() == null || reportLog.getReportType().isEmpty()) {
            reportLog.setReportType(DEFAULT_REPORT_TYPE);
        }
        if (reportLog.getReportSource() == null || reportLog.getReportSource().isEmpty()) {
            reportLog.setReportSource(DEFAULT_REPORT_SOURCE);
        }
        if (reportLog.getIsOutsourced() == null || reportLog.getIsOutsourced().isEmpty()) {
            reportLog.setIsOutsourced("0");
        }
        if (reportLog.getDelFlag() == null || reportLog.getDelFlag().isEmpty()) {
            reportLog.setDelFlag("0");
        }
    }

    private void fillSnapshotFields(ProduceReportLog reportLog, ProduceJobProcess current) {
        reportLog.setJobId(current.getJobId());
        reportLog.setProcessId(current.getProcessId());
        reportLog.setProcessSeq(current.getProcessSeq());
        if (reportLog.getEmployeeId() == null) {
            reportLog.setEmployeeId(current.getEmployeeId());
        }
        if (reportLog.getEmployeeName() == null || reportLog.getEmployeeName().isEmpty()) {
            reportLog.setEmployeeName(current.getEmployeeName());
        }
        if ("1".equals(current.getIsOutsource())) {
            reportLog.setIsOutsourced("1");
            if (reportLog.getVendorId() == null) {
                reportLog.setVendorId(current.getOutsourceId());
            }
        }
    }

    private void validatePool(ProduceReportLog reportLog, ProduceJobProcess current, ProduceJob job) {
        int plannedQty = defaultNumber(job.getPlanQty());
        int currentInQty = defaultNumber(current.getInQty());
        int currentHistorical = defaultNumber(current.getOutQty()) + defaultNumber(current.getDefectQty()) + defaultNumber(current.getLossQty());
        int toleranceQty = Math.max(1, (int) Math.ceil(plannedQty * 0.05D));
        int availablePool;

        ProduceJobProcess previous = selectPreviousProcess(current);
        if (previous == null) {
            availablePool = currentInQty > 0 ? currentInQty : plannedQty + toleranceQty;
        } else {
            availablePool = defaultNumber(previous.getOutQty()) - currentHistorical + toleranceQty;
        }

        if (reportLog.getReportQty() > availablePool) {
            throw new ServiceException("报工数超过前道可用池，当前可用: " + availablePool + "，本次报工: " + reportLog.getReportQty());
        }

        int jobTotalAfter = sumJobReportedQty(current.getJobId()) + reportLog.getReportQty();
        int maxJobQty = plannedQty + toleranceQty;
        if (plannedQty > 0 && previous == null && jobTotalAfter > maxJobQty) {
            throw new ServiceException("首道累计报工超过计划容差，计划: " + plannedQty + "，允许: " + maxJobQty);
        }
    }

    private ProduceJobProcess selectPreviousProcess(ProduceJobProcess current) {
        ProduceJobProcess previous = null;
        for (int seq = current.getProcessSeq() - 1; seq >= 0; seq--) {
            ProduceJobProcess query = new ProduceJobProcess();
            query.setJobId(current.getJobId());
            query.setProcessSeq(seq);
            ProduceJobProcess candidate = produceJobProcessMapper.selectProduceJobProcessByJobIdAndProcessSeq(query);
            if (candidate != null && !"1".equals(candidate.getIsSkipped())) {
                previous = candidate;
                break;
            }
        }
        return previous;
    }

    private int sumJobReportedQty(Long jobId) {
        ProduceReportLog query = new ProduceReportLog();
        query.setJobId(jobId);
        query.setValidationStatus(VALIDATION_PASS);
        List<ProduceReportLog> logs = produceReportLogMapper.selectProduceReportLogList(query);
        return logs.stream().mapToInt(log -> defaultNumber(log.getReportQty())).sum();
    }

    private void writeBackSnapshot(ProduceJobProcess current, ProduceReportLog reportLog) {
        ProduceJobProcess update = new ProduceJobProcess();
        update.setId(current.getId());
        update.setEmployeeId(reportLog.getEmployeeId());
        update.setEmployeeName(reportLog.getEmployeeName());
        update.setOutQty(defaultNumber(current.getOutQty()) + reportLog.getCompletedQty());
        update.setDefectQty(defaultNumber(current.getDefectQty()) + reportLog.getDefectQty());
        update.setLossQty(defaultNumber(current.getLossQty()) + reportLog.getLossQty());
        update.setProcessStatus("WAIT_CHECK");
        if (current.getStartTime() == null) {
            update.setStartTime(DateUtils.getNowDate());
        }
        update.setFinishTime(DateUtils.getNowDate());
        produceJobProcessMapper.updateProduceJobProcess(update);

        ProduceJob jobUpdate = new ProduceJob();
        jobUpdate.setId(current.getJobId());
        jobUpdate.setActualQty(update.getOutQty());
        jobUpdate.setDefectQty(update.getDefectQty());
        jobUpdate.setCurrentProcessId(current.getProcessId());
        jobUpdate.setCurrentProcessStatus(update.getProcessStatus());
        jobUpdate.setUpdateBy(SecurityUtils.getUsername());
        jobUpdate.setUpdateTime(DateUtils.getNowDate());
        produceJobMapper.updateProduceJob(jobUpdate);
    }

    private Integer defaultNumber(Integer value) {
        return value == null ? 0 : value;
    }
}
