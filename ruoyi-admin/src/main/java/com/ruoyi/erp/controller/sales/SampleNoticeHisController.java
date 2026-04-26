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
import com.ruoyi.erp.domain.SampleNoticeHis;
import com.ruoyi.erp.service.ISampleNoticeHisService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 打样通知历史Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping("/erp/notice/his")
public class SampleNoticeHisController extends BaseController {
    @Autowired
    private ISampleNoticeHisService sampleNoticeHisService;

    /**
     * 查询打样通知历史列表
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:list')")
    @GetMapping("/list")
    public TableDataInfo list(SampleNoticeHis sampleNoticeHis) {
        startPage();
        List<SampleNoticeHis> list = sampleNoticeHisService.selectSampleNoticeHisList(sampleNoticeHis);
        return getDataTable(list);
    }

    /**
     * 导出打样通知历史列表
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:export')")
    @Log(title = "打样通知历史", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SampleNoticeHis sampleNoticeHis) {
        List<SampleNoticeHis> list = sampleNoticeHisService.selectSampleNoticeHisList(sampleNoticeHis);
        ExcelUtil<SampleNoticeHis> util = new ExcelUtil<SampleNoticeHis>(SampleNoticeHis.class);
        util.exportExcel(response, list, "打样通知历史数据");
    }

    /**
     * 获取打样通知历史详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(sampleNoticeHisService.selectSampleNoticeHisById(id));
    }

    /**
     * 新增打样通知历史
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:add')")
    @Log(title = "打样通知历史", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SampleNoticeHis sampleNoticeHis) {
        return toAjax(sampleNoticeHisService.insertSampleNoticeHis(sampleNoticeHis));
    }

    /**
     * 修改打样通知历史
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:edit')")
    @Log(title = "打样通知历史", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SampleNoticeHis sampleNoticeHis) {
        return toAjax(sampleNoticeHisService.updateSampleNoticeHis(sampleNoticeHis));
    }

    /**
     * 删除打样通知历史
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:remove')")
    @Log(title = "打样通知历史", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sampleNoticeHisService.deleteSampleNoticeHisByIds(ids));
    }
}
