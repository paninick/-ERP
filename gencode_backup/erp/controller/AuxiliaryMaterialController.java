package com.ruoyi.erp.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.erp.domain.AuxiliaryMaterial;
import com.ruoyi.erp.service.IAuxiliaryMaterialService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 辅料Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping("/erp/auxiliary")
public class AuxiliaryMaterialController extends BaseController {
    @Autowired
    private IAuxiliaryMaterialService auxiliaryMaterialService;

    /**
     * 查询辅料列表
     */
    @PreAuthorize("@ss.hasPermi('erp:auxiliary:list')")
    @GetMapping("/list")
    public TableDataInfo list(AuxiliaryMaterial auxiliaryMaterial) {
        startPage();
        List<AuxiliaryMaterial> list = auxiliaryMaterialService.selectAuxiliaryMaterialList(auxiliaryMaterial);
        return getDataTable(list);
    }

    /**
     * 导出辅料列表
     */
    @PreAuthorize("@ss.hasPermi('erp:auxiliary:export')")
    @Log(title = "辅料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AuxiliaryMaterial auxiliaryMaterial) {
        List<AuxiliaryMaterial> list = auxiliaryMaterialService.selectAuxiliaryMaterialList(auxiliaryMaterial);
        ExcelUtil<AuxiliaryMaterial> util = new ExcelUtil<AuxiliaryMaterial>(AuxiliaryMaterial.class);
        util.exportExcel(response, list, "辅料数据");
    }

    /**
     * 获取辅料详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:auxiliary:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(auxiliaryMaterialService.selectAuxiliaryMaterialById(id));
    }

    /**
     * 新增辅料
     */
    @PreAuthorize("@ss.hasPermi('erp:auxiliary:add')")
    @Log(title = "辅料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AuxiliaryMaterial auxiliaryMaterial) {
        return toAjax(auxiliaryMaterialService.insertAuxiliaryMaterial(auxiliaryMaterial));
    }

    /**
     * 修改辅料
     */
    @PreAuthorize("@ss.hasPermi('erp:auxiliary:edit')")
    @Log(title = "辅料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AuxiliaryMaterial auxiliaryMaterial) {
        return toAjax(auxiliaryMaterialService.updateAuxiliaryMaterial(auxiliaryMaterial));
    }

    /**
     * 删除辅料
     */
    @PreAuthorize("@ss.hasPermi('erp:auxiliary:remove')")
    @Log(title = "辅料", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(auxiliaryMaterialService.deleteAuxiliaryMaterialByIds(ids));
    }
}
