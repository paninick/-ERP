package com.ruoyi.flowable.service;

import java.util.List;
import com.ruoyi.flowable.domain.FlowListener;

public interface IFlowListenerService {
    public List<FlowListener> selectFlowListenerList(FlowListener flowListener);
    
    public FlowListener selectFlowListenerById(Long id);
    
    public int insertFlowListener(FlowListener flowListener);
    
    public int updateFlowListener(FlowListener flowListener);
    
    public int deleteFlowListenerByIds(Long[] ids);
    
    public int deleteFlowListenerById(Long id);
}
