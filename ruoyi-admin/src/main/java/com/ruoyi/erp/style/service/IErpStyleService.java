package com.ruoyi.erp.style.service;

import java.util.List;
import com.ruoyi.erp.style.domain.ErpStyle;

public interface IErpStyleService {
    ErpStyle selectErpStyleById(Long id);
    ErpStyle selectErpStyleByCode(String styleCode);
    List<ErpStyle> selectErpStyleList(ErpStyle style);
    int insertErpStyle(ErpStyle style);
    int updateErpStyle(ErpStyle style);
    int deleteErpStyleByIds(Long[] ids);
}
