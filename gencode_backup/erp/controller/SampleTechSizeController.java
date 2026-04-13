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
import com.ruoyi.erp.domain.SampleTechSize;
import com.ruoyi.erp.service.ISampleTechSizeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工艺书尺寸细节Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping("/erp/techsize")
public class SampleTechSizeController extends BaseController {
    @Autowired
    private ISampleTechSizeService sampleTechSizeService;

    /**
     * 查询工艺书尺寸细节列表
     */
    @PreAuthorize("@ss.hasPermi('erp:techsize:list')")
    @GetMapping("/list")
    public TableDataInfo list(SampleTechSize sampleTechSize) {
        startPage();
        List<SampleTechSize> list = sampleTechSizeService.selectSampleTechSizeList(sampleTechSize);
        return getDataTable(list);
    }

    /**
     * 导出工艺书尺寸细节列表
     */
    @PreAuthorize("@ss.hasPermi('erp:techsize:export')")
    @Log(title = "工艺书尺寸细节", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SampleTechSize sampleTechSize) {
        List<SampleTechSize> list = sampleTechSizeService.selectSampleTechSizeList(sampleTechSize);
        ExcelUtil<SampleTechSize> util = new ExcelUtil<SampleTechSize>(SampleTechSize.class);
        util.exportExcel(response, list, "工艺书尺寸细节数据");
    }

    /**
     * 获取工艺书尺寸细节详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:techsize:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(sampleTechSizeService.selectSampleTechSizeById(id));
    }

    /**
     * 新增工艺书尺寸细节
     */
    @PreAuthorize("@ss.hasPermi('erp:techsize:add')")
    @Log(title = "工艺书尺寸细节", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SampleTechSize sampleTechSize) {
        return toAjax(sampleTechSizeService.insertSampleTechSize(sampleTechSize));
    }

    /**
     * 修改工艺书尺寸细节
     */
    @PreAuthorize("@ss.hasPermi('erp:techsize:edit')")
    @Log(title = "工艺书尺寸细节", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SampleTechSize sampleTechSize) {
        return toAjax(sampleTechSizeService.updateSampleTechSize(sampleTechSize));
    }

    /**
     * 删除工艺书尺寸细节
     */
    @PreAuthorize("@ss.hasPermi('erp:techsize:remove')")
    @Log(title = "工艺书尺寸细节", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sampleTechSizeService.deleteSampleTechSizeByIds(ids));
    }
}
