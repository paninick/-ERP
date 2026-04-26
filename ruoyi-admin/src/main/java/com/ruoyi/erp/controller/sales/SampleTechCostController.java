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
import com.ruoyi.erp.domain.SampleTechCost;
import com.ruoyi.erp.service.ISampleTechCostService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工艺书单耗Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping("/erp/cost")
public class SampleTechCostController extends BaseController {
    @Autowired
    private ISampleTechCostService sampleTechCostService;

    /**
     * 查询工艺书单耗列表
     */
    @PreAuthorize("@ss.hasPermi('erp:cost:list')")
    @GetMapping("/list")
    public TableDataInfo list(SampleTechCost sampleTechCost) {
        startPage();
        List<SampleTechCost> list = sampleTechCostService.selectSampleTechCostList(sampleTechCost);
        return getDataTable(list);
    }

    /**
     * 导出工艺书单耗列表
     */
    @PreAuthorize("@ss.hasPermi('erp:cost:export')")
    @Log(title = "工艺书单耗", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SampleTechCost sampleTechCost) {
        List<SampleTechCost> list = sampleTechCostService.selectSampleTechCostList(sampleTechCost);
        ExcelUtil<SampleTechCost> util = new ExcelUtil<SampleTechCost>(SampleTechCost.class);
        util.exportExcel(response, list, "工艺书单耗数据");
    }

    /**
     * 获取工艺书单耗详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:cost:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(sampleTechCostService.selectSampleTechCostById(id));
    }

    /**
     * 新增工艺书单耗
     */
    @PreAuthorize("@ss.hasPermi('erp:cost:add')")
    @Log(title = "工艺书单耗", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SampleTechCost sampleTechCost) {
        return toAjax(sampleTechCostService.insertSampleTechCost(sampleTechCost));
    }

    /**
     * 修改工艺书单耗
     */
    @PreAuthorize("@ss.hasPermi('erp:cost:edit')")
    @Log(title = "工艺书单耗", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SampleTechCost sampleTechCost) {
        return toAjax(sampleTechCostService.updateSampleTechCost(sampleTechCost));
    }

    /**
     * 删除工艺书单耗
     */
    @PreAuthorize("@ss.hasPermi('erp:cost:remove')")
    @Log(title = "工艺书单耗", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sampleTechCostService.deleteSampleTechCostByIds(ids));
    }
}
