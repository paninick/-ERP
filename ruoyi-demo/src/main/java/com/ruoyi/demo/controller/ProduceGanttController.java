package com.ruoyi.demo.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.demo.domain.DemoSchedule;
import com.ruoyi.demo.service.IDemoScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// NOTE: 此控制器暂留在 ruoyi-demo 模块，但 /erp/producegantt 是正式生产路径，非示例代码
@RestController
@RequestMapping("/erp/producegantt")
public class ProduceGanttController {

    @Autowired
    private IDemoScheduleService scheduleService;

    @PreAuthorize("@ss.hasPermi('erp:producegantt:list')")
    @GetMapping("/list")
    public AjaxResult list(@RequestParam(required = false) String startDate,
                           @RequestParam(required = false) String endDate,
                           @RequestParam(required = false) String process) {
        List<DemoSchedule> list = scheduleService.selectGanttList(startDate, endDate, process);
        return AjaxResult.success(list);
    }

    @PreAuthorize("@ss.hasPermi('erp:producegantt:detect')")
    @PostMapping("/detectConflicts")
    public AjaxResult detectConflicts() {
        int count = scheduleService.batchDetectConflicts();
        return AjaxResult.success("检测完成，发现冲突 " + count + " 条", count);
    }

    @PreAuthorize("@ss.hasPermi('erp:producegantt:edit')")
    @PutMapping("/reschedule/{id}")
    public AjaxResult reschedule(@PathVariable Long id,
                                 @RequestParam(required = false) String newStartDate,
                                 @RequestParam(required = false) String newDueDate) {
        boolean ok = scheduleService.reschedule(id, newStartDate, newDueDate);
        return ok ? AjaxResult.success() : AjaxResult.error("排程不存在");
    }
}
