package com.ruoyi.erp.service;

import com.ruoyi.erp.domain.ProduceReportLog;

import java.util.List;

public interface IProduceReportLogService {
    ProduceReportLog selectProduceReportLogById(Long id);

    List<ProduceReportLog> selectProduceReportLogList(ProduceReportLog produceReportLog);

    int insertProduceReportLog(ProduceReportLog produceReportLog);

    int batchReport(List<ProduceReportLog> reportLogs);

    int updateProduceReportLog(ProduceReportLog produceReportLog);

    int deleteProduceReportLogByIds(Long[] ids);

    int deleteProduceReportLogById(Long id);
}
