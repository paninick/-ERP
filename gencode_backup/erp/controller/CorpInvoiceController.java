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
import com.ruoyi.erp.domain.CorpInvoice;
import com.ruoyi.erp.service.ICorpInvoiceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 公司开票信息Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping("/erp/invoice")
public class CorpInvoiceController extends BaseController {
    @Autowired
    private ICorpInvoiceService corpInvoiceService;

    /**
     * 查询公司开票信息列表
     */
    @PreAuthorize("@ss.hasPermi('erp:invoice:list')")
    @GetMapping("/list")
    public TableDataInfo list(CorpInvoice corpInvoice) {
        startPage();
        List<CorpInvoice> list = corpInvoiceService.selectCorpInvoiceList(corpInvoice);
        return getDataTable(list);
    }

    /**
     * 导出公司开票信息列表
     */
    @PreAuthorize("@ss.hasPermi('erp:invoice:export')")
    @Log(title = "公司开票信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CorpInvoice corpInvoice) {
        List<CorpInvoice> list = corpInvoiceService.selectCorpInvoiceList(corpInvoice);
        ExcelUtil<CorpInvoice> util = new ExcelUtil<CorpInvoice>(CorpInvoice.class);
        util.exportExcel(response, list, "公司开票信息数据");
    }

    /**
     * 获取公司开票信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:invoice:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(corpInvoiceService.selectCorpInvoiceById(id));
    }

    /**
     * 新增公司开票信息
     */
    @PreAuthorize("@ss.hasPermi('erp:invoice:add')")
    @Log(title = "公司开票信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CorpInvoice corpInvoice) {
        return toAjax(corpInvoiceService.insertCorpInvoice(corpInvoice));
    }

    /**
     * 修改公司开票信息
     */
    @PreAuthorize("@ss.hasPermi('erp:invoice:edit')")
    @Log(title = "公司开票信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CorpInvoice corpInvoice) {
        return toAjax(corpInvoiceService.updateCorpInvoice(corpInvoice));
    }

    /**
     * 删除公司开票信息
     */
    @PreAuthorize("@ss.hasPermi('erp:invoice:remove')")
    @Log(title = "公司开票信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(corpInvoiceService.deleteCorpInvoiceByIds(ids));
    }
}
