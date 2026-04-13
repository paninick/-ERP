package com.ruoyi.flowable.mapper;

import java.util.List;
import com.ruoyi.flowable.domain.FlowExpression;

public interface FlowExpressionMapper {
    public List<FlowExpression> selectFlowExpressionList(FlowExpression flowExpression);
    
    public FlowExpression selectFlowExpressionById(Long id);
    
    public int insertFlowExpression(FlowExpression flowExpression);
    
    public int updateFlowExpression(FlowExpression flowExpression);
    
    public int deleteFlowExpressionById(Long id);
    
    public int deleteFlowExpressionByIds(Long[] ids);
}
