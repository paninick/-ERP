package com.ruoyi.erp.controller;

import com.ruoyi.erp.domain.ProduceMaterialConsume;
import com.ruoyi.erp.service.IProduceMaterialConsumeService;
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

import java.math.BigDecimal;
import java.util.List;

/**
 * 生产物料消耗记录Controller
 *
 * @author zhangmingjian
 */
@RestController
@RequestMapping("/erp/materialconsume")
public class ProduceMaterialConsumeController extends BaseController {

    @Autowired
    private IProduceMaterialConsumeService produceMaterialConsumeService;

    /**
     * 查询生产物料消耗记录列表
     */
    @PreAuthorize("@ss.hasPermi('erp:materialconsume:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProduceMaterialConsume produceMaterialConsume) {
        startPage();
        List<ProduceMaterialConsume> list = produceMaterialConsumeService.selectProduceMaterialConsumeList(produceMaterialConsume);
        return getDataTable(list);
    }

    /**
     * 根据生产计划查询消耗记录
     */
    @PreAuthorize("@ss.hasPermi('erp:materialconsume:list')")
    @GetMapping("/listByPlan/{producePlanId}")
    public AjaxResult listByPlan(@PathVariable Long producePlanId) {
        List<ProduceMaterialConsume> list = produceMaterialConsumeService.selectByProducePlanId(producePlanId);
        BigDecimal totalLoss = produceMaterialConsumeService.sumActualLossByProducePlan(producePlanId);
        AjaxResult result = AjaxResult.success(list);
        result.put("totalLoss", totalLoss);
        return result;
    }

    /**
     * 计算限额
     */
    @PreAuthorize("@ss.hasPermi('erp:materialconsume:query')")
    @GetMapping("/calculateLimit")
    public AjaxResult calculateLimit(@RequestParam BigDecimal bomQty, @RequestParam BigDecimal standardLossRate) {
        BigDecimal limit = produceMaterialConsumeService.calculateLimitQty(bomQty, standardLossRate);
        AjaxResult result = AjaxResult.success();
        result.put("limitQty", limit);
        return result;
    }

    /**
     * 导出生产物料消耗记录列表
     */
    @PreAuthorize("@ss.hasPermi('erp:materialconsume:export')")
    @Log(title = "物料消耗记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProduceMaterialConsume produceMaterialConsume) {
        List<ProduceMaterialConsume> list = produceMaterialConsumeService.selectProduceMaterialConsumeList(produceMaterialConsume);
        ExcelUtil<ProduceMaterialConsume> util = new ExcelUtil<ProduceMaterialConsume>(ProduceMaterialConsume.class);
        util.exportExcel(response, list, "物料消耗记录数据");
    }

    /**
     * 获取生产物料消耗记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:materialconsume:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(produceMaterialConsumeService.selectProduceMaterialConsumeById(id));
    }

    /**
     * 新增生产物料消耗记录
     */
    @PreAuthorize("@ss.hasPermi('erp:materialconsume:add')")
    @Log(title = "物料消耗记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProduceMaterialConsume produceMaterialConsume) {
        return toAjax(produceMaterialConsumeService.insertProduceMaterialConsume(produceMaterialConsume));
    }

    /**
     * 修改生产物料消耗记录
     */
    @PreAuthorize("@ss.hasPermi('erp:materialconsume:edit')")
    @Log(title = "物料消耗记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProduceMaterialConsume produceMaterialConsume) {
        return toAjax(produceMaterialConsumeService.updateProduceMaterialConsume(produceMaterialConsume));
    }

    /**
     * 删除生产物料消耗记录
     */
    @PreAuthorize("@ss.hasPermi('erp:materialconsume:remove')")
    @Log(title = "物料消耗记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(produceMaterialConsumeService.deleteProduceMaterialConsumeByIds(ids));
    }

    /**
     * 损耗统计汇总（统计卡片数据）
     */
    @PreAuthorize("@ss.hasPermi('erp:materialconsume:list')")
    @GetMapping("/lossStats")
    public AjaxResult lossStats() {
        return AjaxResult.success(produceMaterialConsumeService.selectLossStats());
    }

    /**
     * 审批超领申请
     */
    @PreAuthorize("@ss.hasPermi('erp:materialconsume:edit')")
    @Log(title = "物料消耗记录", businessType = BusinessType.UPDATE)
    @PutMapping("/approve/{id}")
    public AjaxResult approve(@PathVariable Long id,
                              @RequestParam boolean approved,
                              @RequestParam(required = false, defaultValue = "") String remark) {
        return toAjax(produceMaterialConsumeService.approveLoss(id, approved, remark));
    }

    /**
     * 按出库单同步生成生产用料基线
     */
    @PreAuthorize("@ss.hasPermi('erp:materialconsume:edit')")
    @Log(title = "物料消耗记录", businessType = BusinessType.UPDATE)
    @PostMapping("/syncByStockOut/{stockOutId}")
    public AjaxResult syncByStockOut(@PathVariable Long stockOutId) {
        return AjaxResult.success(produceMaterialConsumeService.syncByStockOut(stockOutId));
    }

    /**
     * 绑定用料记录到工序/报工事件
     */
    @PreAuthorize("@ss.hasPermi('erp:materialconsume:edit')")
    @Log(title = "物料消耗记录", businessType = BusinessType.UPDATE)
    @PutMapping("/bind/{consumeId}")
    public AjaxResult bindToJobProcess(@PathVariable Long consumeId,
                                       @RequestParam Long jobProcessId,
                                       @RequestParam(required = false) Long reportLogId) {
        return AjaxResult.success(produceMaterialConsumeService.bindToJobProcess(consumeId, jobProcessId, reportLogId));
    }
}
