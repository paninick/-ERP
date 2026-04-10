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
import com.ruoyi.erp.domain.WarehouseLocation;
import com.ruoyi.erp.service.IWarehouseLocationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 仓位管理Controller
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
@RestController
@RequestMapping("/erp/warehouselocation")
public class WarehouseLocationController extends BaseController {
    @Autowired
    private IWarehouseLocationService warehouseLocationService;

    /**
     * 查询仓位管理列表
     */
    @PreAuthorize("@ss.hasPermi('erp:warehouselocation:list')")
    @GetMapping("/list")
    public TableDataInfo list(WarehouseLocation warehouseLocation) {
        startPage();
        List<WarehouseLocation> list = warehouseLocationService.selectWarehouseLocationList(warehouseLocation);
        return getDataTable(list);
    }

    /**
     * 导出仓位管理列表
     */
    @PreAuthorize("@ss.hasPermi('erp:warehouselocation:export')")
    @Log(title = "仓位管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WarehouseLocation warehouseLocation) {
        List<WarehouseLocation> list = warehouseLocationService.selectWarehouseLocationList(warehouseLocation);
        ExcelUtil<WarehouseLocation> util = new ExcelUtil<WarehouseLocation>(WarehouseLocation.class);
        util.exportExcel(response, list, "仓位管理数据");
    }

    /**
     * 获取仓位管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:warehouselocation:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(warehouseLocationService.selectWarehouseLocationById(id));
    }

    /**
     * 新增仓位管理
     */
    @PreAuthorize("@ss.hasPermi('erp:warehouselocation:add')")
    @Log(title = "仓位管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WarehouseLocation warehouseLocation) {
        return toAjax(warehouseLocationService.insertWarehouseLocation(warehouseLocation));
    }

    /**
     * 修改仓位管理
     */
    @PreAuthorize("@ss.hasPermi('erp:warehouselocation:edit')")
    @Log(title = "仓位管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WarehouseLocation warehouseLocation) {
        return toAjax(warehouseLocationService.updateWarehouseLocation(warehouseLocation));
    }

    /**
     * 删除仓位管理
     */
    @PreAuthorize("@ss.hasPermi('erp:warehouselocation:remove')")
    @Log(title = "仓位管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(warehouseLocationService.deleteWarehouseLocationByIds(ids));
    }
}
