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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.erp.domain.StockIn;
import com.ruoyi.erp.service.IStockInService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 入库单Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping("/erp/stockIn")
public class StockInController extends BaseController {
    @Autowired
    private IStockInService stockInService;

    /**
     * 查询入库单列表
     */
    @PreAuthorize("@ss.hasPermi('erp:stockin:list')")
    @GetMapping("/list")
    public TableDataInfo list(StockIn stockIn) {
        startPage();
        List<StockIn> list = stockInService.selectStockInList(stockIn);
        return getDataTable(list);
    }

    /**
     * 导出入库单列表
     */
    @PreAuthorize("@ss.hasPermi('erp:stockin:export')")
    @Log(title = "入库单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StockIn stockIn) {
        List<StockIn> list = stockInService.selectStockInList(stockIn);
        ExcelUtil<StockIn> util = new ExcelUtil<StockIn>(StockIn.class);
        util.exportExcel(response, list, "入库单数据");
    }

    /**
     * 获取入库单详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:stockin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(stockInService.selectStockInById(id));
    }

    /**
     * 新增入库单
     */
    @PreAuthorize("@ss.hasPermi('erp:stockin:add')")
    @Log(title = "入库单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody StockIn stockIn) {
        return toAjax(stockInService.insertStockIn(stockIn));
    }

    /**
     * 修改入库单
     */
    @PreAuthorize("@ss.hasPermi('erp:stockin:edit')")
    @Log(title = "入库单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody StockIn stockIn) {
        return toAjax(stockInService.updateStockIn(stockIn));
    }

    /**
     * 删除入库单
     */
    @PreAuthorize("@ss.hasPermi('erp:stockin:remove')")
    @Log(title = "入库单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(stockInService.deleteStockInByIds(ids));
    }

    /**
     * 导入入库单 Excel
     */
    @PreAuthorize("@ss.hasPermi('erp:stockin:import')")
    @Log(title = "入库单导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        if (file == null || file.isEmpty()) {
            return error("上传文件不能为空");
        }
        ExcelUtil<StockIn> util = new ExcelUtil<>(StockIn.class);
        List<StockIn> list = util.importExcel(file.getInputStream());
        String message = stockInService.importStockIn(list, updateSupport);
        return success(message);
    }

    /**
     * 下载入库单导入模板
     */
    @PreAuthorize("@ss.hasPermi('erp:stockin:import')")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<StockIn> util = new ExcelUtil<>(StockIn.class);
        util.importTemplateExcel(response, "入库单数据");
    }
}
