package com.ruoyi.erp.controller;

import java.util.List;
import java.util.Map;
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
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.erp.domain.Supplier;
import com.ruoyi.erp.service.ISupplierService;
import com.ruoyi.erp.mapper.SupplierMapper;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 供应商Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping("/erp/supplier")
public class SupplierController extends BaseController {
    @Autowired
    private ISupplierService supplierService;

    @Autowired
    private SupplierMapper supplierMapper;

    /**
     * 查询供应商列表
     */
    @PreAuthorize("@ss.hasPermi('erp:supplier:list')")
    @GetMapping("/list")
    public TableDataInfo list(Supplier supplier) {
        startPage();
        List<Supplier> list = supplierService.selectSupplierList(supplier);
        return getDataTable(list);
    }

    /**
     * 导出供应商列表
     */
    @PreAuthorize("@ss.hasPermi('erp:supplier:export')")
    @Log(title = "供应商", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Supplier supplier) {
        List<Supplier> list = supplierService.selectSupplierList(supplier);
        ExcelUtil<Supplier> util = new ExcelUtil<Supplier>(Supplier.class);
        util.exportExcel(response, list, "供应商数据");
    }

    /**
     * 获取供应商详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:supplier:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(supplierService.selectSupplierById(id));
    }

    /**
     * 新增供应商
     */
    @PreAuthorize("@ss.hasPermi('erp:supplier:add')")
    @Log(title = "供应商", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Supplier supplier) {
        return toAjax(supplierService.insertSupplier(supplier));
    }

    /**
     * 修改供应商
     */
    @PreAuthorize("@ss.hasPermi('erp:supplier:edit')")
    @Log(title = "供应商", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Supplier supplier) {
        return toAjax(supplierService.updateSupplier(supplier));
    }

    /**
     * 删除供应商
     */
    @PreAuthorize("@ss.hasPermi('erp:supplier:remove')")
    @Log(title = "供应商", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(supplierService.deleteSupplierByIds(ids));
    }

    /**
     * 下载导入模板
     */
    @PreAuthorize("@ss.hasPermi('erp:supplier:import')")
    @GetMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Supplier> util = new ExcelUtil<Supplier>(Supplier.class);
        util.importTemplateExcel(response, "供应商数据");
    }

    /**
     * 导入供应商数据
     */
    @PreAuthorize("@ss.hasPermi('erp:supplier:import')")
    @Log(title = "供应商导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<Supplier> util = new ExcelUtil<Supplier>(Supplier.class);
        List<Supplier> list = util.importExcel(file.getInputStream());
        String message = supplierService.importSupplier(list, updateSupport);
        return success(message);
    }

    /**
     * 触发指定供应商评级计算
     *
     * @param id 供应商ID（传 0 则批量评级所有供应商）
     */
    @PreAuthorize("@ss.hasPermi('erp:supplier:edit')")
    @Log(title = "供应商评级", businessType = BusinessType.UPDATE)
    @PutMapping("/rate/{id}")
    public AjaxResult rateSupplier(@PathVariable Long id) {
        if (id == 0) {
            // 批量：查所有供应商 ID 逐一评级
            List<Supplier> all = supplierService.selectSupplierList(new Supplier());
            for (Supplier s : all) {
                supplierMapper.rateSupplier(s.getId());
            }
            return success("已批量评级 " + all.size() + " 家供应商");
        }
        supplierMapper.rateSupplier(id);
        return success("评级完成");
    }

    /**
     * 供应商评级概览（按综合评级降序）
     */
    @PreAuthorize("@ss.hasPermi('erp:supplier:list')")
    @GetMapping("/ratingView")
    public AjaxResult ratingView() {
        return success(supplierMapper.selectSupplierRatingView());
    }
}
