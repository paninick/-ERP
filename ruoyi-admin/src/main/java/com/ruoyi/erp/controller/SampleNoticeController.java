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
import com.ruoyi.erp.domain.SampleNotice;
import com.ruoyi.erp.service.ISampleNoticeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 打样通知Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping("/erp/notice")
public class SampleNoticeController extends BaseController {
    @Autowired
    private ISampleNoticeService sampleNoticeService;

    /**
     * 查询打样通知列表
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:list')")
    @GetMapping("/list")
    public TableDataInfo list(SampleNotice sampleNotice) {
        startPage();
        List<SampleNotice> list = sampleNoticeService.selectSampleNoticeList(sampleNotice);
        return getDataTable(list);
    }

    /**
     * 导出打样通知列表
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:export')")
    @Log(title = "打样通知", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SampleNotice sampleNotice) {
        List<SampleNotice> list = sampleNoticeService.selectSampleNoticeList(sampleNotice);
        ExcelUtil<SampleNotice> util = new ExcelUtil<SampleNotice>(SampleNotice.class);
        util.exportExcel(response, list, "打样通知数据");
    }

    /**
     * 获取打样通知详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(sampleNoticeService.selectSampleNoticeById(id));
    }

    /**
     * 新增打样通知
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:add')")
    @Log(title = "打样通知", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SampleNotice sampleNotice) {
        return toAjax(sampleNoticeService.insertSampleNotice(sampleNotice));
    }

    /**
     * 修改打样通知
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:edit')")
    @Log(title = "打样通知", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SampleNotice sampleNotice) {
        return toAjax(sampleNoticeService.updateSampleNotice(sampleNotice));
    }

    /**
     * 删除打样通知
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:remove')")
    @Log(title = "打样通知", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sampleNoticeService.deleteSampleNoticeByIds(ids));
    }

    /**
     * 导入打样通知 Excel
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:import')")
    @Log(title = "打样通知导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        if (file == null || file.isEmpty()) {
            return error("上传文件不能为空");
        }
        ExcelUtil<SampleNotice> util = new ExcelUtil<>(SampleNotice.class);
        List<SampleNotice> list = util.importExcel(file.getInputStream());
        String message = sampleNoticeService.importSampleNotice(list, updateSupport);
        return success(message);
    }

    /**
     * 下载打样通知导入模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<SampleNotice> util = new ExcelUtil<>(SampleNotice.class);
        util.importTemplateExcel(response, "打样通知数据");
    }
}
