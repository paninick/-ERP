package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.QcDefectRecord;

public interface QcDefectRecordMapper {
    QcDefectRecord selectById(Long id);
    List<QcDefectRecord> selectList(QcDefectRecord record);
    int insert(QcDefectRecord record);
    int update(QcDefectRecord record);
    int deleteById(Long id);
    int deleteByIds(Long[] ids);
}
