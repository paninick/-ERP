package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.ProducePlan;

/**
 * 生产计划Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface ProducePlanMapper {
    /**
     * 查询生产计划
     *
     * @param id 生产计划主键
     * @return 生产计划
     */
    ProducePlan selectProducePlanById(Long id);

    /**
     * 查询生产计划列表
     *
     * @param producePlan 生产计划
     * @return 生产计划集合
     */
    List<ProducePlan> selectProducePlanList(ProducePlan producePlan);

    /**
     * 新增生产计划
     *
     * @param producePlan 生产计划
     * @return 结果
     */
    int insertProducePlan(ProducePlan producePlan);

    /**
     * 修改生产计划
     *
     * @param producePlan 生产计划
     * @return 结果
     */
    int updateProducePlan(ProducePlan producePlan);

    /**
     * 删除生产计划
     *
     * @param id 生产计划主键
     * @return 结果
     */
    int deleteProducePlanById(Long id);

    /**
     * 批量删除生产计划
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteProducePlanByIds(Long[] ids);

    /**
     * 批量新增生产计划批量
     *
     * @param list 生产计划
     * @return 结果
     */
    int insertProducePlanBatch(List<ProducePlan> list);
}
