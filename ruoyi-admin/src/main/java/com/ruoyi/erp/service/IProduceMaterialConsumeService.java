package com.ruoyi.erp.service;

import com.ruoyi.erp.domain.ProduceMaterialConsume;

import java.math.BigDecimal;
import java.util.List;

/**
 * 生产物料消耗记录Service接口
 *
 * @author zhangmingjian
 */
public interface IProduceMaterialConsumeService {
    /**
     * 查询生产物料消耗记录
     *
     * @param id 生产物料消耗记录ID
     * @return 生产物料消耗记录
     */
    public ProduceMaterialConsume selectProduceMaterialConsumeById(Long id);

    /**
     * 查询生产物料消耗记录列表
     *
     * @param produceMaterialConsume 生产物料消耗记录
     * @return 生产物料消耗记录集合
     */
    public List<ProduceMaterialConsume> selectProduceMaterialConsumeList(ProduceMaterialConsume produceMaterialConsume);

    /**
     * 新增生产物料消耗记录
     *
     * @param produceMaterialConsume 生产物料消耗记录
     * @return 结果
     */
    public int insertProduceMaterialConsume(ProduceMaterialConsume produceMaterialConsume);

    /**
     * 修改生产物料消耗记录
     *
     * @param produceMaterialConsume 生产物料消耗记录
     * @return 结果
     */
    public int updateProduceMaterialConsume(ProduceMaterialConsume produceMaterialConsume);

    /**
     * 批量删除生产物料消耗记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteProduceMaterialConsumeByIds(Long[] ids);

    /**
     * 删除生产物料消耗记录信息
     *
     * @param id 生产物料消耗记录ID
     * @return 结果
     */
    public int deleteProduceMaterialConsumeById(Long id);

    /**
     * 根据生产计划ID汇总实际损耗
     */
    public BigDecimal sumActualLossByProducePlan(Long producePlanId);

    /**
     * 根据生产计划ID查询记录
     */
    public List<ProduceMaterialConsume> selectByProducePlanId(Long producePlanId);

    /**
     * 计算限额：BOM用量 × (1 + 标准损耗率/100)
     */
    public BigDecimal calculateLimitQty(BigDecimal bomQty, BigDecimal standardLossRate);

    /**
     * 计算实际损耗：实际领用 - BOM用量
     */
    public BigDecimal calculateActualLoss(BigDecimal actualQty, BigDecimal bomQty);

    /**
     * 查询损耗统计汇总（总数/超限额数/待审批数/超限额率）
     */
    public java.util.Map<String, Object> selectLossStats();

    /**
     * 审批超领申请
     *
     * @param id       记录ID
     * @param approved true=批准 false=拒绝
     * @param remark   审批备注
     * @return 结果
     */
    public int approveLoss(Long id, boolean approved, String remark);
}
