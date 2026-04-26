package com.ruoyi.erp.orgunit.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.erp.orgunit.domain.OrgUnit;
import com.ruoyi.erp.orgunit.mapper.OrgUnitMapper;
import com.ruoyi.erp.orgunit.service.IOrgUnitService;

/**
 * 组织层级Service业务层处理
 *
 * @author erp
 * @date 2026-04-25
 */
@Service
public class OrgUnitServiceImpl implements IOrgUnitService {

    @Autowired
    private OrgUnitMapper orgUnitMapper;

    @Override
    public OrgUnit selectOrgUnitById(Long id) {
        return orgUnitMapper.selectOrgUnitById(id);
    }

    @Override
    public List<OrgUnit> selectOrgUnitList(OrgUnit orgUnit) {
        return orgUnitMapper.selectOrgUnitList(orgUnit);
    }

    @Override
    public int insertOrgUnit(OrgUnit orgUnit) {
        validateOrgUnit(orgUnit, false);
        return orgUnitMapper.insertOrgUnit(orgUnit);
    }

    @Override
    public int updateOrgUnit(OrgUnit orgUnit) {
        validateOrgUnit(orgUnit, true);
        return orgUnitMapper.updateOrgUnit(orgUnit);
    }

    @Override
    public int deleteOrgUnitById(Long id) {
        if (hasChildren(id)) {
            throw new ServiceException("存在子节点，不允许删除");
        }
        return orgUnitMapper.deleteOrgUnitById(id);
    }

    @Override
    public int deleteOrgUnitByIds(Long[] ids) {
        for (Long id : ids) {
            if (hasChildren(id)) {
                OrgUnit unit = orgUnitMapper.selectOrgUnitById(id);
                String name = (unit != null) ? unit.getOrgName() : String.valueOf(id);
                throw new ServiceException("【" + name + "】存在子节点，不允许删除");
            }
        }
        return orgUnitMapper.deleteOrgUnitByIds(ids);
    }

    @Override
    public List<OrgUnit> selectOrgUnitByParentId(Long parentId) {
        return orgUnitMapper.selectOrgUnitByParentId(parentId);
    }

    private void validateOrgUnit(OrgUnit orgUnit, boolean isUpdate) {
        Long parentId = orgUnit.getParentId();
        Long currentId = orgUnit.getId();

        // parentId=0 表示顶级节点，不需要校验
        if (parentId == null || parentId == 0) {
            return;
        }

        // 父节点必须存在
        OrgUnit parent = orgUnitMapper.selectOrgUnitById(parentId);
        if (parent == null) {
            throw new ServiceException("上级组织不存在，请重新选择");
        }

        if (isUpdate && currentId != null) {
            // 禁止挂到自己
            if (currentId.equals(parentId)) {
                throw new ServiceException("不能将自己设为上级组织");
            }
            // 禁止挂到自己的子孙节点下
            if (isSelfOrDescendant(parentId, currentId)) {
                throw new ServiceException("不能将节点移动到其子孙节点下");
            }
        }
    }

    private boolean hasChildren(Long id) {
        return orgUnitMapper.hasChildByParentId(id) > 0;
    }

    private boolean isSelfOrDescendant(Long targetId, Long currentId) {
        if (targetId == null || currentId == null) {
            return false;
        }
        if (targetId.equals(currentId)) {
            return true;
        }
        // BFS 遍历 currentId 的所有子节点，visited 集合防历史脏环
        Set<Long> visited = new HashSet<>();
        List<Long> queue = new ArrayList<>();
        visited.add(currentId);
        queue.add(currentId);
        int index = 0;
        while (index < queue.size()) {
            Long nodeId = queue.get(index++);
            List<OrgUnit> children = orgUnitMapper.selectOrgUnitByParentId(nodeId);
            for (OrgUnit child : children) {
                Long childId = child.getId();
                if (!visited.add(childId)) {
                    continue; // 已访问过，跳过防环
                }
                if (childId.equals(targetId)) {
                    return true;
                }
                queue.add(childId);
            }
        }
        return false;
    }
}
