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
import com.ruoyi.erp.domain.WarehouseArea;
import com.ruoyi.erp.service.IWarehouseAreaService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 仓库管理Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping("/erp/warehousearea")
public class WarehouseAreaController extends BaseController {
    @Autowired
    private IWarehouseAreaService warehouseAreaService;

    /**
     * 查询仓库管理列表
     */
    @PreAuthorize("@ss.hasPermi('erp:warehousearea:list')")
    @GetMapping("/list")
    public TableDataInfo list(WarehouseArea warehouseArea) {
        startPage();
        List<WarehouseArea> list = warehouseAreaService.selectWarehouseAreaList(warehouseArea);
        return getDataTable(list);
    }

    /**
     * 导出仓库管理列表
     */
    @PreAuthorize("@ss.hasPermi('erp:warehousearea:export')")
    @Log(title = "仓库管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WarehouseArea warehouseArea) {
        List<WarehouseArea> list = warehouseAreaService.selectWarehouseAreaList(warehouseArea);
        ExcelUtil<WarehouseArea> util = new ExcelUtil<WarehouseArea>(WarehouseArea.class);
        util.exportExcel(response, list, "仓库管理数据");
    }

    /**
     * 获取仓库管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:warehousearea:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(warehouseAreaService.selectWarehouseAreaById(id));
    }

    /**
     * 新增仓库管理
     */
    @PreAuthorize("@ss.hasPermi('erp:warehousearea:add')")
    @Log(title = "仓库管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WarehouseArea warehouseArea) {
        return toAjax(warehouseAreaService.insertWarehouseArea(warehouseArea));
    }

    /**
     * 修改仓库管理
     */
    @PreAuthorize("@ss.hasPermi('erp:warehousearea:edit')")
    @Log(title = "仓库管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WarehouseArea warehouseArea) {
        return toAjax(warehouseAreaService.updateWarehouseArea(warehouseArea));
    }

    /**
     * 删除仓库管理
     */
    @PreAuthorize("@ss.hasPermi('erp:warehousearea:remove')")
    @Log(title = "仓库管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(warehouseAreaService.deleteWarehouseAreaByIds(ids));
    }
}
