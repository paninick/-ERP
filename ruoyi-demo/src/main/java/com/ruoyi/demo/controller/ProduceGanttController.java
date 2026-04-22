package com.ruoyi.demo.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.demo.domain.DemoSchedule;
import com.ruoyi.demo.service.IDemoScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/erp/producegantt")
public class ProduceGanttController {

    @Autowired
    private IDemoScheduleService scheduleService;

    @GetMapping("/list")
    public AjaxResult list(@RequestParam(required = false) String startDate,
                           @RequestParam(required = false) String endDate,
                           @RequestParam(required = false) String process) {
        List<DemoSchedule> list = scheduleService.selectGanttList(startDate, endDate, process);
        return AjaxResult.success(list);
    }

    @PostMapping("/detectConflicts")
    public AjaxResult detectConflicts() {
        int count = scheduleService.batchDetectConflicts();
        return AjaxResult.success("检测完成，发现冲突 " + count + " 条", count);
    }

    @PutMapping("/reschedule/{id}")
    public AjaxResult reschedule(@PathVariable Long id,
                                 @RequestParam(required = false) String newStartDate,
                                 @RequestParam(required = false) String newDueDate) {
        boolean ok = scheduleService.reschedule(id, newStartDate, newDueDate);
        return ok ? AjaxResult.success() : AjaxResult.error("排程不存在");
    }
}
