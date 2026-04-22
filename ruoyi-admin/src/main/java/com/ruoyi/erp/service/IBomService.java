package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.Bom;

public interface IBomService {
    Bom selectBomById(Long id);
    List<Bom> selectBomList(Bom bom);
    int insertBom(Bom bom);
    int updateBom(Bom bom);
    int deleteBomByIds(Long[] ids);
    int deleteBomById(Long id);
    int insertBomBatch(List<Bom> list);

    /**
     * 导入样衣 BOM 主表 Excel（明细表不在此方法范围）
     */
    String importBom(List<Bom> list, boolean updateSupport);
}
