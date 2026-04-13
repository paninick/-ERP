package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.Bom;

public interface BomMapper {
    Bom selectBomById(Long id);
    List<Bom> selectBomList(Bom bom);
    int insertBom(Bom bom);
    int updateBom(Bom bom);
    int deleteBomById(Long id);
    int deleteBomByIds(Long[] ids);
    int insertBomBatch(List<Bom> list);
}
