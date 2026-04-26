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
import com.ruoyi.erp.domain.SampleNoticeFile;
import com.ruoyi.erp.service.ISampleNoticeFileService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 打样通知-文件附录Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping("/erp/notice/file")
public class SampleNoticeFileController extends BaseController {
    @Autowired
    private ISampleNoticeFileService sampleNoticeFileService;

    /**
     * 查询打样通知-文件附录列表
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:list')")
    @GetMapping("/list")
    public TableDataInfo list(SampleNoticeFile sampleNoticeFile) {
        startPage();
        List<SampleNoticeFile> list = sampleNoticeFileService.selectSampleNoticeFileList(sampleNoticeFile);
        return getDataTable(list);
    }

    /**
     * 导出打样通知-文件附录列表
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:export')")
    @Log(title = "打样通知-文件附录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SampleNoticeFile sampleNoticeFile) {
        List<SampleNoticeFile> list = sampleNoticeFileService.selectSampleNoticeFileList(sampleNoticeFile);
        ExcelUtil<SampleNoticeFile> util = new ExcelUtil<SampleNoticeFile>(SampleNoticeFile.class);
        util.exportExcel(response, list, "打样通知-文件附录数据");
    }

    /**
     * 获取打样通知-文件附录详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(sampleNoticeFileService.selectSampleNoticeFileById(id));
    }

    /**
     * 新增打样通知-文件附录
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:add')")
    @Log(title = "打样通知-文件附录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SampleNoticeFile sampleNoticeFile) {
        return toAjax(sampleNoticeFileService.insertSampleNoticeFile(sampleNoticeFile));
    }

    /**
     * 修改打样通知-文件附录
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:edit')")
    @Log(title = "打样通知-文件附录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SampleNoticeFile sampleNoticeFile) {
        return toAjax(sampleNoticeFileService.updateSampleNoticeFile(sampleNoticeFile));
    }

    /**
     * 删除打样通知-文件附录
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:remove')")
    @Log(title = "打样通知-文件附录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sampleNoticeFileService.deleteSampleNoticeFileByIds(ids));
    }
}
