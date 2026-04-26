package com.ruoyi.erp.controller.production;

import com.ruoyi.erp.domain.vo.GanttTaskVO;
import com.ruoyi.erp.service.IProducePlanService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 甘特图排产Controller
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
@RestController
@RequestMapping("/erp/producegantt")
public class ProduceGanttController extends BaseController {

    @Autowired
    private IProducePlanService producePlanService;

    /**
     * 获取甘特图数据
     */
    @PreAuthorize("@ss.hasPermi('erp:producegantt:list')")
    @GetMapping("/list")
    public AjaxResult getGanttData() {
        List<GanttTaskVO> tasks = producePlanService.selectGanttTasks();
        return AjaxResult.success(tasks);
    }

    /**
     * 更新生产计划日期（拖拽调整后）
     */
    @PreAuthorize("@ss.hasPermi('erp:producegantt:edit')")
    @PutMapping("/updateDate/{id}")
    public AjaxResult updateDate(@PathVariable Long id,
                                  @RequestParam Date startDate,
                                  @RequestParam Date dueDate) {
        return toAjax(producePlanService.updatePlanDates(id, startDate, dueDate));
    }

    /**
     * 重新排期（前端 reschedule 接口）
     */
    @PreAuthorize("@ss.hasPermi('erp:producegantt:edit')")
    @PutMapping("/reschedule/{id}")
    public AjaxResult reschedule(@PathVariable Long id,
                                 @RequestParam(required = false) String newStartDate,
                                 @RequestParam(required = false) String newDueDate) {
        boolean ok = producePlanService.reschedule(id, newStartDate, newDueDate);
        return ok ? AjaxResult.success() : AjaxResult.error("排程不存在");
    }

    /**
     * 批量检测冲突
     */
    @PreAuthorize("@ss.hasPermi('erp:producegantt:detect')")
    @PostMapping("/detectConflicts")
    public AjaxResult detectConflicts() {
        int count = producePlanService.batchDetectConflicts();
        return AjaxResult.success("检测完成，发现冲突 " + count + " 条", count);
    }
}
