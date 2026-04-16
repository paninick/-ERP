package com.ruoyi.erp.mapper;

import com.ruoyi.erp.domain.ProduceMaterialConsume;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 生产物料消耗记录Mapper接口
 *
 * @author zhangmingjian
 */
@Mapper
public interface ProduceMaterialConsumeMapper {
    /**
     * 查询生产物料消耗记录
     *
     * @param id 生产物料消耗记录ID
     * @return 生产物料消耗记录
     */
    public ProduceMaterialConsume selectProduceMaterialConsumeById(Long id);

    /**
     * 查询生产物料消耗记录列表
     *
     * @param produceMaterialConsume 生产物料消耗记录
     * @return 生产物料消耗记录集合
     */
    public List<ProduceMaterialConsume> selectProduceMaterialConsumeList(ProduceMaterialConsume produceMaterialConsume);

    /**
     * 新增生产物料消耗记录
     *
     * @param produceMaterialConsume 生产物料消耗记录
     * @return 结果
     */
    public int insertProduceMaterialConsume(ProduceMaterialConsume produceMaterialConsume);

    /**
     * 修改生产物料消耗记录
     *
     * @param produceMaterialConsume 生产物料消耗记录
     * @return 结果
     */
    public int updateProduceMaterialConsume(ProduceMaterialConsume produceMaterialConsume);

    /**
     * 删除生产物料消耗记录
     *
     * @param id 生产物料消耗记录ID
     * @return 结果
     */
    public int deleteProduceMaterialConsumeById(Long id);

    /**
     * 批量删除生产物料消耗记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteProduceMaterialConsumeByIds(Long[] ids);

    /**
     * 根据生产计划ID汇总实际损耗
     *
     * @param producePlanId 生产计划ID
     * @return 总损耗
     */
    public BigDecimal sumActualLossByProducePlan(Long producePlanId);

    /**
     * 根据生产计划ID查询记录
     *
     * @param producePlanId 生产计划ID
     * @return 记录列表
     */
    public List<ProduceMaterialConsume> selectByProducePlanId(Long producePlanId);

    /**
     * 统计待审批数量
     *
     * @return 待审批数量
     */
    public int countPendingApproval();
}
