package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.SampleTechMaterialMapper;
import com.ruoyi.erp.domain.SampleTechMaterial;
import com.ruoyi.erp.service.ISampleTechMaterialService;

/**
 * 工艺书面料信息Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@Service
public class SampleTechMaterialServiceImpl implements ISampleTechMaterialService {
    @Autowired
    private SampleTechMaterialMapper sampleTechMaterialMapper;

    /**
     * 查询工艺书面料信息
     *
     * @param id 工艺书面料信息主键
     * @return 工艺书面料信息
     */
    @Override
    public SampleTechMaterial selectSampleTechMaterialById(Long id) {
        return sampleTechMaterialMapper.selectSampleTechMaterialById(id);
    }

    /**
     * 查询工艺书面料信息列表
     *
     * @param sampleTechMaterial 工艺书面料信息
     * @return 工艺书面料信息
     */
    @Override
    public List<SampleTechMaterial> selectSampleTechMaterialList(SampleTechMaterial sampleTechMaterial) {
        return sampleTechMaterialMapper.selectSampleTechMaterialList(sampleTechMaterial);
    }

    /**
     * 新增工艺书面料信息
     *
     * @param sampleTechMaterial 工艺书面料信息
     * @return 结果
     */
    @Override
    public int insertSampleTechMaterial(SampleTechMaterial sampleTechMaterial) {
        sampleTechMaterial.setCreateBy(SecurityUtils.getUserId().toString());
        sampleTechMaterial.setCreateTime(DateUtils.getNowDate());
        return sampleTechMaterialMapper.insertSampleTechMaterial(sampleTechMaterial);
    }

    /**
     * 修改工艺书面料信息
     *
     * @param sampleTechMaterial 工艺书面料信息
     * @return 结果
     */
    @Override
    public int updateSampleTechMaterial(SampleTechMaterial sampleTechMaterial) {
        sampleTechMaterial.setUpdateTime(DateUtils.getNowDate());
        return sampleTechMaterialMapper.updateSampleTechMaterial(sampleTechMaterial);
    }

    /**
     * 批量删除工艺书面料信息
     *
     * @param ids 需要删除的工艺书面料信息主键
     * @return 结果
     */
    @Override
    public int deleteSampleTechMaterialByIds(Long[] ids) {
        return sampleTechMaterialMapper.deleteSampleTechMaterialByIds(ids);
    }

    /**
     * 删除工艺书面料信息信息
     *
     * @param id 工艺书面料信息主键
     * @return 结果
     */
    @Override
    public int deleteSampleTechMaterialById(Long id) {
        return sampleTechMaterialMapper.deleteSampleTechMaterialById(id);
    }

    /**
     * 批量新增工艺书面料信息
     *
     * @param list 工艺书面料信息
     * @return 结果
     */
    @Override
    public int insertSampleTechMaterialBatch(List<SampleTechMaterial> list) {
        return sampleTechMaterialMapper.insertSampleTechMaterialBatch(list);
    }
}
