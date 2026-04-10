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
import com.ruoyi.erp.domain.Warehouse;
import com.ruoyi.erp.service.IWarehouseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 库区管理Controller
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
@RestController
@RequestMapping("/erp/warehouse")
public class WarehouseController extends BaseController {
    @Autowired
    private IWarehouseService warehouseService;

    /**
     * 查询库区管理列表
     */
    @PreAuthorize("@ss.hasPermi('erp:warehouse:list')")
    @GetMapping("/list")
    public TableDataInfo list(Warehouse warehouse) {
        startPage();
        List<Warehouse> list = warehouseService.selectWarehouseList(warehouse);
        return getDataTable(list);
    }

    /**
     * 导出库区管理列表
     */
    @PreAuthorize("@ss.hasPermi('erp:warehouse:export')")
    @Log(title = "库区管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Warehouse warehouse) {
        List<Warehouse> list = warehouseService.selectWarehouseList(warehouse);
        ExcelUtil<Warehouse> util = new ExcelUtil<Warehouse>(Warehouse.class);
        util.exportExcel(response, list, "库区管理数据");
    }

    /**
     * 获取库区管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:warehouse:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(warehouseService.selectWarehouseById(id));
    }

    /**
     * 新增库区管理
     */
    @PreAuthorize("@ss.hasPermi('erp:warehouse:add')")
    @Log(title = "库区管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Warehouse warehouse) {
        return toAjax(warehouseService.insertWarehouse(warehouse));
    }

    /**
     * 修改库区管理
     */
    @PreAuthorize("@ss.hasPermi('erp:warehouse:edit')")
    @Log(title = "库区管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Warehouse warehouse) {
        return toAjax(warehouseService.updateWarehouse(warehouse));
    }

    /**
     * 删除库区管理
     */
    @PreAuthorize("@ss.hasPermi('erp:warehouse:remove')")
    @Log(title = "库区管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(warehouseService.deleteWarehouseByIds(ids));
    }
}
