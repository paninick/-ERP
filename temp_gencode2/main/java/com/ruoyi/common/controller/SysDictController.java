package com.ruoyi.common.controller;

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
import com.ruoyi.common.domain.SysDict;
import com.ruoyi.common.service.ISysDictService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 字典数据Controller
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
@RestController
@RequestMapping("/common/sysdict")
public class SysDictController extends BaseController {
    @Autowired
    private ISysDictService sysDictService;

    /**
     * 查询字典数据列表
     */
    @PreAuthorize("@ss.hasPermi('common:sysdict:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysDict sysDict) {
        startPage();
        List<SysDict> list = sysDictService.selectSysDictList(sysDict);
        return getDataTable(list);
    }

    /**
     * 导出字典数据列表
     */
    @PreAuthorize("@ss.hasPermi('common:sysdict:export')")
    @Log(title = "字典数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDict sysDict) {
        List<SysDict> list = sysDictService.selectSysDictList(sysDict);
        ExcelUtil<SysDict> util = new ExcelUtil<SysDict>(SysDict.class);
        util.exportExcel(response, list, "字典数据数据");
    }

    /**
     * 获取字典数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('common:sysdict:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(sysDictService.selectSysDictById(id));
    }

    /**
     * 新增字典数据
     */
    @PreAuthorize("@ss.hasPermi('common:sysdict:add')")
    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysDict sysDict) {
        return toAjax(sysDictService.insertSysDict(sysDict));
    }

    /**
     * 修改字典数据
     */
    @PreAuthorize("@ss.hasPermi('common:sysdict:edit')")
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysDict sysDict) {
        return toAjax(sysDictService.updateSysDict(sysDict));
    }

    /**
     * 删除字典数据
     */
    @PreAuthorize("@ss.hasPermi('common:sysdict:remove')")
    @Log(title = "字典数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sysDictService.deleteSysDictByIds(ids));
    }
}
