
package com.ruoyi.erp.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.erp.domain.SampleNotice;
import com.ruoyi.erp.service.ISampleNoticeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 打样总览Controller
 *
 * @author zhangmingjian
 * @date 2026-04-12
 */
@RestController
@RequestMapping("/erp/overview")
public class OverviewController extends BaseController {
    @Autowired
    private ISampleNoticeService sampleNoticeService;

    /**
     * 查询打样总览列表
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:list')")
    @GetMapping("/list")
    public TableDataInfo list(SampleNotice sampleNotice) {
        startPage();
        List<SampleNotice> list = sampleNoticeService.selectSampleNoticeList(sampleNotice);
        return getDataTable(list);
    }

    /**
     * 导出打样总览列表
     */
    @PreAuthorize("@ss.hasPermi('erp:notice:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response, SampleNotice sampleNotice) {
        List<SampleNotice> list = sampleNoticeService.selectSampleNoticeList(sampleNotice);
        ExcelUtil<SampleNotice> util = new ExcelUtil<SampleNotice>(SampleNotice.class);
        util.exportExcel(response, list, "打样总览数据");
    }
}
