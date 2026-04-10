package com.ruoyi.erp.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.erp.domain.MainMaterial;
import com.ruoyi.erp.service.IMainMaterialService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 主料Controller
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
@RestController
@RequestMapping("/erp/material")
public class MainMaterialController extends BaseController {
    @Autowired
    private IMainMaterialService mainMaterialService;

    /**
     * 查询主料列表
     */
    @PreAuthorize("@ss.hasPermi('erp:material:list')")
    @GetMapping("/list")
    public TableDataInfo list(MainMaterial mainMaterial) {
        startPage();
        List<MainMaterial> list = mainMaterialService.selectMainMaterialList(mainMaterial);
        return getDataTable(list);
    }

    /**
     * 导出主料列表
     */
    @PreAuthorize("@ss.hasPermi('erp:material:export')")
    @Log(title = "主料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MainMaterial mainMaterial) {
        List<MainMaterial> list = mainMaterialService.selectMainMaterialList(mainMaterial);
        ExcelUtil<MainMaterial> util = new ExcelUtil<MainMaterial>(MainMaterial.class);
        util.exportExcel(response, list, "主料数据");
    }

    /**
     * 获取主料详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:material:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(mainMaterialService.selectMainMaterialById(id));
    }

    /**
     * 新增主料
     */
    @PreAuthorize("@ss.hasPermi('erp:material:add')")
    @Log(title = "主料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MainMaterial mainMaterial) {
        return toAjax(mainMaterialService.insertMainMaterial(mainMaterial));
    }

    /**
     * 修改主料
     */
    @PreAuthorize("@ss.hasPermi('erp:material:edit')")
    @Log(title = "主料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MainMaterial mainMaterial) {
        return toAjax(mainMaterialService.updateMainMaterial(mainMaterial));
    }

    /**
     * 删除主料
     */
    @PreAuthorize("@ss.hasPermi('erp:material:remove')")
    @Log(title = "主料", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(mainMaterialService.deleteMainMaterialByIds(ids));
    }
}
