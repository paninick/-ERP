package com.ruoyi.erp.mapper;

import com.ruoyi.erp.domain.ProduceJob;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 生产工票Mapper接口
 *
 * @author ruoyi
 */
@Mapper
public interface ProduceJobMapper {
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
    public int batchInsertProduceJob(@Param("list") List<ProduceJob> produceJobList);

    /**
     * 修改生产工票
     *
     * @param produceJob 生产工票
     * @return 结果
     */
    public int updateProduceJob(ProduceJob produceJob);

    /**
     * 删除生产工票
     *
     * @param id 生产工票ID
     * @return 结果
     */
    public int deleteProduceJobById(Long id);

    /**
     * 批量删除生产工票
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteProduceJobByIds(Long[] ids);

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

    /**
     * 按状态统计工票数
     *
     * @param status 状态
     * @return 数量
     */
    public int countByStatus(String status);

    /**
     * 统计今日新增数量
     *
     * @param today 今日
     * @return 数量
     */
    public int countCreatedToday(java.util.Date today);

    /**
     * 按工序统计WIP数量
     *
     * @param processId 工序ID
     * @return 数量
     */
    public int countWipByProcess(Long processId);

    /**
     * 按工序统计WIP总件数
     *
     * @param processId 工序ID
     * @return 总件数
     */
    public int sumWipQtyByProcess(Long processId);

    /**
     * 查询工序日均产量
     *
     * @param processId 工序ID
     * @param start 开始日期
     * @param end 结束日期
     * @return 日均产量
     */
    public Integer selectDailyAvgByProcess(@Param("processId") Long processId,
                                           @Param("start") java.util.Date start,
                                           @Param("end") java.util.Date end);

    /**
     * 查询员工月度产量排名
     *
     * @param start 开始日期
     * @param end 结束日期
     * @return 排名列表
     */
    public List<com.ruoyi.erp.domain.vo.EmployeeRankVO> selectEmployeeMonthlyRank(
            @Param("start") java.util.Date start,
            @Param("end") java.util.Date end);
}
