package com.ruoyi.wx.controller;

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
import com.ruoyi.wx.domain.TemplateMessage;
import com.ruoyi.wx.service.ITemplateMessageService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 模板消息Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping("/wx/templatemessage")
public class TemplateMessageController extends BaseController {
    @Autowired
    private ITemplateMessageService templateMessageService;

    /**
     * 查询模板消息列表
     */
    @PreAuthorize("@ss.hasPermi('wx:templatemessage:list')")
    @GetMapping("/list")
    public TableDataInfo list(TemplateMessage templateMessage) {
        startPage();
        List<TemplateMessage> list = templateMessageService.selectTemplateMessageList(templateMessage);
        return getDataTable(list);
    }

    /**
     * 导出模板消息列表
     */
    @PreAuthorize("@ss.hasPermi('wx:templatemessage:export')")
    @Log(title = "模板消息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TemplateMessage templateMessage) {
        List<TemplateMessage> list = templateMessageService.selectTemplateMessageList(templateMessage);
        ExcelUtil<TemplateMessage> util = new ExcelUtil<TemplateMessage>(TemplateMessage.class);
        util.exportExcel(response, list, "模板消息数据");
    }

    /**
     * 获取模板消息详细信息
     */
    @PreAuthorize("@ss.hasPermi('wx:templatemessage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(templateMessageService.selectTemplateMessageById(id));
    }

    /**
     * 新增模板消息
     */
    @PreAuthorize("@ss.hasPermi('wx:templatemessage:add')")
    @Log(title = "模板消息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TemplateMessage templateMessage) {
        return toAjax(templateMessageService.insertTemplateMessage(templateMessage));
    }

    /**
     * 修改模板消息
     */
    @PreAuthorize("@ss.hasPermi('wx:templatemessage:edit')")
    @Log(title = "模板消息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TemplateMessage templateMessage) {
        return toAjax(templateMessageService.updateTemplateMessage(templateMessage));
    }

    /**
     * 删除模板消息
     */
    @PreAuthorize("@ss.hasPermi('wx:templatemessage:remove')")
    @Log(title = "模板消息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(templateMessageService.deleteTemplateMessageByIds(ids));
    }
}
