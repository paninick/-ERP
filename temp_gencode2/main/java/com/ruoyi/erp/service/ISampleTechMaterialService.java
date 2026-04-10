package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.SampleTechMaterial;

/**
 * 工艺书面料信息Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
public interface ISampleTechMaterialService {
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
     * 批量删除工艺书面料信息
     *
     * @param ids 需要删除的工艺书面料信息主键集合
     * @return 结果
     */
    int deleteSampleTechMaterialByIds(Long[] ids);

    /**
     * 删除工艺书面料信息信息
     *
     * @param id 工艺书面料信息主键
     * @return 结果
     */
    int deleteSampleTechMaterialById(Long id);

    /**
     * 新增工艺书面料信息批量
     *
     * @param list 工艺书面料信息
     * @return 结果
     */
    int insertSampleTechMaterialBatch(List<SampleTechMaterial> list);
}
