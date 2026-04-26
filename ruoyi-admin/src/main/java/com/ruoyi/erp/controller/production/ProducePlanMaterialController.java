package com.ruoyi.erp.controller.production;

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
import com.ruoyi.erp.domain.ProducePlanMaterial;
import com.ruoyi.erp.service.IProducePlanMaterialService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 生产计划物料明细Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping("/erp/planmaterial")
public class ProducePlanMaterialController extends BaseController {
    @Autowired
    private IProducePlanMaterialService producePlanMaterialService;

    /**
     * 查询生产计划物料明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:planmaterial:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProducePlanMaterial producePlanMaterial) {
        startPage();
        List<ProducePlanMaterial> list = producePlanMaterialService.selectProducePlanMaterialList(producePlanMaterial);
        return getDataTable(list);
    }

    /**
     * 导出生产计划物料明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:planmaterial:export')")
    @Log(title = "生产计划物料明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProducePlanMaterial producePlanMaterial) {
        List<ProducePlanMaterial> list = producePlanMaterialService.selectProducePlanMaterialList(producePlanMaterial);
        ExcelUtil<ProducePlanMaterial> util = new ExcelUtil<ProducePlanMaterial>(ProducePlanMaterial.class);
        util.exportExcel(response, list, "生产计划物料明细数据");
    }

    /**
     * 获取生产计划物料明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:planmaterial:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(producePlanMaterialService.selectProducePlanMaterialById(id));
    }

    /**
     * 新增生产计划物料明细
     */
    @PreAuthorize("@ss.hasPermi('erp:planmaterial:add')")
    @Log(title = "生产计划物料明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProducePlanMaterial producePlanMaterial) {
        return toAjax(producePlanMaterialService.insertProducePlanMaterial(producePlanMaterial));
    }

    /**
     * 修改生产计划物料明细
     */
    @PreAuthorize("@ss.hasPermi('erp:planmaterial:edit')")
    @Log(title = "生产计划物料明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProducePlanMaterial producePlanMaterial) {
        return toAjax(producePlanMaterialService.updateProducePlanMaterial(producePlanMaterial));
    }

    /**
     * 删除生产计划物料明细
     */
    @PreAuthorize("@ss.hasPermi('erp:planmaterial:remove')")
    @Log(title = "生产计划物料明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(producePlanMaterialService.deleteProducePlanMaterialByIds(ids));
    }
}
