package com.ruoyi.erp.service;

import com.ruoyi.erp.domain.ProduceJob;

import java.util.List;

/**
 * 生产工票Service接口
 *
 * @author ruoyi
 */
public interface IProduceJobService {
    /**
     * 查询生产工票
     *
     * @param id 生产工票ID
     * @return 生产工票
     */
    public ProduceJob selectProduceJobById(Long id);

    /**
     * 查询生产工票列表
     *
     * @param produceJob 生产工票
     * @return 生产工票集合
     */
    public List<ProduceJob> selectProduceJobList(ProduceJob produceJob);

    /**
     * 新增生产工票
     *
     * @param produceJob 生产工票
     * @return 结果
     */
    public int insertProduceJob(ProduceJob produceJob);

    /**
     * 批量新增生产工票
     *
     * @param produceJobList 生产工票列表
     * @return 结果
     */
    public int batchInsertProduceJob(List<ProduceJob> produceJobList);

    /**
     * 修改生产工票
     *
     * @param produceJob 生产工票
     * @return 结果
     */
    public int updateProduceJob(ProduceJob produceJob);

    /**
     * 批量删除生产工票
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteProduceJobByIds(Long[] ids);

    /**
     * 删除生产工票信息
     *
     * @param id 生产工票ID
     * @return 结果
     */
    public int deleteProduceJobById(Long id);

    /**
     * 根据生产计划ID查询工票列表
     *
     * @param producePlanId 生产计划ID
     * @return 工票列表
     */
    public List<ProduceJob> selectProduceJobByProducePlanId(Long producePlanId);

    /**
     * 根据生产计划ID统计总计划数量
     *
     * @param producePlanId 生产计划ID
     * @return 总计划数量
     */
    public Integer sumPlanQtyByProducePlanId(Long producePlanId);

    /**
     * 根据生产计划ID统计已完成数量
     *
     * @param producePlanId 生产计划ID
     * @return 已完成数量
     */
    public Integer sumActualQtyByProducePlanId(Long producePlanId);
}
