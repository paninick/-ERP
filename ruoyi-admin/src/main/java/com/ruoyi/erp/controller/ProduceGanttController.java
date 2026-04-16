package com.ruoyi.erp.controller;

import com.ruoyi.erp.domain.ProducePlan;
import com.ruoyi.erp.domain.vo.GanttTaskVO;
import com.ruoyi.erp.service.IProducePlanService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
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
}
