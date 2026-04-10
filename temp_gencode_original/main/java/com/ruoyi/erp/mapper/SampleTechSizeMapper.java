package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.SampleTechSize;

/**
 * 工艺书尺寸细节Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
public interface SampleTechSizeMapper {
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
     * 删除工艺书尺寸细节
     *
     * @param id 工艺书尺寸细节主键
     * @return 结果
     */
    int deleteSampleTechSizeById(Long id);

    /**
     * 批量删除工艺书尺寸细节
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteSampleTechSizeByIds(Long[] ids);

    /**
     * 批量新增工艺书尺寸细节批量
     *
     * @param list 工艺书尺寸细节
     * @return 结果
     */
    int insertSampleTechSizeBatch(List<SampleTechSize> list);
}
