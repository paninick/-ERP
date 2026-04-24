package com.ruoyi.erp.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.erp.domain.MaterialSku;
import com.ruoyi.erp.service.IMaterialSkuService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/erp/material/sku", "/erp/materialSku"})
public class MaterialSkuController extends BaseController {

    @Autowired
    private IMaterialSkuService materialSkuService;

    @PreAuthorize("@ss.hasAnyPermi('erp:inventory:list,erp:inventory:add,erp:inventory:edit,erp:material:query')")
    @GetMapping("/options")
    public AjaxResult options(MaterialSku materialSku) {
        materialSku.setStatus("0");
        List<MaterialSku> list = materialSkuService.selectMaterialSkuList(materialSku);
        return success(list);
    }

    @PreAuthorize("@ss.hasAnyPermi('erp:inventory:list,erp:inventory:add,erp:inventory:edit,erp:material:query')")
    @GetMapping("/options/{materialId}")
    public AjaxResult optionsByMaterial(@PathVariable Long materialId) {
        return success(materialSkuService.selectMaterialSkuByMaterialId(materialId));
    }
}
