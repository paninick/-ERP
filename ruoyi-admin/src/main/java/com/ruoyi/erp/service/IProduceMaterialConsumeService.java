package com.ruoyi.erp.service;

import com.ruoyi.erp.domain.ProduceMaterialConsume;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Production material consume service.
 */
public interface IProduceMaterialConsumeService {
    ProduceMaterialConsume selectProduceMaterialConsumeById(Long id);

    List<ProduceMaterialConsume> selectProduceMaterialConsumeList(ProduceMaterialConsume produceMaterialConsume);

    int insertProduceMaterialConsume(ProduceMaterialConsume produceMaterialConsume);

    int updateProduceMaterialConsume(ProduceMaterialConsume produceMaterialConsume);

    int deleteProduceMaterialConsumeByIds(Long[] ids);

    int deleteProduceMaterialConsumeById(Long id);

    BigDecimal sumActualLossByProducePlan(Long producePlanId);

    List<ProduceMaterialConsume> selectByProducePlanId(Long producePlanId);

    BigDecimal calculateLimitQty(BigDecimal bomQty, BigDecimal standardLossRate);

    BigDecimal calculateActualLoss(BigDecimal actualQty, BigDecimal bomQty);

    Map<String, Object> selectLossStats();

    int approveLoss(Long id, boolean approved, String remark);

    /**
     * 按出库单同步生成生产用料基线
     * @param stockOutId 出库单ID
     * @return Map 包含 itemCount/insertedCount/updatedCount/producePlanId/jobId
     */
    Map<String, Object> syncByStockOut(Long stockOutId);

    /**
     * 绑定用料记录到工序/报工事件
     * @param consumeId 用料记录ID
     * @param jobProcessId 工序快照ID
     * @param reportLogId 报工记录ID（可选）
     * @return Map 包含绑定后的 consumeId/jobId/jobProcessId/processId/reportLogId
     */
    Map<String, Object> bindToJobProcess(Long consumeId, Long jobProcessId, Long reportLogId);

    boolean hasLockedAbnormalByStockOut(Long stockOutId);
}
