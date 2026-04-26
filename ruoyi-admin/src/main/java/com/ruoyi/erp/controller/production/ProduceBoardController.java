package com.ruoyi.erp.controller.production;

import com.ruoyi.erp.domain.vo.ProduceBoardStatsVO;
import com.ruoyi.erp.domain.vo.ProcessWipStatsVO;
import com.ruoyi.erp.domain.vo.EmployeeRankVO;
import com.ruoyi.erp.service.IProduceBoardService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 生产看板Controller
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
@RestController
@RequestMapping("/erp/produceboard")
public class ProduceBoardController extends BaseController {

    @Autowired
    private IProduceBoardService produceBoardService;

    /**
     * 获取生产看板总体统计数据
     */
    @PreAuthorize("@ss.hasPermi('erp:produceboard:query')")
    @GetMapping("/stats")
    public AjaxResult getBoardStats() {
        ProduceBoardStatsVO stats = produceBoardService.calculateBoardStats();
        return AjaxResult.success(stats);
    }

    /**
     * 获取各工序WIP统计
     */
    @PreAuthorize("@ss.hasPermi('erp:produceboard:query')")
    @GetMapping("/wipStats")
    public AjaxResult getWipStats() {
        List<ProcessWipStatsVO> list = produceBoardService.calculateProcessWipStats();
        return AjaxResult.success(list);
    }

    /**
     * 获取员工计件产量排名
     */
    @PreAuthorize("@ss.hasPermi('erp:produceboard:query')")
    @GetMapping("/employeeRank")
    public AjaxResult getEmployeeRank() {
        List<EmployeeRankVO> list = produceBoardService.getEmployeeProductivityRank();
        return AjaxResult.success(list);
    }

    /**
     * 获取瓶颈工序预警
     */
    @PreAuthorize("@ss.hasPermi('erp:produceboard:query')")
    @GetMapping("/bottleneckWarnings")
    public AjaxResult getBottleneckWarnings() {
        List<ProcessWipStatsVO> list = produceBoardService.getBottleneckWarnings();
        return AjaxResult.success(list);
    }
}
