package com.ruoyi.erp.cost.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.cost.domain.ErpCostSummary;
import com.ruoyi.erp.cost.mapper.ErpCostSummaryMapper;
import com.ruoyi.erp.cost.service.IErpCostSummaryService;

@Service
public class ErpCostSummaryServiceImpl implements IErpCostSummaryService {

    @Autowired
    private ErpCostSummaryMapper costSummaryMapper;

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
