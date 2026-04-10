package com.ruoyi.erp.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.erp.domain.ProducePlan;
import com.ruoyi.erp.service.IProducePlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 生产计划Controller
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
@RestController
@RequestMapping("/erp/plan")
public class ProducePlanController extends BaseController {
    @Autowired
    private IProducePlanService producePlanService;

    /**
     * 查询生产计划列表
     */
    @PreAuthorize("@ss.hasPermi('erp:plan:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProducePlan producePlan) {
        startPage();
        List<ProducePlan> list = producePlanService.selectProducePlanList(producePlan);
        return getDataTable(list);
    }

    /**
     * 导出生产计划列表
     */
    @PreAuthorize("@ss.hasPermi('erp:plan:export')")
    @Log(title = "生产计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProducePlan producePlan) {
        List<ProducePlan> list = producePlanService.selectProducePlanList(producePlan);
        ExcelUtil<ProducePlan> util = new ExcelUtil<ProducePlan>(ProducePlan.class);
        util.exportExcel(response, list, "生产计划数据");
    }

    /**
     * 获取生产计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:plan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(producePlanService.selectProducePlanById(id));
    }

    /**
     * 新增生产计划
     */
    @PreAuthorize("@ss.hasPermi('erp:plan:add')")
    @Log(title = "生产计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProducePlan producePlan) {
        return toAjax(producePlanService.insertProducePlan(producePlan));
    }

    /**
     * 修改生产计划
     */
    @PreAuthorize("@ss.hasPermi('erp:plan:edit')")
    @Log(title = "生产计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProducePlan producePlan) {
        return toAjax(producePlanService.updateProducePlan(producePlan));
    }

    /**
     * 删除生产计划
     */
    @PreAuthorize("@ss.hasPermi('erp:plan:remove')")
    @Log(title = "生产计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(producePlanService.deleteProducePlanByIds(ids));
    }
}
