package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.SampleTech;

/**
 * 工艺指示书Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface ISampleTechService {
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
     * 批量删除工艺指示书
     *
     * @param ids 需要删除的工艺指示书主键集合
     * @return 结果
     */
    int deleteSampleTechByIds(Long[] ids);

    /**
     * 删除工艺指示书信息
     *
     * @param id 工艺指示书主键
     * @return 结果
     */
    int deleteSampleTechById(Long id);

    /**
     * 新增工艺指示书批量
     *
     * @param list 工艺指示书
     * @return 结果
     */
    int insertSampleTechBatch(List<SampleTech> list);

    /**
     * 导入工艺指示书数据
     *
     * @param sampleTechList 工艺指示书数据列表
     * @param updateSupport 是否更新支持，如果已存在，则进行更新
     * @return 结果
     */
    String importSampleTech(List<SampleTech> sampleTechList, boolean updateSupport);
}
