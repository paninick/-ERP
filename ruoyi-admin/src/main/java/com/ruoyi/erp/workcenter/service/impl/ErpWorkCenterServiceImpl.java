package com.ruoyi.erp.workcenter.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.erp.workcenter.domain.ErpWorkCenter;
import com.ruoyi.erp.workcenter.mapper.ErpWorkCenterMapper;
import com.ruoyi.erp.workcenter.service.IErpWorkCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ErpWorkCenterServiceImpl implements IErpWorkCenterService {

    @Autowired
    private ErpWorkCenterMapper workCenterMapper;

    @Override
    public ErpWorkCenter selectErpWorkCenterById(Long id) {
        return workCenterMapper.selectErpWorkCenterById(id);
    }

    @Override
    public List<ErpWorkCenter> selectErpWorkCenterList(ErpWorkCenter workCenter) {
        return workCenterMapper.selectErpWorkCenterList(workCenter);
    }

    @Override
    public int insertErpWorkCenter(ErpWorkCenter workCenter) {
        workCenter.setCreateTime(DateUtils.getNowDate());
        return workCenterMapper.insertErpWorkCenter(workCenter);
    }

    @Override
    public int updateErpWorkCenter(ErpWorkCenter workCenter) {
        workCenter.setUpdateTime(DateUtils.getNowDate());
        return workCenterMapper.updateErpWorkCenter(workCenter);
    }

    @Override
    public int deleteErpWorkCenterByIds(Long[] ids) {
        int rows = 0;
        for (Long id : ids) {
            rows += workCenterMapper.deleteErpWorkCenterById(id);
        }
        return rows;
    }
}
