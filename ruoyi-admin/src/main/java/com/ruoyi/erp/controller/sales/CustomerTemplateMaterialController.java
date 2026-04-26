package com.ruoyi.erp.controller.sales;

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
import com.ruoyi.erp.domain.CustomerTemplateMaterial;
import com.ruoyi.erp.service.ICustomerTemplateMaterialService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户模板面料Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping({"/erp/customerTemplate/material", "/erp/customerTemplateMaterial"})
public class CustomerTemplateMaterialController extends BaseController {
    @Autowired
    private ICustomerTemplateMaterialService customerTemplateMaterialService;

    /**
     * 查询客户模板面料列表
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:list')")
    @GetMapping("/list")
    public TableDataInfo list(CustomerTemplateMaterial customerTemplateMaterial) {
        startPage();
        List<CustomerTemplateMaterial> list = customerTemplateMaterialService.selectCustomerTemplateMaterialList(customerTemplateMaterial);
        return getDataTable(list);
    }

    /**
     * 导出客户模板面料列表
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:export')")
    @Log(title = "客户模板面料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CustomerTemplateMaterial customerTemplateMaterial) {
        List<CustomerTemplateMaterial> list = customerTemplateMaterialService.selectCustomerTemplateMaterialList(customerTemplateMaterial);
        ExcelUtil<CustomerTemplateMaterial> util = new ExcelUtil<CustomerTemplateMaterial>(CustomerTemplateMaterial.class);
        util.exportExcel(response, list, "客户模板面料数据");
    }

    /**
     * 获取客户模板面料详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(customerTemplateMaterialService.selectCustomerTemplateMaterialById(id));
    }

    /**
     * 新增客户模板面料
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:add')")
    @Log(title = "客户模板面料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CustomerTemplateMaterial customerTemplateMaterial) {
        return toAjax(customerTemplateMaterialService.insertCustomerTemplateMaterial(customerTemplateMaterial));
    }

    /**
     * 修改客户模板面料
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:edit')")
    @Log(title = "客户模板面料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CustomerTemplateMaterial customerTemplateMaterial) {
        return toAjax(customerTemplateMaterialService.updateCustomerTemplateMaterial(customerTemplateMaterial));
    }

    /**
     * 删除客户模板面料
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:remove')")
    @Log(title = "客户模板面料", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(customerTemplateMaterialService.deleteCustomerTemplateMaterialByIds(ids));
    }
}
