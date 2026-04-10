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
import com.ruoyi.erp.domain.SalesOrderMaterial;
import com.ruoyi.erp.service.ISalesOrderMaterialService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 大货订单物料明细Controller
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
@RestController
@RequestMapping("/erp/salesmaterial")
public class SalesOrderMaterialController extends BaseController {
    @Autowired
    private ISalesOrderMaterialService salesOrderMaterialService;

    /**
     * 查询大货订单物料明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:salesmaterial:list')")
    @GetMapping("/list")
    public TableDataInfo list(SalesOrderMaterial salesOrderMaterial) {
        startPage();
        List<SalesOrderMaterial> list = salesOrderMaterialService.selectSalesOrderMaterialList(salesOrderMaterial);
        return getDataTable(list);
    }

    /**
     * 导出大货订单物料明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:salesmaterial:export')")
    @Log(title = "大货订单物料明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SalesOrderMaterial salesOrderMaterial) {
        List<SalesOrderMaterial> list = salesOrderMaterialService.selectSalesOrderMaterialList(salesOrderMaterial);
        ExcelUtil<SalesOrderMaterial> util = new ExcelUtil<SalesOrderMaterial>(SalesOrderMaterial.class);
        util.exportExcel(response, list, "大货订单物料明细数据");
    }

    /**
     * 获取大货订单物料明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:salesmaterial:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(salesOrderMaterialService.selectSalesOrderMaterialById(id));
    }

    /**
     * 新增大货订单物料明细
     */
    @PreAuthorize("@ss.hasPermi('erp:salesmaterial:add')")
    @Log(title = "大货订单物料明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SalesOrderMaterial salesOrderMaterial) {
        return toAjax(salesOrderMaterialService.insertSalesOrderMaterial(salesOrderMaterial));
    }

    /**
     * 修改大货订单物料明细
     */
    @PreAuthorize("@ss.hasPermi('erp:salesmaterial:edit')")
    @Log(title = "大货订单物料明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SalesOrderMaterial salesOrderMaterial) {
        return toAjax(salesOrderMaterialService.updateSalesOrderMaterial(salesOrderMaterial));
    }

    /**
     * 删除大货订单物料明细
     */
    @PreAuthorize("@ss.hasPermi('erp:salesmaterial:remove')")
    @Log(title = "大货订单物料明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(salesOrderMaterialService.deleteSalesOrderMaterialByIds(ids));
    }
}
