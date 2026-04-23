package com.ruoyi.erp.service.impl;

import com.ruoyi.erp.domain.MaterialSku;
import com.ruoyi.erp.mapper.MaterialSkuMapper;
import com.ruoyi.erp.service.IMaterialSkuService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialSkuServiceImpl implements IMaterialSkuService {

    @Autowired
    private MaterialSkuMapper materialSkuMapper;

    @Override
    public List<MaterialSku> selectMaterialSkuList(MaterialSku materialSku) {
        return materialSkuMapper.selectMaterialSkuList(materialSku);
    }

    @Override
    public List<MaterialSku> selectMaterialSkuByMaterialId(Long materialId) {
        return materialSkuMapper.selectMaterialSkuByMaterialId(materialId);
    }
}
