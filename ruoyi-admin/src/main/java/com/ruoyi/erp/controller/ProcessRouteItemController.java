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
import com.ruoyi.erp.domain.ProcessRouteItem;
import com.ruoyi.erp.service.IProcessRouteItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工艺路线明细Controller
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
@RestController
@RequestMapping("/erp/processRouteItem")
public class ProcessRouteItemController extends BaseController {
    @Autowired
    private IProcessRouteItemService processRouteItemService;

    /**
     * 查询工艺路线明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:processRouteItem:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProcessRouteItem processRouteItem) {
        startPage();
        List<ProcessRouteItem> list = processRouteItemService.selectProcessRouteItemList(processRouteItem);
        return getDataTable(list);
    }

    /**
     * 导出工艺路线明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:processRouteItem:export')")
    @Log(title = "工艺路线明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProcessRouteItem processRouteItem) {
        List<ProcessRouteItem> list = processRouteItemService.selectProcessRouteItemList(processRouteItem);
        ExcelUtil<ProcessRouteItem> util = new ExcelUtil<ProcessRouteItem>(ProcessRouteItem.class);
        util.exportExcel(response, list, "工艺路线明细数据");
    }

    /**
     * 获取工艺路线明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:processRouteItem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(processRouteItemService.selectProcessRouteItemById(id));
    }

    /**
     * 新增工艺路线明细
     */
    @PreAuthorize("@ss.hasPermi('erp:processRouteItem:add')")
    @Log(title = "工艺路线明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProcessRouteItem processRouteItem) {
        return toAjax(processRouteItemService.insertProcessRouteItem(processRouteItem));
    }

    /**
     * 修改工艺路线明细
     */
    @PreAuthorize("@ss.hasPermi('erp:processRouteItem:edit')")
    @Log(title = "工艺路线明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProcessRouteItem processRouteItem) {
        return toAjax(processRouteItemService.updateProcessRouteItem(processRouteItem));
    }

    /**
     * 删除工艺路线明细
     */
    @PreAuthorize("@ss.hasPermi('erp:processRouteItem:remove')")
    @Log(title = "工艺路线明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(processRouteItemService.deleteProcessRouteItemByIds(ids));
    }
}
