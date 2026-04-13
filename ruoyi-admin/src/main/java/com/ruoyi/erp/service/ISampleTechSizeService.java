package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.SampleTechSize;

/**
 * 工艺书尺寸细节Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface ISampleTechSizeService {
    /**
     * 查询工艺书尺寸细节
     *
     * @param id 工艺书尺寸细节主键
     * @return 工艺书尺寸细节
     */
    SampleTechSize selectSampleTechSizeById(Long id);

    /**
     * 查询工艺书尺寸细节列表
     *
     * @param sampleTechSize 工艺书尺寸细节
     * @return 工艺书尺寸细节集合
     */
    List<SampleTechSize> selectSampleTechSizeList(SampleTechSize sampleTechSize);

    /**
     * 新增工艺书尺寸细节
     *
     * @param sampleTechSize 工艺书尺寸细节
     * @return 结果
     */
    int insertSampleTechSize(SampleTechSize sampleTechSize);

    /**
     * 修改工艺书尺寸细节
     *
     * @param sampleTechSize 工艺书尺寸细节
     * @return 结果
     */
    int updateSampleTechSize(SampleTechSize sampleTechSize);

    /**
     * 批量删除工艺书尺寸细节
     *
     * @param ids 需要删除的工艺书尺寸细节主键集合
     * @return 结果
     */
    int deleteSampleTechSizeByIds(Long[] ids);

    /**
     * 删除工艺书尺寸细节信息
     *
     * @param id 工艺书尺寸细节主键
     * @return 结果
     */
    int deleteSampleTechSizeById(Long id);

    /**
     * 新增工艺书尺寸细节批量
     *
     * @param list 工艺书尺寸细节
     * @return 结果
     */
    int insertSampleTechSizeBatch(List<SampleTechSize> list);
}
