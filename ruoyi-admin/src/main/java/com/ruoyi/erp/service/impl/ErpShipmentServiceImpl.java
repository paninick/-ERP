package com.ruoyi.erp.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.domain.ErpShipment;
import com.ruoyi.erp.mapper.ErpShipmentMapper;
import com.ruoyi.erp.service.IErpShipmentService;

@Service
public class ErpShipmentServiceImpl implements IErpShipmentService {

    @Autowired
    private ErpShipmentMapper mapper;

    @Override
    public ErpShipment selectById(Long id) { return mapper.selectById(id); }

    @Override
    public List<ErpShipment> selectList(ErpShipment s) { return mapper.selectList(s); }

    @Override
    public int insert(ErpShipment s) { return mapper.insert(s); }

    @Override
    public int update(ErpShipment s) { return mapper.update(s); }

    @Override
    public int deleteById(Long id) { return mapper.deleteById(id); }

    @Override
    public int deleteByIds(Long[] ids) { return mapper.deleteByIds(ids); }
}
