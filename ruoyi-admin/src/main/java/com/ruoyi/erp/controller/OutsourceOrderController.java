package com.ruoyi.erp.controller;

import com.ruoyi.erp.domain.OutsourceOrder;
import com.ruoyi.erp.service.IOutsourceOrderService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 外协加工单Controller
 *
 * @author zhangmingjian
 */
@RestController
@RequestMapping("/erp/outsource")
public class OutsourceOrderController extends BaseController {

    @Autowired
    private IOutsourceOrderService outsourceOrderService;

    /**
     * 查询外协加工单列表
     */
    @PreAuthorize("@ss.hasPermi('erp:outsource:list')")
    @GetMapping("/list")
    public TableDataInfo list(OutsourceOrder outsourceOrder) {
        startPage();
        List<OutsourceOrder> list = outsourceOrderService.selectOutsourceOrderList(outsourceOrder);
        return getDataTable(list);
    }

    /**
     * 导出外协加工单列表
     */
    @PreAuthorize("@ss.hasPermi('erp:outsource:export')")
    @Log(title = "外协加工单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OutsourceOrder outsourceOrder) {
        List<OutsourceOrder> list = outsourceOrderService.selectOutsourceOrderList(outsourceOrder);
        ExcelUtil<OutsourceOrder> util = new ExcelUtil<OutsourceOrder>(OutsourceOrder.class);
        util.exportExcel(response, list, "外协加工单数据");
    }

    /**
     * 获取外协加工单详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:outsource:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(outsourceOrderService.selectOutsourceOrderById(id));
    }

    /**
     * 新增外协加工单
     */
    @PreAuthorize("@ss.hasPermi('erp:outsource:add')")
    @Log(title = "外协加工单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OutsourceOrder outsourceOrder) {
        return toAjax(outsourceOrderService.insertOutsourceOrder(outsourceOrder));
    }

    /**
     * 修改外协加工单
     */
    @PreAuthorize("@ss.hasPermi('erp:outsource:edit')")
    @Log(title = "外协加工单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OutsourceOrder outsourceOrder) {
        return toAjax(outsourceOrderService.updateOutsourceOrder(outsourceOrder));
    }

    /**
     * 删除外协加工单
     */
    @PreAuthorize("@ss.hasPermi('erp:outsource:remove')")
    @Log(title = "外协加工单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(outsourceOrderService.deleteOutsourceOrderByIds(ids));
    }
}
