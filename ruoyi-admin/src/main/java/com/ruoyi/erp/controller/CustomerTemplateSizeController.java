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
import com.ruoyi.erp.domain.CustomerTemplateSize;
import com.ruoyi.erp.service.ICustomerTemplateSizeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户尺寸细节Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping({"/erp/customerTemplate/size", "/erp/customerTemplateSize"})
public class CustomerTemplateSizeController extends BaseController {
    @Autowired
    private ICustomerTemplateSizeService customerTemplateSizeService;

    /**
     * 查询客户尺寸细节列表
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:list')")
    @GetMapping("/list")
    public TableDataInfo list(CustomerTemplateSize customerTemplateSize) {
        startPage();
        List<CustomerTemplateSize> list = customerTemplateSizeService.selectCustomerTemplateSizeList(customerTemplateSize);
        return getDataTable(list);
    }

    /**
     * 导出客户尺寸细节列表
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:export')")
    @Log(title = "客户尺寸细节", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CustomerTemplateSize customerTemplateSize) {
        List<CustomerTemplateSize> list = customerTemplateSizeService.selectCustomerTemplateSizeList(customerTemplateSize);
        ExcelUtil<CustomerTemplateSize> util = new ExcelUtil<CustomerTemplateSize>(CustomerTemplateSize.class);
        util.exportExcel(response, list, "客户尺寸细节数据");
    }

    /**
     * 获取客户尺寸细节详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(customerTemplateSizeService.selectCustomerTemplateSizeById(id));
    }

    /**
     * 新增客户尺寸细节
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:add')")
    @Log(title = "客户尺寸细节", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CustomerTemplateSize customerTemplateSize) {
        return toAjax(customerTemplateSizeService.insertCustomerTemplateSize(customerTemplateSize));
    }

    /**
     * 修改客户尺寸细节
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:edit')")
    @Log(title = "客户尺寸细节", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CustomerTemplateSize customerTemplateSize) {
        return toAjax(customerTemplateSizeService.updateCustomerTemplateSize(customerTemplateSize));
    }

    /**
     * 删除客户尺寸细节
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:remove')")
    @Log(title = "客户尺寸细节", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(customerTemplateSizeService.deleteCustomerTemplateSizeByIds(ids));
    }
}
