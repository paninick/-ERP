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
import com.ruoyi.erp.domain.CorpContacts;
import com.ruoyi.erp.service.ICorpContactsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 公司联系人Controller
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
@RestController
@RequestMapping("/erp/contacts")
public class CorpContactsController extends BaseController {
    @Autowired
    private ICorpContactsService corpContactsService;

    /**
     * 查询公司联系人列表
     */
    @PreAuthorize("@ss.hasPermi('erp:contacts:list')")
    @GetMapping("/list")
    public TableDataInfo list(CorpContacts corpContacts) {
        startPage();
        List<CorpContacts> list = corpContactsService.selectCorpContactsList(corpContacts);
        return getDataTable(list);
    }

    /**
     * 导出公司联系人列表
     */
    @PreAuthorize("@ss.hasPermi('erp:contacts:export')")
    @Log(title = "公司联系人", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CorpContacts corpContacts) {
        List<CorpContacts> list = corpContactsService.selectCorpContactsList(corpContacts);
        ExcelUtil<CorpContacts> util = new ExcelUtil<CorpContacts>(CorpContacts.class);
        util.exportExcel(response, list, "公司联系人数据");
    }

    /**
     * 获取公司联系人详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:contacts:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(corpContactsService.selectCorpContactsById(id));
    }

    /**
     * 新增公司联系人
     */
    @PreAuthorize("@ss.hasPermi('erp:contacts:add')")
    @Log(title = "公司联系人", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CorpContacts corpContacts) {
        return toAjax(corpContactsService.insertCorpContacts(corpContacts));
    }

    /**
     * 修改公司联系人
     */
    @PreAuthorize("@ss.hasPermi('erp:contacts:edit')")
    @Log(title = "公司联系人", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CorpContacts corpContacts) {
        return toAjax(corpContactsService.updateCorpContacts(corpContacts));
    }

    /**
     * 删除公司联系人
     */
    @PreAuthorize("@ss.hasPermi('erp:contacts:remove')")
    @Log(title = "公司联系人", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(corpContactsService.deleteCorpContactsByIds(ids));
    }
}
