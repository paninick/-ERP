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
import com.ruoyi.erp.domain.StandardColor;
import com.ruoyi.erp.service.IStandardColorService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 标准色卡Controller
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
@RestController
@RequestMapping("/erp/standardColor")
public class StandardColorController extends BaseController {
    @Autowired
    private IStandardColorService standardColorService;

    /**
     * 查询标准色卡列表
     */
    @PreAuthorize("@ss.hasPermi('erp:standardColor:list')")
    @GetMapping("/list")
    public TableDataInfo list(StandardColor standardColor) {
        startPage();
        List<StandardColor> list = standardColorService.selectStandardColorList(standardColor);
        return getDataTable(list);
    }

    /**
     * 导出标准色卡列表
     */
    @PreAuthorize("@ss.hasPermi('erp:standardColor:export')")
    @Log(title = "标准色卡", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StandardColor standardColor) {
        List<StandardColor> list = standardColorService.selectStandardColorList(standardColor);
        ExcelUtil<StandardColor> util = new ExcelUtil<StandardColor>(StandardColor.class);
        util.exportExcel(response, list, "标准色卡数据");
    }

    /**
     * 获取标准色卡详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:standardColor:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(standardColorService.selectStandardColorById(id));
    }

    /**
     * 新增标准色卡
     */
    @PreAuthorize("@ss.hasPermi('erp:standardColor:add')")
    @Log(title = "标准色卡", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StandardColor standardColor) {
        return toAjax(standardColorService.insertStandardColor(standardColor));
    }

    /**
     * 修改标准色卡
     */
    @PreAuthorize("@ss.hasPermi('erp:standardColor:edit')")
    @Log(title = "标准色卡", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StandardColor standardColor) {
        return toAjax(standardColorService.updateStandardColor(standardColor));
    }

    /**
     * 删除标准色卡
     */
    @PreAuthorize("@ss.hasPermi('erp:standardColor:remove')")
    @Log(title = "标准色卡", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(standardColorService.deleteStandardColorByIds(ids));
    }
}
