package com.ruoyi.erp.controller.masterdata;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.erp.domain.ProcessPrice;
import com.ruoyi.erp.service.IProcessPriceService;

/**
 * 工序工价Controller
 */
@RestController
@RequestMapping("/erp/processPrice")
public class ProcessPriceController extends BaseController {
    @Autowired
    private IProcessPriceService processPriceService;

    @PreAuthorize("@ss.hasPermi('erp:processPrice:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProcessPrice processPrice) {
        startPage();
        List<ProcessPrice> list = processPriceService.selectProcessPriceList(processPrice);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('erp:processPrice:export')")
    @Log(title = "工序工价", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProcessPrice processPrice) {
        List<ProcessPrice> list = processPriceService.selectProcessPriceList(processPrice);
        ExcelUtil<ProcessPrice> util = new ExcelUtil<ProcessPrice>(ProcessPrice.class);
        util.exportExcel(response, list, "工序工价数据");
    }

    @PreAuthorize("@ss.hasPermi('erp:processPrice:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(processPriceService.selectProcessPriceById(id));
    }

    @PreAuthorize("@ss.hasPermi('erp:processPrice:query')")
    @GetMapping("/current/{processId}/{employeeId}")
    public AjaxResult getCurrentPrice(@PathVariable Long processId, @PathVariable Long employeeId) {
        return success(processPriceService.selectCurrentPrice(processId, employeeId));
    }

    @PreAuthorize("@ss.hasPermi('erp:processPrice:add')")
    @Log(title = "工序工价", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProcessPrice processPrice) {
        return toAjax(processPriceService.insertProcessPrice(processPrice));
    }

    @PreAuthorize("@ss.hasPermi('erp:processPrice:edit')")
    @Log(title = "工序工价", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProcessPrice processPrice) {
        return toAjax(processPriceService.updateProcessPrice(processPrice));
    }

    @PreAuthorize("@ss.hasPermi('erp:processPrice:remove')")
    @Log(title = "工序工价", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(processPriceService.deleteProcessPriceByIds(ids));
    }
}
