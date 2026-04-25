package com.ruoyi.erp.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.erp.domain.ProduceMaterialConsume;
import com.ruoyi.erp.mapper.ProduceMaterialConsumeMapper;
import com.ruoyi.erp.service.IProduceMaterialConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProduceMaterialConsumeServiceImpl implements IProduceMaterialConsumeService {

    @Autowired
    private ProduceMaterialConsumeMapper produceMaterialConsumeMapper;

    @Override
    public ProduceMaterialConsume selectProduceMaterialConsumeById(Long id) {
        return produceMaterialConsumeMapper.selectProduceMaterialConsumeById(id);
    }

    @Override
    public List<ProduceMaterialConsume> selectProduceMaterialConsumeList(ProduceMaterialConsume produceMaterialConsume) {
        return produceMaterialConsumeMapper.selectProduceMaterialConsumeList(produceMaterialConsume);
    }

    @Override
    public int insertProduceMaterialConsume(ProduceMaterialConsume consume) {
        fillDerivedFields(consume);
        consume.setCreateBy(SecurityUtils.getUsername());
        consume.setCreateTime(DateUtils.getNowDate());
        consume.setUpdateBy(SecurityUtils.getUsername());
        consume.setUpdateTime(DateUtils.getNowDate());
        return produceMaterialConsumeMapper.insertProduceMaterialConsume(consume);
    }

    @Override
    public int updateProduceMaterialConsume(ProduceMaterialConsume consume) {
        fillDerivedFields(consume);
        consume.setUpdateBy(SecurityUtils.getUsername());
        consume.setUpdateTime(DateUtils.getNowDate());
        return produceMaterialConsumeMapper.updateProduceMaterialConsume(consume);
    }

    @Override
    public int deleteProduceMaterialConsumeByIds(Long[] ids) {
        return produceMaterialConsumeMapper.deleteProduceMaterialConsumeByIds(ids);
    }

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
        BigDecimal safeBomQty = defaultDecimal(bomQty);
        BigDecimal safeLossRate = defaultDecimal(standardLossRate);
        BigDecimal rate = safeLossRate.divide(new BigDecimal("100"), 6, RoundingMode.HALF_UP);
        return safeBomQty.multiply(BigDecimal.ONE.add(rate)).setScale(4, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateActualLoss(BigDecimal actualQty, BigDecimal bomQty) {
        return defaultDecimal(actualQty).subtract(defaultDecimal(bomQty)).setScale(4, RoundingMode.HALF_UP);
    }

    @Override
    public Map<String, Object> selectLossStats() {
        Map<String, Object> stats = produceMaterialConsumeMapper.selectLossStats();
        if (stats == null) {
            stats = new HashMap<>();
        }
        long total = stats.get("totalRecords") != null ? ((Number) stats.get("totalRecords")).longValue() : 0L;
        long overLimit = stats.get("overLimitCount") != null ? ((Number) stats.get("overLimitCount")).longValue() : 0L;
        String rate = total > 0
            ? new BigDecimal(overLimit)
                .divide(new BigDecimal(total), 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"))
                .setScale(2, RoundingMode.HALF_UP)
                .toPlainString()
            : "0.00";
        stats.put("overLimitRate", rate);
        return stats;
    }

    @Override
    public int approveLoss(Long id, boolean approved, String remark) {
        ProduceMaterialConsume consume = produceMaterialConsumeMapper.selectProduceMaterialConsumeById(id);
        if (consume == null) {
            return 0;
        }
        consume.setApprovalStatus(approved ? "2" : "3");
        consume.setApprovalRemark(remark);
        consume.setApprovalByName(SecurityUtils.getUsername());
        consume.setApprovalTime(DateUtils.getNowDate());
        consume.setUpdateBy(SecurityUtils.getUsername());
        consume.setUpdateTime(DateUtils.getNowDate());
        return produceMaterialConsumeMapper.updateProduceMaterialConsume(consume);
    }

    private void fillDerivedFields(ProduceMaterialConsume consume) {
        BigDecimal limitQty = calculateLimitQty(consume.getBomQty(), consume.getStandardLossRate());
        BigDecimal actualLoss = calculateActualLoss(consume.getActualQty(), consume.getBomQty());
        BigDecimal allowedLossQty = limitQty.subtract(defaultDecimal(consume.getBomQty())).setScale(4, RoundingMode.HALF_UP);
        consume.setLimitLossQty(limitQty);
        consume.setActualLossQty(actualLoss);

        if (actualLoss.compareTo(allowedLossQty) > 0) {
            consume.setIsOverLimit("1");
            if (!"2".equals(consume.getApprovalStatus()) && !"3".equals(consume.getApprovalStatus())) {
                consume.setApprovalStatus("1");
            }
        } else {
            consume.setIsOverLimit("0");
            consume.setApprovalStatus("0");
        }

        BigDecimal unitPrice = defaultDecimal(consume.getUnitPrice());
        BigDecimal theoreticalCost = defaultDecimal(consume.getBomQty()).multiply(unitPrice).setScale(4, RoundingMode.HALF_UP);
        BigDecimal actualCost = defaultDecimal(consume.getActualQty()).multiply(unitPrice).setScale(4, RoundingMode.HALF_UP);
        consume.setTheoreticalCost(theoreticalCost);
        consume.setActualCost(actualCost);
        consume.setCostDiff(actualCost.subtract(theoreticalCost).setScale(4, RoundingMode.HALF_UP));
    }

    private BigDecimal defaultDecimal(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }
}
