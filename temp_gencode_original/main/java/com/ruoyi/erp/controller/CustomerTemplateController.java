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
import com.ruoyi.erp.domain.CustomerTemplate;
import com.ruoyi.erp.service.ICustomerTemplateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户尺寸Controller
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
@RestController
@RequestMapping("/erp/customer")
public class CustomerTemplateController extends BaseController {
    @Autowired
    private ICustomerTemplateService customerTemplateService;

    /**
     * 查询客户尺寸列表
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:list')")
    @GetMapping("/list")
    public TableDataInfo list(CustomerTemplate customerTemplate) {
        startPage();
        List<CustomerTemplate> list = customerTemplateService.selectCustomerTemplateList(customerTemplate);
        return getDataTable(list);
    }

    /**
     * 导出客户尺寸列表
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:export')")
    @Log(title = "客户尺寸", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CustomerTemplate customerTemplate) {
        List<CustomerTemplate> list = customerTemplateService.selectCustomerTemplateList(customerTemplate);
        ExcelUtil<CustomerTemplate> util = new ExcelUtil<CustomerTemplate>(CustomerTemplate.class);
        util.exportExcel(response, list, "客户尺寸数据");
    }

    /**
     * 获取客户尺寸详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(customerTemplateService.selectCustomerTemplateById(id));
    }

    /**
     * 新增客户尺寸
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:add')")
    @Log(title = "客户尺寸", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CustomerTemplate customerTemplate) {
        return toAjax(customerTemplateService.insertCustomerTemplate(customerTemplate));
    }

    /**
     * 修改客户尺寸
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:edit')")
    @Log(title = "客户尺寸", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CustomerTemplate customerTemplate) {
        return toAjax(customerTemplateService.updateCustomerTemplate(customerTemplate));
    }

    /**
     * 删除客户尺寸
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:remove')")
    @Log(title = "客户尺寸", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(customerTemplateService.deleteCustomerTemplateByIds(ids));
    }
}
