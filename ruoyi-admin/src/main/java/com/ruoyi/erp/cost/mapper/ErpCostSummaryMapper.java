package com.ruoyi.erp.cost.mapper;

import java.util.List;
import com.ruoyi.erp.cost.domain.ErpCostSummary;

import java.math.BigDecimal;

public interface ErpCostSummaryMapper {
    ErpCostSummary selectById(Long id);
    List<ErpCostSummary> selectList(ErpCostSummary summary);
    int insert(ErpCostSummary summary);
    int update(ErpCostSummary summary);
    int deleteById(Long id);
    int deleteByIds(Long[] ids);
    int deleteByPlanId(Long planId);
    BigDecimal sumMaterialCostByPlan(Long planId);
    BigDecimal sumWageCostByPlan(Long planId);
    BigDecimal sumOutsourceCostByPlan(Long planId);
    BigDecimal sumFinishQtyByPlan(Long planId);
}
