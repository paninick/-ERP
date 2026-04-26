package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.ErpShipment;

public interface ErpShipmentMapper {
    ErpShipment selectById(Long id);
    List<ErpShipment> selectList(ErpShipment shipment);
    int insert(ErpShipment shipment);
    int update(ErpShipment shipment);
    int deleteById(Long id);
    int deleteByIds(Long[] ids);
}
