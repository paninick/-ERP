package com.ruoyi.web.controller.erp;

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
import com.ruoyi.system.domain.SysAutomation;
import com.ruoyi.system.service.ISysAutomationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 自动化任务Controller
 * 
 * @author ruoyi
 * @date 2026-04-02
 */
@RestController
@RequestMapping("/erp/automation")
public class SysAutomationController extends BaseController
{
    @Autowired
    private ISysAutomationService sysAutomationService;

    /**
     * 查询自动化任务列表
     */
    @PreAuthorize("@ss.hasPermi('erp:automation:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysAutomation sysAutomation)
    {
        startPage();
        List<SysAutomation> list = sysAutomationService.selectSysAutomationList(sysAutomation);
        return getDataTable(list);
    }

    /**
     * 导出自动化任务列表
     */
    @PreAuthorize("@ss.hasPermi('erp:automation:export')")
    @Log(title = "自动化任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysAutomation sysAutomation)
    {
        List<SysAutomation> list = sysAutomationService.selectSysAutomationList(sysAutomation);
        ExcelUtil<SysAutomation> util = new ExcelUtil<SysAutomation>(SysAutomation.class);
        util.exportExcel(response, list, "自动化任务数据");
    }

    /**
     * 获取自动化任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:automation:query')")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable("taskId") Long taskId)
    {
        return AjaxResult.success(sysAutomationService.selectSysAutomationById(taskId));
    }

    /**
     * 新增自动化任务
     */
    @PreAuthorize("@ss.hasPermi('erp:automation:add')")
    @Log(title = "自动化任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysAutomation sysAutomation)
    {
        return toAjax(sysAutomationService.insertSysAutomation(sysAutomation));
    }

    /**
     * 修改自动化任务
     */
    @PreAuthorize("@ss.hasPermi('erp:automation:edit')")
    @Log(title = "自动化任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysAutomation sysAutomation)
    {
        return toAjax(sysAutomationService.updateSysAutomation(sysAutomation));
    }

    /**
     * 删除自动化任务
     */
    @PreAuthorize("@ss.hasPermi('erp:automation:remove')")
    @Log(title = "自动化任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{taskId}")
    public AjaxResult remove(@PathVariable Long[] taskId)
    {
        return toAjax(sysAutomationService.deleteSysAutomationByIds(taskId));
    }

    /**
     * 运行自动化任务
     */
    @PreAuthorize("@ss.hasPermi('erp:automation:run')")
    @Log(title = "自动化任务", businessType = BusinessType.UPDATE)
    @PutMapping("/run/{taskId}")
    public AjaxResult run(@PathVariable Long taskId)
    {
        return toAjax(sysAutomationService.runSysAutomation(taskId));
    }

    /**
     * 暂停自动化任务
     */
    @PreAuthorize("@ss.hasPermi('erp:automation:pause')")
    @Log(title = "自动化任务", businessType = BusinessType.UPDATE)
    @PutMapping("/pause/{taskId}")
    public AjaxResult pause(@PathVariable Long taskId)
    {
        return toAjax(sysAutomationService.pauseSysAutomation(taskId));
    }
}
