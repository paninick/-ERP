package com.ruoyi.erp.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.domain.DataImport;
import com.ruoyi.erp.mapper.DataImportMapper;
import com.ruoyi.erp.service.IDataImportService;

@Service
public class DataImportServiceImpl implements IDataImportService
{
    @Autowired
    private DataImportMapper dataImportMapper;

    @Override
    public List<DataImport> selectDataImportList(DataImport dataImport)
    {
        return dataImportMapper.selectDataImportList(dataImport);
    }

    @Override
    public DataImport selectDataImportByImportId(Long importId)
    {
        return dataImportMapper.selectDataImportByImportId(importId);
    }

    @Override
    public int insertDataImport(DataImport dataImport)
    {
        return dataImportMapper.insertDataImport(dataImport);
    }

    @Override
    public int updateDataImport(DataImport dataImport)
    {
        return dataImportMapper.updateDataImport(dataImport);
    }

    @Override
    public int deleteDataImportByImportId(Long importId)
    {
        return dataImportMapper.deleteDataImportByImportId(importId);
    }

    @Override
    public int deleteDataImportByImportIds(Long[] importIds)
    {
        return dataImportMapper.deleteDataImportByImportIds(importIds);
    }
}
