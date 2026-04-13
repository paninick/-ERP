package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.ProducePlanClothesMapper;
import com.ruoyi.erp.domain.ProducePlanClothes;
import com.ruoyi.erp.service.IProducePlanClothesService;

/**
 * 生产计划衣服明细Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@Service
public class ProducePlanClothesServiceImpl implements IProducePlanClothesService {
    @Autowired
    private ProducePlanClothesMapper producePlanClothesMapper;

    /**
     * 查询生产计划衣服明细
     *
     * @param id 生产计划衣服明细主键
     * @return 生产计划衣服明细
     */
    @Override
    public ProducePlanClothes selectProducePlanClothesById(Long id) {
        return producePlanClothesMapper.selectProducePlanClothesById(id);
    }

    /**
     * 查询生产计划衣服明细列表
     *
     * @param producePlanClothes 生产计划衣服明细
     * @return 生产计划衣服明细
     */
    @Override
    public List<ProducePlanClothes> selectProducePlanClothesList(ProducePlanClothes producePlanClothes) {
        return producePlanClothesMapper.selectProducePlanClothesList(producePlanClothes);
    }

    /**
     * 新增生产计划衣服明细
     *
     * @param producePlanClothes 生产计划衣服明细
     * @return 结果
     */
    @Override
    public int insertProducePlanClothes(ProducePlanClothes producePlanClothes) {
        producePlanClothes.setCreateBy(SecurityUtils.getUserId().toString());
        producePlanClothes.setCreateTime(DateUtils.getNowDate());
        return producePlanClothesMapper.insertProducePlanClothes(producePlanClothes);
    }

    /**
     * 修改生产计划衣服明细
     *
     * @param producePlanClothes 生产计划衣服明细
     * @return 结果
     */
    @Override
    public int updateProducePlanClothes(ProducePlanClothes producePlanClothes) {
        producePlanClothes.setUpdateTime(DateUtils.getNowDate());
        return producePlanClothesMapper.updateProducePlanClothes(producePlanClothes);
    }

    /**
     * 批量删除生产计划衣服明细
     *
     * @param ids 需要删除的生产计划衣服明细主键
     * @return 结果
     */
    @Override
    public int deleteProducePlanClothesByIds(Long[] ids) {
        return producePlanClothesMapper.deleteProducePlanClothesByIds(ids);
    }

    /**
     * 删除生产计划衣服明细信息
     *
     * @param id 生产计划衣服明细主键
     * @return 结果
     */
    @Override
    public int deleteProducePlanClothesById(Long id) {
        return producePlanClothesMapper.deleteProducePlanClothesById(id);
    }

    /**
     * 批量新增生产计划衣服明细
     *
     * @param list 生产计划衣服明细
     * @return 结果
     */
    @Override
    public int insertProducePlanClothesBatch(List<ProducePlanClothes> list) {
        return producePlanClothesMapper.insertProducePlanClothesBatch(list);
    }
}
