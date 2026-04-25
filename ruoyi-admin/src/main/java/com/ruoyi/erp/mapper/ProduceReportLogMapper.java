package com.ruoyi.erp.mapper;

import com.ruoyi.erp.domain.ProduceReportLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProduceReportLogMapper {
    ProduceReportLog selectProduceReportLogById(Long id);

    List<ProduceReportLog> selectProduceReportLogList(ProduceReportLog produceReportLog);

    int insertProduceReportLog(ProduceReportLog produceReportLog);

    int updateProduceReportLog(ProduceReportLog produceReportLog);

    int deleteProduceReportLogById(Long id);

    int deleteProduceReportLogByIds(Long[] ids);

    Integer sumReportQtyByJobProcessId(Long jobProcessId);
}
