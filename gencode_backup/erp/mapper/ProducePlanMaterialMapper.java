package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.ProducePlanMaterial;

/**
 * 生产计划物料明细Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface ProducePlanMaterialMapper {
    /**
     * 查询生产计划物料明细
     *
     * @param id 生产计划物料明细主键
     * @return 生产计划物料明细
     */
    ProducePlanMaterial selectProducePlanMaterialById(Long id);

    /**
     * 查询生产计划物料明细列表
     *
     * @param producePlanMaterial 生产计划物料明细
     * @return 生产计划物料明细集合
     */
    List<ProducePlanMaterial> selectProducePlanMaterialList(ProducePlanMaterial producePlanMaterial);

    /**
     * 新增生产计划物料明细
     *
     * @param producePlanMaterial 生产计划物料明细
     * @return 结果
     */
    int insertProducePlanMaterial(ProducePlanMaterial producePlanMaterial);

    /**
     * 修改生产计划物料明细
     *
     * @param producePlanMaterial 生产计划物料明细
     * @return 结果
     */
    int updateProducePlanMaterial(ProducePlanMaterial producePlanMaterial);

    /**
     * 删除生产计划物料明细
     *
     * @param id 生产计划物料明细主键
     * @return 结果
     */
    int deleteProducePlanMaterialById(Long id);

    /**
     * 批量删除生产计划物料明细
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteProducePlanMaterialByIds(Long[] ids);

    /**
     * 批量新增生产计划物料明细批量
     *
     * @param list 生产计划物料明细
     * @return 结果
     */
    int insertProducePlanMaterialBatch(List<ProducePlanMaterial> list);
}
