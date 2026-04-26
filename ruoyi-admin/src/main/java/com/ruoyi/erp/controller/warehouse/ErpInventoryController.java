package com.ruoyi.erp.controller.warehouse;

import java.util.List;
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
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.erp.domain.ErpInventory;
import com.ruoyi.erp.service.IErpInventoryService;

/**
 * 库存Controller
 *
 * @author zhangmingjian
 * @date 2026-04-20
 */
@RestController
@RequestMapping("/erp/inventory")
public class ErpInventoryController extends BaseController {

    @Autowired
    private IErpInventoryService erpInventoryService;

    /**
     * 查询库存列表
     */
    @PreAuthorize("@ss.hasPermi('erp:inventory:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpInventory erpInventory) {
        startPage();
        List<ErpInventory> list = erpInventoryService.selectErpInventoryList(erpInventory);
        return getDataTable(list);
    }

    /**
     * 获取库存详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:inventory:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        ErpInventory inventory = erpInventoryService.selectErpInventoryById(id);
        return success(inventory);
    }

    /**
     * 新增库存
     */
    @PreAuthorize("@ss.hasPermi('erp:inventory:add')")
    @Log(title = "库存", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpInventory erpInventory) {
        return toAjax(erpInventoryService.insertErpInventory(erpInventory));
    }

    /**
     * 修改库存
     */
    @PreAuthorize("@ss.hasPermi('erp:inventory:edit')")
    @Log(title = "库存", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpInventory erpInventory) {
        return toAjax(erpInventoryService.updateErpInventory(erpInventory));
    }

    /**
     * 删除库存
     */
    @PreAuthorize("@ss.hasPermi('erp:inventory:remove')")
    @Log(title = "库存", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(erpInventoryService.deleteErpInventoryByIds(ids));
    }
}
