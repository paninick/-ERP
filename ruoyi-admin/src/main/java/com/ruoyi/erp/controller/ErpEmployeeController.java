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
import com.ruoyi.erp.domain.ErpEmployee;
import com.ruoyi.erp.service.IErpEmployeeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 员工Controller
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
@RestController
@RequestMapping("/erp/employee")
public class ErpEmployeeController extends BaseController {
    @Autowired
    private IErpEmployeeService erpEmployeeService;

    /**
     * 查询员工列表
     */
    @PreAuthorize("@ss.hasPermi('erp:employee:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpEmployee erpEmployee) {
        startPage();
        List<ErpEmployee> list = erpEmployeeService.selectErpEmployeeList(erpEmployee);
        return getDataTable(list);
    }

    /**
     * 导出员工列表
     */
    @PreAuthorize("@ss.hasPermi('erp:employee:export')")
    @Log(title = "员工", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpEmployee erpEmployee) {
        List<ErpEmployee> list = erpEmployeeService.selectErpEmployeeList(erpEmployee);
        ExcelUtil<ErpEmployee> util = new ExcelUtil<ErpEmployee>(ErpEmployee.class);
        util.exportExcel(response, list, "员工数据");
    }

    /**
     * 获取员工详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:employee:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(erpEmployeeService.selectErpEmployeeById(id));
    }

    /**
     * 新增员工
     */
    @PreAuthorize("@ss.hasPermi('erp:employee:add')")
    @Log(title = "员工", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpEmployee erpEmployee) {
        return toAjax(erpEmployeeService.insertErpEmployee(erpEmployee));
    }

    /**
     * 修改员工
     */
    @PreAuthorize("@ss.hasPermi('erp:employee:edit')")
    @Log(title = "员工", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpEmployee erpEmployee) {
        return toAjax(erpEmployeeService.updateErpEmployee(erpEmployee));
    }

    /**
     * 删除员工
     */
    @PreAuthorize("@ss.hasPermi('erp:employee:remove')")
    @Log(title = "员工", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(erpEmployeeService.deleteErpEmployeeByIds(ids));
    }
}
