package com.ruoyi.erp.controller;

import com.ruoyi.erp.domain.ProductSerial;
import com.ruoyi.erp.service.IProductSerialService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 单件流水号Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/erp/productSerial")
public class ProductSerialController extends BaseController {

    @Autowired
    private IProductSerialService productSerialService;

    /**
     * 查询单件流水号列表
     */
    @PreAuthorize("@ss.hasPermi('erp:productSerial:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProductSerial productSerial) {
        startPage();
        List<ProductSerial> list = productSerialService.selectProductSerialList(productSerial);
        return getDataTable(list);
    }

    /**
     * 根据工票ID查询单件流水号列表
     */
    @PreAuthorize("@ss.hasPermi('erp:productSerial:list')")
    @GetMapping("/listByJob/{jobId}")
    public AjaxResult listByJob(@PathVariable Long jobId) {
        List<ProductSerial> list = productSerialService.selectProductSerialByJobId(jobId);
        return AjaxResult.success(list);
    }

    /**
     * 根据流水号查询详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:productSerial:query')")
    @GetMapping(value = "/scan/{serialNo}")
    public AjaxResult scan(@PathVariable String serialNo) {
        ProductSerial productSerial = productSerialService.selectProductSerialBySerialNo(serialNo);
        if (productSerial != null) {
            return AjaxResult.success(productSerial);
        } else {
            return AjaxResult.error("未找到该产品");
        }
    }

    /**
     * 导出单件流水号列表
     */
    @PreAuthorize("@ss.hasPermi('erp:productSerial:export')")
    @Log(title = "单件流水号", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProductSerial productSerial) {
        List<ProductSerial> list = productSerialService.selectProductSerialList(productSerial);
        ExcelUtil<ProductSerial> util = new ExcelUtil<ProductSerial>(ProductSerial.class);
        util.exportExcel(response, list, "单件流水号数据");
    }

    /**
     * 获取单件流水号详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:productSerial:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(productSerialService.selectProductSerialById(id));
    }

    /**
     * 新增单件流水号
     */
    @PreAuthorize("@ss.hasPermi('erp:productSerial:add')")
    @Log(title = "单件流水号", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProductSerial productSerial) {
        return toAjax(productSerialService.insertProductSerial(productSerial));
    }

    /**
     * 批量新增单件流水号
     */
    @PreAuthorize("@ss.hasPermi('erp:productSerial:add')")
    @Log(title = "单件流水号", businessType = BusinessType.INSERT)
    @PostMapping("/batchAdd")
    public AjaxResult batchAdd(@RequestBody List<ProductSerial> serialList) {
        return toAjax(productSerialService.batchInsertProductSerial(serialList));
    }

    /**
     * 修改单件流水号
     */
    @PreAuthorize("@ss.hasPermi('erp:productSerial:edit')")
    @Log(title = "单件流水号", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProductSerial productSerial) {
        return toAjax(productSerialService.updateProductSerial(productSerial));
    }

    /**
     * 删除单件流水号
     */
    @PreAuthorize("@ss.hasPermi('erp:productSerial:remove')")
    @Log(title = "单件流水号", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(productSerialService.deleteProductSerialByIds(ids));
    }
}
