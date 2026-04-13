package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.SampleTechCost;

/**
 * 工艺书单耗Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface SampleTechCostMapper {
    /**
     * 查询工艺书单耗
     *
     * @param id 工艺书单耗主键
     * @return 工艺书单耗
     */
    SampleTechCost selectSampleTechCostById(Long id);

    /**
     * 查询工艺书单耗列表
     *
     * @param sampleTechCost 工艺书单耗
     * @return 工艺书单耗集合
     */
    List<SampleTechCost> selectSampleTechCostList(SampleTechCost sampleTechCost);

    /**
     * 新增工艺书单耗
     *
     * @param sampleTechCost 工艺书单耗
     * @return 结果
     */
    int insertSampleTechCost(SampleTechCost sampleTechCost);

    /**
     * 修改工艺书单耗
     *
     * @param sampleTechCost 工艺书单耗
     * @return 结果
     */
    int updateSampleTechCost(SampleTechCost sampleTechCost);

    /**
     * 删除工艺书单耗
     *
     * @param id 工艺书单耗主键
     * @return 结果
     */
    int deleteSampleTechCostById(Long id);

    /**
     * 批量删除工艺书单耗
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteSampleTechCostByIds(Long[] ids);

    /**
     * 批量新增工艺书单耗批量
     *
     * @param list 工艺书单耗
     * @return 结果
     */
    int insertSampleTechCostBatch(List<SampleTechCost> list);
}
