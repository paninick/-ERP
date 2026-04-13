package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.DataImport;

public interface IDataImportService
{
    public List<DataImport> selectDataImportList(DataImport dataImport);

    public DataImport selectDataImportByImportId(Long importId);

    public int insertDataImport(DataImport dataImport);

    public int updateDataImport(DataImport dataImport);

    public int deleteDataImportByImportId(Long importId);

    public int deleteDataImportByImportIds(Long[] importIds);
}
