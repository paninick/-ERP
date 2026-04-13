package com.ruoyi.flowable.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import com.ruoyi.flowable.domain.FlowableDefinition;
import com.ruoyi.flowable.service.IFlowableDefinitionService;
import org.apache.commons.lang3.StringUtils;

@Service
public class FlowableDefinitionServiceImpl implements IFlowableDefinitionService {

    private static final ConcurrentHashMap<String, FlowableDefinition> definitionMap = new ConcurrentHashMap<>();

    static {
        Date now = new Date();
        FlowableDefinition def1 = new FlowableDefinition();
        def1.setId("def-001");
        def1.setName("请假审批流程");
        def1.setKey("leave-process");
        def1.setVersion(1);
        def1.setSuspended(false);
        def1.setDeploymentTime(now);
        def1.setDescription("员工请假审批流程");
        def1.setCategory("OA");
        definitionMap.put(def1.getId(), def1);

        FlowableDefinition def2 = new FlowableDefinition();
        def2.setId("def-002");
        def2.setName("报销审批流程");
        def2.setKey("expense-process");
        def2.setVersion(1);
        def2.setSuspended(false);
        def2.setDeploymentTime(now);
        def2.setDescription("费用报销审批流程");
        def2.setCategory("财务");
        definitionMap.put(def2.getId(), def2);
    }

    @Override
    public List<FlowableDefinition> selectDefinitionList(FlowableDefinition definition) {
        List<FlowableDefinition> list = new ArrayList<>(definitionMap.values());
        if (definition != null) {
            if (StringUtils.isNotBlank(definition.getName())) {
                list.removeIf(d -> !d.getName().contains(definition.getName()));
            }
            if (StringUtils.isNotBlank(definition.getKey())) {
                list.removeIf(d -> !d.getKey().contains(definition.getKey()));
            }
            if (definition.getSuspended() != null) {
                list.removeIf(d -> !d.getSuspended().equals(definition.getSuspended()));
            }
        }
        return list;
    }

    @Override
    public FlowableDefinition selectDefinitionById(String id) {
        return definitionMap.get(id);
    }

    @Override
    public int insertDefinition(FlowableDefinition definition) {
        if (definition.getId() == null) {
            definition.setId("def-" + System.currentTimeMillis());
        }
        if (definition.getVersion() == null) {
            definition.setVersion(1);
        }
        if (definition.getSuspended() == null) {
            definition.setSuspended(false);
        }
        if (definition.getDeploymentTime() == null) {
            definition.setDeploymentTime(new Date());
        }
        definitionMap.put(definition.getId(), definition);
        return 1;
    }

    @Override
    public int updateDefinition(FlowableDefinition definition) {
        if (definitionMap.containsKey(definition.getId())) {
            definitionMap.put(definition.getId(), definition);
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteDefinitionByIds(String[] ids) {
        int count = 0;
        for (String id : ids) {
            if (definitionMap.remove(id) != null) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int activateDefinition(String id) {
        FlowableDefinition def = definitionMap.get(id);
        if (def != null) {
            def.setSuspended(false);
            return 1;
        }
        return 0;
    }

    @Override
    public int suspendDefinition(String id) {
        FlowableDefinition def = definitionMap.get(id);
        if (def != null) {
            def.setSuspended(true);
            return 1;
        }
        return 0;
    }
}
