package com.ruoyi.flowable.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.flowable.domain.FlowListener;
import com.ruoyi.flowable.mapper.FlowListenerMapper;
import com.ruoyi.flowable.service.IFlowListenerService;

@Service
public class FlowListenerServiceImpl implements IFlowListenerService {
    
    @Autowired
    private FlowListenerMapper flowListenerMapper;

    @Override
    public List<FlowListener> selectFlowListenerList(FlowListener flowListener) {
        return flowListenerMapper.selectFlowListenerList(flowListener);
    }

    @Override
    public FlowListener selectFlowListenerById(Long id) {
        return flowListenerMapper.selectFlowListenerById(id);
    }

    @Override
    public int insertFlowListener(FlowListener flowListener) {
        return flowListenerMapper.insertFlowListener(flowListener);
    }

    @Override
    public int updateFlowListener(FlowListener flowListener) {
        return flowListenerMapper.updateFlowListener(flowListener);
    }

    @Override
    public int deleteFlowListenerByIds(Long[] ids) {
        return flowListenerMapper.deleteFlowListenerByIds(ids);
    }

    @Override
    public int deleteFlowListenerById(Long id) {
        return flowListenerMapper.deleteFlowListenerById(id);
    }
}
