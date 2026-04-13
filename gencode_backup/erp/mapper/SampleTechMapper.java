package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.SampleTech;

/**
 * 工艺指示书Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface SampleTechMapper {
    /**
     * 查询工艺指示书
     *
     * @param id 工艺指示书主键
     * @return 工艺指示书
     */
    SampleTech selectSampleTechById(Long id);

    /**
     * 查询工艺指示书列表
     *
     * @param sampleTech 工艺指示书
     * @return 工艺指示书集合
     */
    List<SampleTech> selectSampleTechList(SampleTech sampleTech);

    /**
     * 新增工艺指示书
     *
     * @param sampleTech 工艺指示书
     * @return 结果
     */
    int insertSampleTech(SampleTech sampleTech);

    /**
     * 修改工艺指示书
     *
     * @param sampleTech 工艺指示书
     * @return 结果
     */
    int updateSampleTech(SampleTech sampleTech);

    /**
     * 删除工艺指示书
     *
     * @param id 工艺指示书主键
     * @return 结果
     */
    int deleteSampleTechById(Long id);

    /**
     * 批量删除工艺指示书
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteSampleTechByIds(Long[] ids);

    /**
     * 批量新增工艺指示书批量
     *
     * @param list 工艺指示书
     * @return 结果
     */
    int insertSampleTechBatch(List<SampleTech> list);
}
