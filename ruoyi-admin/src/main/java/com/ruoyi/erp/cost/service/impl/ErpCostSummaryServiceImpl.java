package com.ruoyi.erp.cost.service.impl;

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

    /**
     * TODO: W5 — 实现成本自动汇总引擎
     * 从 t_erp_produce_material_consume / t_erp_piece_wage /
     * t_erp_outsource_order / t_erp_stock_in 四表按 producePlanId
     * 聚合 materialCost + wageCost + outsourceCost + freightCost，
     * 计算 totalCost = SUM(above) + qualityLoss + otherCost，
     * 写入 t_erp_cost_summary。当前为手动 CRUD 模式。
     */
    public int calculateByPlanId(Long producePlanId) {
        throw new UnsupportedOperationException("成本自动汇总引擎尚未实现，请通过 POST/PUT 手动录入");
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
