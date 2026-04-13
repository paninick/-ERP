package com.ruoyi.flowable.mapper;

import java.util.List;
import com.ruoyi.flowable.domain.FlowListener;

public interface FlowListenerMapper {
    public List<FlowListener> selectFlowListenerList(FlowListener flowListener);
    
    public FlowListener selectFlowListenerById(Long id);
    
    public int insertFlowListener(FlowListener flowListener);
    
    public int updateFlowListener(FlowListener flowListener);
    
    public int deleteFlowListenerById(Long id);
    
    public int deleteFlowListenerByIds(Long[] ids);
}
