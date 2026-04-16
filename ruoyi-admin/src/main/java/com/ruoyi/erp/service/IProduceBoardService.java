package com.ruoyi.erp.service;

import com.ruoyi.erp.domain.vo.ProduceBoardStatsVO;
import com.ruoyi.erp.domain.vo.ProcessWipStatsVO;
import com.ruoyi.erp.domain.vo.EmployeeRankVO;

import java.util.List;

/**
 * 生产看板Service接口
 *
 * @author zhangmingjian
 */
public interface IProduceBoardService {

    /**
     * 计算生产看板总体统计
     *
     * @return 统计数据
     */
    ProduceBoardStatsVO calculateBoardStats();

    /**
     * 计算各工序WIP统计
     *
     * @return 工序统计列表
     */
    List<ProcessWipStatsVO> calculateProcessWipStats();

    /**
     * 获取员工产量排名（本月）
     *
     * @return 排名列表
     */
    List<EmployeeRankVO> getEmployeeProductivityRank();

    /**
     * 获取瓶颈工序预警
     *
     * @return 瓶颈列表
     */
    List<ProcessWipStatsVO> getBottleneckWarnings();
}
