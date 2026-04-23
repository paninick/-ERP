package com.ruoyi.system.mapper;

import java.util.List;
import java.util.Map;
import com.ruoyi.system.domain.QcInspection;

/**
 * 质检单 Mapper 接口
 *
 * @author ruoyi
 */
public interface QcInspectionMapper
{
    /**
     * 查询质检单列表（含工厂权限过滤）
     */
    List<QcInspection> selectQcList(QcInspection qcInspection);

    /**
     * 按 ID 查询
     */
    QcInspection selectQcById(Long id);

    /**
     * 按批次号/条码查询
     */
    QcInspection selectQcByBatchNo(String batchNo);

    /**
     * 新增质检单
     */
    int insertQcInspection(QcInspection qcInspection);

    /**
     * 更新质检单（打回/状态变更）
     */
    int updateQcInspection(QcInspection qcInspection);

    /**
     * 统计 KPI：待检/今日已检/合格率/Critical 数
     * 返回 Map: {pendingCount, todayChecked, passRate, criticalCount}
     */
    Map<String, Object> selectQcStats(Long factoryId);

    /**
     * TOP5 不合格原因
     * 返回 [{name, count}]
     */
    List<Map<String, Object>> selectDefectReasonTop5(Long factoryId);
}
