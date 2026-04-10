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
import com.ruoyi.erp.domain.SampleTechMaterial;
import com.ruoyi.erp.service.ISampleTechMaterialService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工艺书面料信息Controller
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
@RestController
@RequestMapping("/erp/techmaterial")
public class SampleTechMaterialController extends BaseController {
    @Autowired
    private ISampleTechMaterialService sampleTechMaterialService;

    /**
     * 查询工艺书面料信息列表
     */
    @PreAuthorize("@ss.hasPermi('erp:techmaterial:list')")
    @GetMapping("/list")
    public TableDataInfo list(SampleTechMaterial sampleTechMaterial) {
        startPage();
        List<SampleTechMaterial> list = sampleTechMaterialService.selectSampleTechMaterialList(sampleTechMaterial);
        return getDataTable(list);
    }

    /**
     * 导出工艺书面料信息列表
     */
    @PreAuthorize("@ss.hasPermi('erp:techmaterial:export')")
    @Log(title = "工艺书面料信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SampleTechMaterial sampleTechMaterial) {
        List<SampleTechMaterial> list = sampleTechMaterialService.selectSampleTechMaterialList(sampleTechMaterial);
        ExcelUtil<SampleTechMaterial> util = new ExcelUtil<SampleTechMaterial>(SampleTechMaterial.class);
        util.exportExcel(response, list, "工艺书面料信息数据");
    }

    /**
     * 获取工艺书面料信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:techmaterial:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(sampleTechMaterialService.selectSampleTechMaterialById(id));
    }

    /**
     * 新增工艺书面料信息
     */
    @PreAuthorize("@ss.hasPermi('erp:techmaterial:add')")
    @Log(title = "工艺书面料信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SampleTechMaterial sampleTechMaterial) {
        return toAjax(sampleTechMaterialService.insertSampleTechMaterial(sampleTechMaterial));
    }

    /**
     * 修改工艺书面料信息
     */
    @PreAuthorize("@ss.hasPermi('erp:techmaterial:edit')")
    @Log(title = "工艺书面料信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SampleTechMaterial sampleTechMaterial) {
        return toAjax(sampleTechMaterialService.updateSampleTechMaterial(sampleTechMaterial));
    }

    /**
     * 删除工艺书面料信息
     */
    @PreAuthorize("@ss.hasPermi('erp:techmaterial:remove')")
    @Log(title = "工艺书面料信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sampleTechMaterialService.deleteSampleTechMaterialByIds(ids));
    }
}
