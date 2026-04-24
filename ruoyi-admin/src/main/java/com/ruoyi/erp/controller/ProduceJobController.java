package com.ruoyi.erp.controller;

import com.ruoyi.erp.domain.ProduceJob;
import com.ruoyi.erp.service.IProduceJobService;
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
 * 生产工票Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/erp/produceJob")
public class ProduceJobController extends BaseController {

    @Autowired
    private IProduceJobService produceJobService;

    /**
     * 查询生产工票列表
     */
    @PreAuthorize("@ss.hasPermi('erp:produceJob:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProduceJob produceJob) {
        startPage();
        List<ProduceJob> list = produceJobService.selectProduceJobList(produceJob);
        return getDataTable(list);
    }

    /**
     * 根据生产计划ID查询工票列表
     */
    @PreAuthorize("@ss.hasPermi('erp:produceJob:list')")
    @GetMapping("/listByProducePlan/{producePlanId}")
    public AjaxResult listByProducePlan(@PathVariable Long producePlanId) {
        List<ProduceJob> list = produceJobService.selectProduceJobByProducePlanId(producePlanId);
        return AjaxResult.success(list);
    }

    /**
     * 导出生产工票列表
     */
    @PreAuthorize("@ss.hasPermi('erp:produceJob:export')")
    @Log(title = "生产工票", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProduceJob produceJob) {
        List<ProduceJob> list = produceJobService.selectProduceJobList(produceJob);
        ExcelUtil<ProduceJob> util = new ExcelUtil<ProduceJob>(ProduceJob.class);
        util.exportExcel(response, list, "生产工票数据");
    }

    /**
     * 获取生产工票详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:produceJob:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(produceJobService.selectProduceJobById(id));
    }

    /**
     * 获取生产计划统计
     */
    @PreAuthorize("@ss.hasPermi('erp:produceJob:query')")
    @GetMapping(value = "/statistics/{producePlanId}")
    public AjaxResult getStatistics(@PathVariable Long producePlanId) {
        Integer planQty = produceJobService.sumPlanQtyByProducePlanId(producePlanId);
        Integer actualQty = produceJobService.sumActualQtyByProducePlanId(producePlanId);
        AjaxResult result = AjaxResult.success();
        result.put("planQty", planQty);
        result.put("actualQty", actualQty);
        return result;
    }

    /**
     * 新增生产工票
     */
    @PreAuthorize("@ss.hasPermi('erp:produceJob:add')")
    @Log(title = "生产工票", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProduceJob produceJob) {
        return toAjax(produceJobService.insertProduceJob(produceJob));
    }

    /**
     * 批量新增生产工票
     */
    @PreAuthorize("@ss.hasPermi('erp:produceJob:add')")
    @Log(title = "生产工票", businessType = BusinessType.INSERT)
    @PostMapping("/batchAdd")
    public AjaxResult batchAdd(@RequestBody List<ProduceJob> produceJobList) {
        return toAjax(produceJobService.batchInsertProduceJob(produceJobList));
    }

    /**
     * 修改生产工票
     */
    @PreAuthorize("@ss.hasPermi('erp:produceJob:edit')")
    @Log(title = "生产工票", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProduceJob produceJob) {
        return toAjax(produceJobService.updateProduceJob(produceJob));
    }

    /**
     * 删除生产工票
     */
    @PreAuthorize("@ss.hasPermi('erp:produceJob:remove')")
    @Log(title = "生产工票", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(produceJobService.deleteProduceJobByIds(ids));
    }

    /**
     * 初始化工单工序队列（根据工艺路线自动生成工序流转）
     */
    @PreAuthorize("@ss.hasPermi('erp:produceJob:init')")
    @Log(title = "生产工票", businessType = BusinessType.INSERT)
    @PostMapping("/initProcesses/{jobId}/{routeId}")
    public AjaxResult initProcesses(@PathVariable Long jobId, @PathVariable Long routeId) {
        produceJobService.initJobProcesses(jobId, routeId);
        return AjaxResult.success("工序初始化成功");
    }
}
