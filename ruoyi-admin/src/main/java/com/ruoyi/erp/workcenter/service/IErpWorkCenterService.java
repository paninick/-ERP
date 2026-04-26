package com.ruoyi.erp.workcenter.service;

import java.util.List;
import com.ruoyi.erp.workcenter.domain.ErpWorkCenter;

public interface IErpWorkCenterService {
    ErpWorkCenter selectErpWorkCenterById(Long id);
    List<ErpWorkCenter> selectErpWorkCenterList(ErpWorkCenter workCenter);
    int insertErpWorkCenter(ErpWorkCenter workCenter);
    int updateErpWorkCenter(ErpWorkCenter workCenter);
    int deleteErpWorkCenterByIds(Long[] ids);
}
