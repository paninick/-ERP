package com.ruoyi.erp.workcenter.controller;

import java.util.List;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.erp.workcenter.domain.ErpWorkCenter;
import com.ruoyi.erp.workcenter.service.IErpWorkCenterService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erp/workCenter")
public class ErpWorkCenterController extends BaseController {

    @Autowired
    private IErpWorkCenterService workCenterService;

    @PreAuthorize("@ss.hasPermi('erp:workCenter:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpWorkCenter workCenter) {
        startPage();
        List<ErpWorkCenter> list = workCenterService.selectErpWorkCenterList(workCenter);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('erp:workCenter:export')")
    @Log(title = "工作中心", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpWorkCenter workCenter) {
        List<ErpWorkCenter> list = workCenterService.selectErpWorkCenterList(workCenter);
        ExcelUtil<ErpWorkCenter> util = new ExcelUtil<>(ErpWorkCenter.class);
        util.exportExcel(response, list, "workCenter");
    }

    @PreAuthorize("@ss.hasPermi('erp:workCenter:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return success(workCenterService.selectErpWorkCenterById(id));
    }

    @PreAuthorize("@ss.hasPermi('erp:workCenter:add')")
    @Log(title = "工作中心", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody @Validated ErpWorkCenter workCenter) {
        return toAjax(workCenterService.insertErpWorkCenter(workCenter));
    }

    @PreAuthorize("@ss.hasPermi('erp:workCenter:edit')")
    @Log(title = "工作中心", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody @Validated ErpWorkCenter workCenter) {
        return toAjax(workCenterService.updateErpWorkCenter(workCenter));
    }

    @PreAuthorize("@ss.hasPermi('erp:workCenter:remove')")
    @Log(title = "工作中心", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(workCenterService.deleteErpWorkCenterByIds(ids));
    }
}
