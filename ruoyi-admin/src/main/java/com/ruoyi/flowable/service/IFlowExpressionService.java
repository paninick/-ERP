package com.ruoyi.flowable.service;

import java.util.List;
import com.ruoyi.flowable.domain.FlowExpression;

public interface IFlowExpressionService {
    public List<FlowExpression> selectFlowExpressionList(FlowExpression flowExpression);
    
    public FlowExpression selectFlowExpressionById(Long id);
    
    public int insertFlowExpression(FlowExpression flowExpression);
    
    public int updateFlowExpression(FlowExpression flowExpression);
    
    public int deleteFlowExpressionByIds(Long[] ids);
    
    public int deleteFlowExpressionById(Long id);
}
