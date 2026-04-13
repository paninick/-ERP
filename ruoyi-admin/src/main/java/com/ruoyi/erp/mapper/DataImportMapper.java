package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.DataImport;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataImportMapper
{
    public List<DataImport> selectDataImportList(DataImport dataImport);

    public DataImport selectDataImportByImportId(Long importId);

    public int insertDataImport(DataImport dataImport);

    public int updateDataImport(DataImport dataImport);

    public int deleteDataImportByImportId(Long importId);

    public int deleteDataImportByImportIds(Long[] importIds);
}
