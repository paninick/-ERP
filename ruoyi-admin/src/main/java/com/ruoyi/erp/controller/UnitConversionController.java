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
import com.ruoyi.erp.domain.UnitConversion;
import com.ruoyi.erp.service.IUnitConversionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 单位换算Controller
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
@RestController
@RequestMapping("/erp/unitConversion")
public class UnitConversionController extends BaseController {
    @Autowired
    private IUnitConversionService unitConversionService;

    /**
     * 查询单位换算列表
     */
    @PreAuthorize("@ss.hasPermi('erp:unitConversion:list')")
    @GetMapping("/list")
    public TableDataInfo list(UnitConversion unitConversion) {
        startPage();
        List<UnitConversion> list = unitConversionService.selectUnitConversionList(unitConversion);
        return getDataTable(list);
    }

    /**
     * 导出单位换算列表
     */
    @PreAuthorize("@ss.hasPermi('erp:unitConversion:export')")
    @Log(title = "单位换算", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UnitConversion unitConversion) {
        List<UnitConversion> list = unitConversionService.selectUnitConversionList(unitConversion);
        ExcelUtil<UnitConversion> util = new ExcelUtil<UnitConversion>(UnitConversion.class);
        util.exportExcel(response, list, "单位换算数据");
    }

    /**
     * 获取单位换算详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:unitConversion:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(unitConversionService.selectUnitConversionById(id));
    }

    /**
     * 获取物料单位换算
     */
    @PreAuthorize("@ss.hasPermi('erp:unitConversion:query')")
    @GetMapping(value = "/material/{materialId}")
    public AjaxResult getByMaterialId(@PathVariable Long materialId) {
        return success(unitConversionService.selectUnitConversionByMaterialId(materialId));
    }

    /**
     * 新增单位换算
     */
    @PreAuthorize("@ss.hasPermi('erp:unitConversion:add')")
    @Log(title = "单位换算", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UnitConversion unitConversion) {
        return toAjax(unitConversionService.insertUnitConversion(unitConversion));
    }

    /**
     * 修改单位换算
     */
    @PreAuthorize("@ss.hasPermi('erp:unitConversion:edit')")
    @Log(title = "单位换算", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UnitConversion unitConversion) {
        return toAjax(unitConversionService.updateUnitConversion(unitConversion));
    }

    /**
     * 删除单位换算
     */
    @PreAuthorize("@ss.hasPermi('erp:unitConversion:remove')")
    @Log(title = "单位换算", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(unitConversionService.deleteUnitConversionByIds(ids));
    }
}
