package com.ruoyi.erp.service.impl;

import com.ruoyi.erp.domain.ProduceMaterialConsume;
import com.ruoyi.erp.mapper.ProduceMaterialConsumeMapper;
import com.ruoyi.erp.service.IProduceMaterialConsumeService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 生产物料消耗记录Service业务层处理
 *
 * @author zhangmingjian
 */
@Service
public class ProduceMaterialConsumeServiceImpl implements IProduceMaterialConsumeService {

    @Autowired
    private ProduceMaterialConsumeMapper produceMaterialConsumeMapper;

    /**
     * 查询生产物料消耗记录
     *
     * @param id 生产物料消耗记录ID
     * @return 生产物料消耗记录
     */
    @Override
    public ProduceMaterialConsume selectProduceMaterialConsumeById(Long id) {
        return produceMaterialConsumeMapper.selectProduceMaterialConsumeById(id);
    }

    /**
     * 查询生产物料消耗记录列表
     *
     * @param produceMaterialConsume 生产物料消耗记录
     * @return 生产物料消耗记录
     */
    @Override
    public List<ProduceMaterialConsume> selectProduceMaterialConsumeList(ProduceMaterialConsume produceMaterialConsume) {
        return produceMaterialConsumeMapper.selectProduceMaterialConsumeList(produceMaterialConsume);
    }

    /**
     * 新增生产物料消耗记录
     *
     * @param consume 生产物料消耗记录
     * @return 结果
     */
    @Override
    public int insertProduceMaterialConsume(ProduceMaterialConsume consume) {
        // 计算限额损耗和实际损耗
        BigDecimal limitQty = calculateLimitQty(consume.getBomQty(), consume.getStandardLossRate());
        consume.setLimitLossQty(limitQty);
        BigDecimal actualLoss = calculateActualLoss(consume.getActualQty(), consume.getBomQty());
        consume.setActualLossQty(actualLoss);
        // 判断是否超限额
        if (actualLoss.compareTo(limitQty) > 0) {
            consume.setIsOverLimit("1");
            consume.setApprovalStatus("1"); // 需要审批
        } else {
            consume.setIsOverLimit("0");
            consume.setApprovalStatus("0"); // 无需审批
        }
        consume.setCreateBy(SecurityUtils.getUsername());
        consume.setCreateTime(DateUtils.getNowDate());
        consume.setUpdateBy(SecurityUtils.getUsername());
        consume.setUpdateTime(DateUtils.getNowDate());
        return produceMaterialConsumeMapper.insertProduceMaterialConsume(consume);
    }

    /**
     * 修改生产物料消耗记录
     *
     * @param consume 生产物料消耗记录
     * @return 结果
     */
    @Override
    public int updateProduceMaterialConsume(ProduceMaterialConsume consume) {
        // 重新计算
        BigDecimal limitQty = calculateLimitQty(consume.getBomQty(), consume.getStandardLossRate());
        consume.setLimitLossQty(limitQty);
        BigDecimal actualLoss = calculateActualLoss(consume.getActualQty(), consume.getBomQty());
        consume.setActualLossQty(actualLoss);
        if (actualLoss.compareTo(limitQty) > 0) {
            consume.setIsOverLimit("1");
            if (!"1".equals(consume.getApprovalStatus())) {
                consume.setApprovalStatus("1");
            }
        } else {
            consume.setIsOverLimit("0");
            consume.setApprovalStatus("0");
        }
        consume.setUpdateBy(SecurityUtils.getUsername());
        consume.setUpdateTime(DateUtils.getNowDate());
        return produceMaterialConsumeMapper.updateProduceMaterialConsume(consume);
    }

    /**
     * 批量删除生产物料消耗记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteProduceMaterialConsumeByIds(Long[] ids) {
        return produceMaterialConsumeMapper.deleteProduceMaterialConsumeByIds(ids);
    }

    /**
     * 删除生产物料消耗记录信息
     *
     * @param id 生产物料消耗记录ID
     * @return 结果
     */
    @Override
    public int deleteProduceMaterialConsumeById(Long id) {
        return produceMaterialConsumeMapper.deleteProduceMaterialConsumeById(id);
    }

    @Override
    public BigDecimal sumActualLossByProducePlan(Long producePlanId) {
        return produceMaterialConsumeMapper.sumActualLossByProducePlan(producePlanId);
    }

    @Override
    public List<ProduceMaterialConsume> selectByProducePlanId(Long producePlanId) {
        return produceMaterialConsumeMapper.selectByProducePlanId(producePlanId);
    }

    @Override
    public BigDecimal calculateLimitQty(BigDecimal bomQty, BigDecimal standardLossRate) {
        // limit = bomQty * (1 + standardLossRate / 100)
        BigDecimal rate = standardLossRate.divide(new BigDecimal("100"), RoundingMode.HALF_UP);
        return bomQty.multiply(BigDecimal.ONE.add(rate));
    }

    @Override
    public BigDecimal calculateActualLoss(BigDecimal actualQty, BigDecimal bomQty) {
        // loss = actualQty - bomQty
        return actualQty.subtract(bomQty);
    }
}
