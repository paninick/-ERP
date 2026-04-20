package com.ruoyi.web.controller.erp;

import java.util.List;
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
import com.ruoyi.erp.domain.ErpInventory;
import com.ruoyi.erp.service.IErpInventoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 单据下推Controller
 *
 * @author zhangmingjian
 * @date 2026-04-20
 */
@RestController
@RequestMapping("/erp/pushdown")
public class ErpPushDownController extends BaseController {

    @Autowired
    private IErpInventoryService erpInventoryService;

    /**
     * 查询库存总表列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ErpInventory erpInventory) {
        startPage();
        List<ErpInventory> list = erpInventoryService.selectErpInventoryList(erpInventory);
        return getDataTable(list);
    }

    /**
     * 获取库存总表详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(erpInventoryService.selectErpInventoryById(id));
    }

    /**
     * 新增库存总表
     */
    @Log(title = "库存总表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpInventory erpInventory) {
        return toAjax(erpInventoryService.insertErpInventory(erpInventory));
    }

    /**
     * 修改库存总表
     */
    @Log(title = "库存总表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpInventory erpInventory) {
        return toAjax(erpInventoryService.updateErpInventory(erpInventory));
    }

    /**
     * 删除库存总表
     */
    @Log(title = "库存总表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(erpInventoryService.deleteErpInventoryByIds(ids));
    }
}
