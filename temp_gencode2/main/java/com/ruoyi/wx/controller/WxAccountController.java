package com.ruoyi.wx.controller;

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
import com.ruoyi.wx.domain.WxAccount;
import com.ruoyi.wx.service.IWxAccountService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 微信账户Controller
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
@RestController
@RequestMapping("/wx/account")
public class WxAccountController extends BaseController {
    @Autowired
    private IWxAccountService wxAccountService;

    /**
     * 查询微信账户列表
     */
    @PreAuthorize("@ss.hasPermi('wx:account:list')")
    @GetMapping("/list")
    public TableDataInfo list(WxAccount wxAccount) {
        startPage();
        List<WxAccount> list = wxAccountService.selectWxAccountList(wxAccount);
        return getDataTable(list);
    }

    /**
     * 导出微信账户列表
     */
    @PreAuthorize("@ss.hasPermi('wx:account:export')")
    @Log(title = "微信账户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WxAccount wxAccount) {
        List<WxAccount> list = wxAccountService.selectWxAccountList(wxAccount);
        ExcelUtil<WxAccount> util = new ExcelUtil<WxAccount>(WxAccount.class);
        util.exportExcel(response, list, "微信账户数据");
    }

    /**
     * 获取微信账户详细信息
     */
    @PreAuthorize("@ss.hasPermi('wx:account:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(wxAccountService.selectWxAccountById(id));
    }

    /**
     * 新增微信账户
     */
    @PreAuthorize("@ss.hasPermi('wx:account:add')")
    @Log(title = "微信账户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WxAccount wxAccount) {
        return toAjax(wxAccountService.insertWxAccount(wxAccount));
    }

    /**
     * 修改微信账户
     */
    @PreAuthorize("@ss.hasPermi('wx:account:edit')")
    @Log(title = "微信账户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WxAccount wxAccount) {
        return toAjax(wxAccountService.updateWxAccount(wxAccount));
    }

    /**
     * 删除微信账户
     */
    @PreAuthorize("@ss.hasPermi('wx:account:remove')")
    @Log(title = "微信账户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(wxAccountService.deleteWxAccountByIds(ids));
    }
}
