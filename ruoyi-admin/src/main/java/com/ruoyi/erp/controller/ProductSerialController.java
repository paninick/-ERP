package com.ruoyi.erp.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.erp.domain.ProductSerial;
import com.ruoyi.erp.mapper.ProductSerialMapper;
import com.ruoyi.erp.service.IProductSerialService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Product serial controller.
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/erp/productSerial")
public class ProductSerialController extends BaseController {

    @Autowired
    private IProductSerialService productSerialService;

    @Autowired
    private ProductSerialMapper productSerialMapper;

    /**
     * Query product serial list.
     */
    @PreAuthorize("@ss.hasPermi('erp:productSerial:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProductSerial productSerial) {
        startPage();
        List<ProductSerial> list = productSerialService.selectProductSerialList(productSerial);
        return getDataTable(list);
    }

    /**
     * Query product serial list by job id.
     */
    @PreAuthorize("@ss.hasPermi('erp:productSerial:list')")
    @GetMapping("/listByJob/{jobId}")
    public AjaxResult listByJob(@PathVariable Long jobId) {
        List<ProductSerial> list = productSerialService.selectProductSerialByJobId(jobId);
        return AjaxResult.success(list);
    }

    /**
     * Query product serial detail by serial number.
     */
    @PreAuthorize("@ss.hasPermi('erp:productSerial:query')")
    @GetMapping(value = "/scan/{serialNo}")
    public AjaxResult scan(@PathVariable String serialNo) {
        ProductSerial productSerial = productSerialService.selectProductSerialBySerialNo(serialNo);
        if (productSerial != null) {
            return AjaxResult.success(productSerial);
        }
        return AjaxResult.error("Product serial not found");
    }

    /**
     * Export product serial list.
     */
    @PreAuthorize("@ss.hasPermi('erp:productSerial:export')")
    @Log(title = "Product Serial", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProductSerial productSerial) {
        List<ProductSerial> list = productSerialService.selectProductSerialList(productSerial);
        ExcelUtil<ProductSerial> util = new ExcelUtil<>(ProductSerial.class);
        util.exportExcel(response, list, "product_serial");
    }

    /**
     * Query product serial by id.
     */
    @PreAuthorize("@ss.hasPermi('erp:productSerial:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(productSerialService.selectProductSerialById(id));
    }

    /**
     * Create one product serial.
     */
    @PreAuthorize("@ss.hasPermi('erp:productSerial:add')")
    @Log(title = "Product Serial", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProductSerial productSerial) {
        return toAjax(productSerialService.insertProductSerial(productSerial));
    }

    /**
     * Batch create product serials.
     */
    @PreAuthorize("@ss.hasPermi('erp:productSerial:add')")
    @Log(title = "Product Serial", businessType = BusinessType.INSERT)
    @PostMapping("/batchAdd")
    public AjaxResult batchAdd(@RequestBody List<ProductSerial> serialList) {
        return toAjax(productSerialService.batchInsertProductSerial(serialList));
    }

    /**
     * Update one product serial.
     */
    @PreAuthorize("@ss.hasPermi('erp:productSerial:edit')")
    @Log(title = "Product Serial", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProductSerial productSerial) {
        return toAjax(productSerialService.updateProductSerial(productSerial));
    }

    /**
     * Delete product serials by ids.
     */
    @PreAuthorize("@ss.hasPermi('erp:productSerial:remove')")
    @Log(title = "Product Serial", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(productSerialService.deleteProductSerialByIds(ids));
    }

    /**
     * Query full trace data by sales order id.
     *
     * @param salesOrderId sales order id
     */
    @PreAuthorize("@ss.hasPermi('erp:productSerial:query')")
    @GetMapping("/trace/byOrder/{salesOrderId}")
    public AjaxResult traceByOrder(@PathVariable Long salesOrderId) {
        List<Map<String, Object>> trace = productSerialMapper.selectTraceByOrderId(salesOrderId);
        return success(trace);
    }

    /**
     * Query style progress summary by style code.
     *
     * @param styleCode style code, for example KN-26-SP-001
     */
    @PreAuthorize("@ss.hasPermi('erp:productSerial:query')")
    @GetMapping("/trace/styleProgress")
    public AjaxResult styleProgress(@RequestParam(required = false) String styleCode) {
        if (styleCode == null || styleCode.isBlank()) {
            return AjaxResult.error("styleCode is required");
        }
        Map<String, Object> progress = productSerialMapper.selectStyleProgress(styleCode.trim());
        return success(progress);
    }
}
