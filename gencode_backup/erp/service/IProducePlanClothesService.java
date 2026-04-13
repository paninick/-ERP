package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.ProducePlanClothes;

/**
 * 生产计划衣服明细Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface IProducePlanClothesService {
    /**
     * 查询生产计划衣服明细
     *
     * @param id 生产计划衣服明细主键
     * @return 生产计划衣服明细
     */
    ProducePlanClothes selectProducePlanClothesById(Long id);

    /**
     * 查询生产计划衣服明细列表
     *
     * @param producePlanClothes 生产计划衣服明细
     * @return 生产计划衣服明细集合
     */
    List<ProducePlanClothes> selectProducePlanClothesList(ProducePlanClothes producePlanClothes);

    /**
     * 新增生产计划衣服明细
     *
     * @param producePlanClothes 生产计划衣服明细
     * @return 结果
     */
    int insertProducePlanClothes(ProducePlanClothes producePlanClothes);

    /**
     * 修改生产计划衣服明细
     *
     * @param producePlanClothes 生产计划衣服明细
     * @return 结果
     */
    int updateProducePlanClothes(ProducePlanClothes producePlanClothes);

    /**
     * 批量删除生产计划衣服明细
     *
     * @param ids 需要删除的生产计划衣服明细主键集合
     * @return 结果
     */
    int deleteProducePlanClothesByIds(Long[] ids);

    /**
     * 删除生产计划衣服明细信息
     *
     * @param id 生产计划衣服明细主键
     * @return 结果
     */
    int deleteProducePlanClothesById(Long id);

    /**
     * 新增生产计划衣服明细批量
     *
     * @param list 生产计划衣服明细
     * @return 结果
     */
    int insertProducePlanClothesBatch(List<ProducePlanClothes> list);
}
