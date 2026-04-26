package com.ruoyi.erp.orgunit.controller;

import java.util.List;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.erp.orgunit.domain.OrgUnit;
import com.ruoyi.erp.orgunit.service.IOrgUnitService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 组织层级Controller
 * 组织层级：工厂 → 分厂 → 车间 → 班组 → 工位
 *
 * @author erp
 * @date 2026-04-25
 */
@RestController
@RequestMapping("/erp/orgunit")
public class OrgUnitController extends BaseController {

    @Autowired
    private IOrgUnitService orgUnitService;

    @PreAuthorize("@ss.hasPermi('erp:orgunit:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrgUnit orgUnit) {
        startPage();
        List<OrgUnit> list = orgUnitService.selectOrgUnitList(orgUnit);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('erp:orgunit:list')")
    @GetMapping("/children/{parentId}")
    public AjaxResult getChildren(@PathVariable Long parentId) {
        List<OrgUnit> list = orgUnitService.selectOrgUnitByParentId(parentId);
        return success(list);
    }

    @PreAuthorize("@ss.hasPermi('erp:orgunit:export')")
    @Log(title = "组织层级", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrgUnit orgUnit) {
        List<OrgUnit> list = orgUnitService.selectOrgUnitList(orgUnit);
        ExcelUtil<OrgUnit> util = new ExcelUtil<>(OrgUnit.class);
        util.exportExcel(response, list, "orgunit");
    }

    @PreAuthorize("@ss.hasPermi('erp:orgunit:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return success(orgUnitService.selectOrgUnitById(id));
    }

    @PreAuthorize("@ss.hasPermi('erp:orgunit:add')")
    @Log(title = "组织层级", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody @Validated OrgUnit orgUnit) {
        return toAjax(orgUnitService.insertOrgUnit(orgUnit));
    }

    @PreAuthorize("@ss.hasPermi('erp:orgunit:edit')")
    @Log(title = "组织层级", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody @Validated OrgUnit orgUnit) {
        return toAjax(orgUnitService.updateOrgUnit(orgUnit));
    }

    @PreAuthorize("@ss.hasPermi('erp:orgunit:remove')")
    @Log(title = "组织层级", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(orgUnitService.deleteOrgUnitByIds(ids));
    }
}
