package com.ruoyi.erp.cost.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.erp.cost.domain.ErpCostSummary;
import com.ruoyi.erp.cost.mapper.ErpCostSummaryMapper;
import com.ruoyi.erp.cost.service.IErpCostSummaryService;

@Service
@Transactional(rollbackFor = Exception.class)
public class ErpCostSummaryServiceImpl implements IErpCostSummaryService {

    @Autowired
    private ErpCostSummaryMapper costSummaryMapper;

    @Override
    public int calculateByPlanId(Long producePlanId) {
        costSummaryMapper.deleteByPlanId(producePlanId);

        BigDecimal material = costSummaryMapper.sumMaterialCostByPlan(producePlanId);
        BigDecimal wage = costSummaryMapper.sumWageCostByPlan(producePlanId);
        BigDecimal outsource = costSummaryMapper.sumOutsourceCostByPlan(producePlanId);
        BigDecimal finishQty = costSummaryMapper.sumFinishQtyByPlan(producePlanId);

        BigDecimal total = material.add(wage).add(outsource);
        BigDecimal unitCost = finishQty.compareTo(BigDecimal.ZERO) > 0
            ? total.divide(finishQty, 6, RoundingMode.HALF_UP) : BigDecimal.ZERO;

        ErpCostSummary cs = new ErpCostSummary();
        cs.setProducePlanId(producePlanId);
        cs.setMaterialCost(material);
        cs.setWageCost(wage);
        cs.setOutsourceCost(outsource);
        cs.setFreightCost(BigDecimal.ZERO);
        cs.setQualityLoss(BigDecimal.ZERO);
        cs.setOtherCost(BigDecimal.ZERO);
        cs.setTotalCost(total);
        cs.setFinishQty(finishQty);
        cs.setUnitCost(unitCost);
        cs.setCalcTime(new Date());

        return costSummaryMapper.insert(cs);
    }

    @Override
    public ErpCostSummary selectById(Long id) {
        return costSummaryMapper.selectById(id);
    }

    @Override
    public List<ErpCostSummary> selectList(ErpCostSummary summary) {
        return costSummaryMapper.selectList(summary);
    }

    @Override
    public int insert(ErpCostSummary summary) {
        return costSummaryMapper.insert(summary);
    }

    @Override
    public int update(ErpCostSummary summary) {
        return costSummaryMapper.update(summary);
    }

    @Override
    public int deleteById(Long id) {
        return costSummaryMapper.deleteById(id);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return costSummaryMapper.deleteByIds(ids);
    }
}
