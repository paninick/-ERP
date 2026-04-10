package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.SampleTechMaterial;

/**
 * 工艺书面料信息Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
public interface SampleTechMaterialMapper {
    /**
     * 查询工艺书面料信息
     *
     * @param id 工艺书面料信息主键
     * @return 工艺书面料信息
     */
    SampleTechMaterial selectSampleTechMaterialById(Long id);

    /**
     * 查询工艺书面料信息列表
     *
     * @param sampleTechMaterial 工艺书面料信息
     * @return 工艺书面料信息集合
     */
    List<SampleTechMaterial> selectSampleTechMaterialList(SampleTechMaterial sampleTechMaterial);

    /**
     * 新增工艺书面料信息
     *
     * @param sampleTechMaterial 工艺书面料信息
     * @return 结果
     */
    int insertSampleTechMaterial(SampleTechMaterial sampleTechMaterial);

    /**
     * 修改工艺书面料信息
     *
     * @param sampleTechMaterial 工艺书面料信息
     * @return 结果
     */
    int updateSampleTechMaterial(SampleTechMaterial sampleTechMaterial);

    /**
     * 删除工艺书面料信息
     *
     * @param id 工艺书面料信息主键
     * @return 结果
     */
    int deleteSampleTechMaterialById(Long id);

    /**
     * 批量删除工艺书面料信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteSampleTechMaterialByIds(Long[] ids);

    /**
     * 批量新增工艺书面料信息批量
     *
     * @param list 工艺书面料信息
     * @return 结果
     */
    int insertSampleTechMaterialBatch(List<SampleTechMaterial> list);
}
