package com.ruoyi.erp.cost.controller;

import java.util.List;
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
import com.ruoyi.erp.cost.domain.ErpCostSummary;
import com.ruoyi.erp.cost.service.IErpCostSummaryService;

@RestController
@RequestMapping("/erp/costSummary")
public class ErpCostSummaryController extends BaseController {

    @Autowired
    private IErpCostSummaryService costSummaryService;

    @PreAuthorize("@ss.hasPermi('erp:cost:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpCostSummary costSummary) {
        startPage();
        List<ErpCostSummary> list = costSummaryService.selectList(costSummary);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('erp:cost:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return success(costSummaryService.selectById(id));
    }

    @PreAuthorize("@ss.hasPermi('erp:cost:add')")
    @Log(title = "成本汇总", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpCostSummary costSummary) {
        return toAjax(costSummaryService.insert(costSummary));
    }

    @PreAuthorize("@ss.hasPermi('erp:cost:edit')")
    @Log(title = "成本汇总", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpCostSummary costSummary) {
        return toAjax(costSummaryService.update(costSummary));
    }

    @PreAuthorize("@ss.hasPermi('erp:cost:remove')")
    @Log(title = "成本汇总", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(costSummaryService.deleteByIds(ids));
    }
}
