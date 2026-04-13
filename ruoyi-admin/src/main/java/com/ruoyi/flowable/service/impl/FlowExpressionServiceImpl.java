package com.ruoyi.flowable.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.flowable.domain.FlowExpression;
import com.ruoyi.flowable.mapper.FlowExpressionMapper;
import com.ruoyi.flowable.service.IFlowExpressionService;

@Service
public class FlowExpressionServiceImpl implements IFlowExpressionService {
    
    @Autowired
    private FlowExpressionMapper flowExpressionMapper;

    @Override
    public List<FlowExpression> selectFlowExpressionList(FlowExpression flowExpression) {
        return flowExpressionMapper.selectFlowExpressionList(flowExpression);
    }

    @Override
    public FlowExpression selectFlowExpressionById(Long id) {
        return flowExpressionMapper.selectFlowExpressionById(id);
    }

    @Override
    public int insertFlowExpression(FlowExpression flowExpression) {
        return flowExpressionMapper.insertFlowExpression(flowExpression);
    }

    @Override
    public int updateFlowExpression(FlowExpression flowExpression) {
        return flowExpressionMapper.updateFlowExpression(flowExpression);
    }

    @Override
    public int deleteFlowExpressionByIds(Long[] ids) {
        return flowExpressionMapper.deleteFlowExpressionByIds(ids);
    }

    @Override
    public int deleteFlowExpressionById(Long id) {
        return flowExpressionMapper.deleteFlowExpressionById(id);
    }
}
