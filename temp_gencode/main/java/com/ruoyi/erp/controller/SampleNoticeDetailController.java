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
import com.ruoyi.erp.domain.SampleNoticeDetail;
import com.ruoyi.erp.service.ISampleNoticeDetailService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 打样通知-样衣要求Controller
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
@RestController
@RequestMapping("/erp/notice")
public class SampleNoticeDetailController extends BaseController {
    @Autowired
    private ISampleNoticeDetailService sampleNoticeDetailService;

    /**
     * 查询打样通知-样衣要求列表
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:list')")
    @GetMapping("/list")
    public TableDataInfo list(SampleNoticeDetail sampleNoticeDetail) {
        startPage();
        List<SampleNoticeDetail> list = sampleNoticeDetailService.selectSampleNoticeDetailList(sampleNoticeDetail);
        return getDataTable(list);
    }

    /**
     * 导出打样通知-样衣要求列表
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:export')")
    @Log(title = "打样通知-样衣要求", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SampleNoticeDetail sampleNoticeDetail) {
        List<SampleNoticeDetail> list = sampleNoticeDetailService.selectSampleNoticeDetailList(sampleNoticeDetail);
        ExcelUtil<SampleNoticeDetail> util = new ExcelUtil<SampleNoticeDetail>(SampleNoticeDetail.class);
        util.exportExcel(response, list, "打样通知-样衣要求数据");
    }

    /**
     * 获取打样通知-样衣要求详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(sampleNoticeDetailService.selectSampleNoticeDetailById(id));
    }

    /**
     * 新增打样通知-样衣要求
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:add')")
    @Log(title = "打样通知-样衣要求", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SampleNoticeDetail sampleNoticeDetail) {
        return toAjax(sampleNoticeDetailService.insertSampleNoticeDetail(sampleNoticeDetail));
    }

    /**
     * 修改打样通知-样衣要求
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:edit')")
    @Log(title = "打样通知-样衣要求", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SampleNoticeDetail sampleNoticeDetail) {
        return toAjax(sampleNoticeDetailService.updateSampleNoticeDetail(sampleNoticeDetail));
    }

    /**
     * 删除打样通知-样衣要求
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:remove')")
    @Log(title = "打样通知-样衣要求", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sampleNoticeDetailService.deleteSampleNoticeDetailByIds(ids));
    }
}
