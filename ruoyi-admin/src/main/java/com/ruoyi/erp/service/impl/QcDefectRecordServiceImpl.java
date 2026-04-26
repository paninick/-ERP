package com.ruoyi.erp.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.domain.QcDefectRecord;
import com.ruoyi.erp.mapper.QcDefectRecordMapper;
import com.ruoyi.erp.service.IQcDefectRecordService;

@Service
public class QcDefectRecordServiceImpl implements IQcDefectRecordService {

    @Autowired
    private QcDefectRecordMapper mapper;

    @Override
    public QcDefectRecord selectById(Long id) { return mapper.selectById(id); }

    @Override
    public List<QcDefectRecord> selectList(QcDefectRecord r) { return mapper.selectList(r); }

    @Override
    public int insert(QcDefectRecord r) { return mapper.insert(r); }

    @Override
    public int update(QcDefectRecord r) { return mapper.update(r); }

    @Override
    public int deleteById(Long id) { return mapper.deleteById(id); }

    @Override
    public int deleteByIds(Long[] ids) { return mapper.deleteByIds(ids); }
}
