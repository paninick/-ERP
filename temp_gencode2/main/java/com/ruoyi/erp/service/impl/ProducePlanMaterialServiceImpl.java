package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.ProducePlanMaterialMapper;
import com.ruoyi.erp.domain.ProducePlanMaterial;
import com.ruoyi.erp.service.IProducePlanMaterialService;

/**
 * 生产计划物料明细Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
@RequiredArgsConstructor
@Service
public class ProducePlanMaterialServiceImpl implements IProducePlanMaterialService {
    private final ProducePlanMaterialMapper producePlanMaterialMapper;

    /**
     * 查询生产计划物料明细
     *
     * @param id 生产计划物料明细主键
     * @return 生产计划物料明细
     */
    @Override
    public ProducePlanMaterial selectProducePlanMaterialById(Long id) {
        return producePlanMaterialMapper.selectProducePlanMaterialById(id);
    }

    /**
     * 查询生产计划物料明细列表
     *
     * @param producePlanMaterial 生产计划物料明细
     * @return 生产计划物料明细
     */
    @Override
    public List<ProducePlanMaterial> selectProducePlanMaterialList(ProducePlanMaterial producePlanMaterial) {
        return producePlanMaterialMapper.selectProducePlanMaterialList(producePlanMaterial);
    }

    /**
     * 新增生产计划物料明细
     *
     * @param producePlanMaterial 生产计划物料明细
     * @return 结果
     */
    @Override
    public int insertProducePlanMaterial(ProducePlanMaterial producePlanMaterial) {
        producePlanMaterial.setCreateBy(SecurityUtils.getUserIdStr());
        producePlanMaterial.setCreateTime(DateUtils.getNowDate());
        return producePlanMaterialMapper.insertProducePlanMaterial(producePlanMaterial);
    }

    /**
     * 修改生产计划物料明细
     *
     * @param producePlanMaterial 生产计划物料明细
     * @return 结果
     */
    @Override
    public int updateProducePlanMaterial(ProducePlanMaterial producePlanMaterial) {
        producePlanMaterial.setUpdateTime(DateUtils.getNowDate());
        return producePlanMaterialMapper.updateProducePlanMaterial(producePlanMaterial);
    }

    /**
     * 批量删除生产计划物料明细
     *
     * @param ids 需要删除的生产计划物料明细主键
     * @return 结果
     */
    @Override
    public int deleteProducePlanMaterialByIds(Long[] ids) {
        return producePlanMaterialMapper.deleteProducePlanMaterialByIds(ids);
    }

    /**
     * 删除生产计划物料明细信息
     *
     * @param id 生产计划物料明细主键
     * @return 结果
     */
    @Override
    public int deleteProducePlanMaterialById(Long id) {
        return producePlanMaterialMapper.deleteProducePlanMaterialById(id);
    }

    /**
     * 批量新增生产计划物料明细
     *
     * @param list 生产计划物料明细
     * @return 结果
     */
    @Override
    public int insertProducePlanMaterialBatch(List<ProducePlanMaterial> list) {
        return producePlanMaterialMapper.insertProducePlanMaterialBatch(list);
    }
}
