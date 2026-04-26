package com.ruoyi.erp.workcenter.mapper;

import java.util.List;
import com.ruoyi.erp.workcenter.domain.ErpWorkCenter;

public interface ErpWorkCenterMapper {
    ErpWorkCenter selectErpWorkCenterById(Long id);
    List<ErpWorkCenter> selectErpWorkCenterList(ErpWorkCenter workCenter);
    int insertErpWorkCenter(ErpWorkCenter workCenter);
    int updateErpWorkCenter(ErpWorkCenter workCenter);
    int deleteErpWorkCenterById(Long id);
    int deleteErpWorkCenterByIds(Long[] ids);
}
