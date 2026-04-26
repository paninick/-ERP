package com.ruoyi.erp.orgunit.mapper;

import java.util.List;
import com.ruoyi.erp.orgunit.domain.OrgUnit;

/**
 * 组织层级Mapper接口
 *
 * @author erp
 * @date 2026-04-25
 */
public interface OrgUnitMapper {
    /**
     * 查询组织层级
     *
     * @param id 组织层级主键
     * @return 组织层级
     */
    OrgUnit selectOrgUnitById(Long id);

    /**
     * 查询组织层级列表
     *
     * @param orgUnit 组织层级
     * @return 组织层级集合
     */
    List<OrgUnit> selectOrgUnitList(OrgUnit orgUnit);

    /**
     * 新增组织层级
     *
     * @param orgUnit 组织层级
     * @return 结果
     */
    int insertOrgUnit(OrgUnit orgUnit);

    /**
     * 修改组织层级
     *
     * @param orgUnit 组织层级
     * @return 结果
     */
    int updateOrgUnit(OrgUnit orgUnit);

    /**
     * 删除组织层级
     *
     * @param id 组织层级主键
     * @return 结果
     */
    int deleteOrgUnitById(Long id);

    /**
     * 批量删除组织层级
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteOrgUnitByIds(Long[] ids);

    /**
     * 根据父ID查询子组织
     *
     * @param parentId 父ID
     * @return 子组织集合
     */
    List<OrgUnit> selectOrgUnitByParentId(Long parentId);

    /**
     * 查询子组织数量（用于删除前校验）
     *
     * @param id 组织ID
     * @return 子组织数量
     */
    int hasChildByParentId(Long id);
}
