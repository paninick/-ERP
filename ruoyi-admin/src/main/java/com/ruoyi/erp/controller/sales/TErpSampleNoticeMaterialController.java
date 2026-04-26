package com.ruoyi.erp.controller.sales;

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
import com.ruoyi.erp.domain.TErpSampleNoticeMaterial;
import com.ruoyi.erp.service.ITErpSampleNoticeMaterialService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 打样通知-面辅料要求Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping("/erp/sampleNotice/material")
public class TErpSampleNoticeMaterialController extends BaseController {
    @Autowired
    private ITErpSampleNoticeMaterialService tErpSampleNoticeMaterialService;

    /**
     * 查询打样通知-面辅料要求列表
     */
    @PreAuthorize("@ss.hasPermi('erp:material:list')")
    @GetMapping("/list")
    public TableDataInfo list(TErpSampleNoticeMaterial tErpSampleNoticeMaterial) {
        startPage();
        List<TErpSampleNoticeMaterial> list = tErpSampleNoticeMaterialService.selectTErpSampleNoticeMaterialList(tErpSampleNoticeMaterial);
        return getDataTable(list);
    }

    /**
     * 导出打样通知-面辅料要求列表
     */
    @PreAuthorize("@ss.hasPermi('erp:material:export')")
    @Log(title = "打样通知-面辅料要求", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TErpSampleNoticeMaterial tErpSampleNoticeMaterial) {
        List<TErpSampleNoticeMaterial> list = tErpSampleNoticeMaterialService.selectTErpSampleNoticeMaterialList(tErpSampleNoticeMaterial);
        ExcelUtil<TErpSampleNoticeMaterial> util = new ExcelUtil<TErpSampleNoticeMaterial>(TErpSampleNoticeMaterial.class);
        util.exportExcel(response, list, "打样通知-面辅料要求数据");
    }

    /**
     * 获取打样通知-面辅料要求详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:material:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(tErpSampleNoticeMaterialService.selectTErpSampleNoticeMaterialById(id));
    }

    /**
     * 新增打样通知-面辅料要求
     */
    @PreAuthorize("@ss.hasPermi('erp:material:add')")
    @Log(title = "打样通知-面辅料要求", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TErpSampleNoticeMaterial tErpSampleNoticeMaterial) {
        return toAjax(tErpSampleNoticeMaterialService.insertTErpSampleNoticeMaterial(tErpSampleNoticeMaterial));
    }

    /**
     * 修改打样通知-面辅料要求
     */
    @PreAuthorize("@ss.hasPermi('erp:material:edit')")
    @Log(title = "打样通知-面辅料要求", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TErpSampleNoticeMaterial tErpSampleNoticeMaterial) {
        return toAjax(tErpSampleNoticeMaterialService.updateTErpSampleNoticeMaterial(tErpSampleNoticeMaterial));
    }

    /**
     * 删除打样通知-面辅料要求
     */
    @PreAuthorize("@ss.hasPermi('erp:material:remove')")
    @Log(title = "打样通知-面辅料要求", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tErpSampleNoticeMaterialService.deleteTErpSampleNoticeMaterialByIds(ids));
    }
}
