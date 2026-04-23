package com.ruoyi.erp.service;

import com.ruoyi.erp.domain.MaterialSku;
import java.util.List;

public interface IMaterialSkuService {
    List<MaterialSku> selectMaterialSkuList(MaterialSku materialSku);

    List<MaterialSku> selectMaterialSkuByMaterialId(Long materialId);
}
