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
import com.ruoyi.erp.domain.Customer;
import com.ruoyi.erp.service.ICustomerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 业务系统-客户Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping("/erp/customer")
public class CustomerController extends BaseController {
    @Autowired
    private ICustomerService customerService;

    /**
     * 查询业务系统-客户列表
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:list')")
    @GetMapping("/list")
    public TableDataInfo list(Customer customer) {
        startPage();
        List<Customer> list = customerService.selectCustomerList(customer);
        return getDataTable(list);
    }

    /**
     * 导出业务系统-客户列表
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:export')")
    @Log(title = "业务系统-客户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Customer customer) {
        List<Customer> list = customerService.selectCustomerList(customer);
        ExcelUtil<Customer> util = new ExcelUtil<Customer>(Customer.class);
        util.exportExcel(response, list, "业务系统-客户数据");
    }

    /**
     * 获取业务系统-客户详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(customerService.selectCustomerById(id));
    }

    /**
     * 新增业务系统-客户
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:add')")
    @Log(title = "业务系统-客户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Customer customer) {
        return toAjax(customerService.insertCustomer(customer));
    }

    /**
     * 修改业务系统-客户
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:edit')")
    @Log(title = "业务系统-客户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Customer customer) {
        return toAjax(customerService.updateCustomer(customer));
    }

    /**
     * 删除业务系统-客户
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:remove')")
    @Log(title = "业务系统-客户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(customerService.deleteCustomerByIds(ids));
    }
}
