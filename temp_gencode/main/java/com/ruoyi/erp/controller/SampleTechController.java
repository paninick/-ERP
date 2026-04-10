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
import com.ruoyi.erp.domain.SampleTech;
import com.ruoyi.erp.service.ISampleTechService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工艺指示书Controller
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
@RestController
@RequestMapping("/erp/tech")
public class SampleTechController extends BaseController {
    @Autowired
    private ISampleTechService sampleTechService;

    /**
     * 查询工艺指示书列表
     */
    @PreAuthorize("@ss.hasPermi('erp:tech:list')")
    @GetMapping("/list")
    public TableDataInfo list(SampleTech sampleTech) {
        startPage();
        List<SampleTech> list = sampleTechService.selectSampleTechList(sampleTech);
        return getDataTable(list);
    }

    /**
     * 导出工艺指示书列表
     */
    @PreAuthorize("@ss.hasPermi('erp:tech:export')")
    @Log(title = "工艺指示书", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SampleTech sampleTech) {
        List<SampleTech> list = sampleTechService.selectSampleTechList(sampleTech);
        ExcelUtil<SampleTech> util = new ExcelUtil<SampleTech>(SampleTech.class);
        util.exportExcel(response, list, "工艺指示书数据");
    }

    /**
     * 获取工艺指示书详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:tech:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(sampleTechService.selectSampleTechById(id));
    }

    /**
     * 新增工艺指示书
     */
    @PreAuthorize("@ss.hasPermi('erp:tech:add')")
    @Log(title = "工艺指示书", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SampleTech sampleTech) {
        return toAjax(sampleTechService.insertSampleTech(sampleTech));
    }

    /**
     * 修改工艺指示书
     */
    @PreAuthorize("@ss.hasPermi('erp:tech:edit')")
    @Log(title = "工艺指示书", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SampleTech sampleTech) {
        return toAjax(sampleTechService.updateSampleTech(sampleTech));
    }

    /**
     * 删除工艺指示书
     */
    @PreAuthorize("@ss.hasPermi('erp:tech:remove')")
    @Log(title = "工艺指示书", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sampleTechService.deleteSampleTechByIds(ids));
    }
}
