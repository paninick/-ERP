package com.ruoyi.flowable.service;

import java.util.List;
import com.ruoyi.flowable.domain.FlowableDefinition;

public interface IFlowableDefinitionService {

    List<FlowableDefinition> selectDefinitionList(FlowableDefinition definition);

    FlowableDefinition selectDefinitionById(String id);

    int insertDefinition(FlowableDefinition definition);

    int updateDefinition(FlowableDefinition definition);

    int deleteDefinitionByIds(String[] ids);

    int activateDefinition(String id);

    int suspendDefinition(String id);
}
