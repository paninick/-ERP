package com.ruoyi.erp.controller.masterdata;

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
import com.ruoyi.erp.domain.ProcessDef;
import com.ruoyi.erp.service.IProcessDefService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工序定义Controller
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
@RestController
@RequestMapping("/erp/processDef")
public class ProcessDefController extends BaseController {
    @Autowired
    private IProcessDefService processDefService;

    /**
     * 查询工序定义列表
     */
    @PreAuthorize("@ss.hasPermi('erp:processDef:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProcessDef processDef) {
        startPage();
        List<ProcessDef> list = processDefService.selectProcessDefList(processDef);
        return getDataTable(list);
    }

    /**
     * 导出工序定义列表
     */
    @PreAuthorize("@ss.hasPermi('erp:processDef:export')")
    @Log(title = "工序定义", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProcessDef processDef) {
        List<ProcessDef> list = processDefService.selectProcessDefList(processDef);
        ExcelUtil<ProcessDef> util = new ExcelUtil<ProcessDef>(ProcessDef.class);
        util.exportExcel(response, list, "工序定义数据");
    }

    /**
     * 获取工序定义详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:processDef:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(processDefService.selectProcessDefById(id));
    }

    /**
     * 新增工序定义
     */
    @PreAuthorize("@ss.hasPermi('erp:processDef:add')")
    @Log(title = "工序定义", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProcessDef processDef) {
        return toAjax(processDefService.insertProcessDef(processDef));
    }

    /**
     * 修改工序定义
     */
    @PreAuthorize("@ss.hasPermi('erp:processDef:edit')")
    @Log(title = "工序定义", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProcessDef processDef) {
        return toAjax(processDefService.updateProcessDef(processDef));
    }

    /**
     * 删除工序定义
     */
    @PreAuthorize("@ss.hasPermi('erp:processDef:remove')")
    @Log(title = "工序定义", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(processDefService.deleteProcessDefByIds(ids));
    }
}
