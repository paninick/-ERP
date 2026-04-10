package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.SampleTechSizeMapper;
import com.ruoyi.erp.domain.SampleTechSize;
import com.ruoyi.erp.service.ISampleTechSizeService;

/**
 * 工艺书尺寸细节Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
@RequiredArgsConstructor
@Service
public class SampleTechSizeServiceImpl implements ISampleTechSizeService {
    private final SampleTechSizeMapper sampleTechSizeMapper;

    /**
     * 查询工艺书尺寸细节
     *
     * @param id 工艺书尺寸细节主键
     * @return 工艺书尺寸细节
     */
    @Override
    public SampleTechSize selectSampleTechSizeById(Long id) {
        return sampleTechSizeMapper.selectSampleTechSizeById(id);
    }

    /**
     * 查询工艺书尺寸细节列表
     *
     * @param sampleTechSize 工艺书尺寸细节
     * @return 工艺书尺寸细节
     */
    @Override
    public List<SampleTechSize> selectSampleTechSizeList(SampleTechSize sampleTechSize) {
        return sampleTechSizeMapper.selectSampleTechSizeList(sampleTechSize);
    }

    /**
     * 新增工艺书尺寸细节
     *
     * @param sampleTechSize 工艺书尺寸细节
     * @return 结果
     */
    @Override
    public int insertSampleTechSize(SampleTechSize sampleTechSize) {
        sampleTechSize.setCreateBy(SecurityUtils.getUserIdStr());
        sampleTechSize.setCreateTime(DateUtils.getNowDate());
        return sampleTechSizeMapper.insertSampleTechSize(sampleTechSize);
    }

    /**
     * 修改工艺书尺寸细节
     *
     * @param sampleTechSize 工艺书尺寸细节
     * @return 结果
     */
    @Override
    public int updateSampleTechSize(SampleTechSize sampleTechSize) {
        sampleTechSize.setUpdateTime(DateUtils.getNowDate());
        return sampleTechSizeMapper.updateSampleTechSize(sampleTechSize);
    }

    /**
     * 批量删除工艺书尺寸细节
     *
     * @param ids 需要删除的工艺书尺寸细节主键
     * @return 结果
     */
    @Override
    public int deleteSampleTechSizeByIds(Long[] ids) {
        return sampleTechSizeMapper.deleteSampleTechSizeByIds(ids);
    }

    /**
     * 删除工艺书尺寸细节信息
     *
     * @param id 工艺书尺寸细节主键
     * @return 结果
     */
    @Override
    public int deleteSampleTechSizeById(Long id) {
        return sampleTechSizeMapper.deleteSampleTechSizeById(id);
    }

    /**
     * 批量新增工艺书尺寸细节
     *
     * @param list 工艺书尺寸细节
     * @return 结果
     */
    @Override
    public int insertSampleTechSizeBatch(List<SampleTechSize> list) {
        return sampleTechSizeMapper.insertSampleTechSizeBatch(list);
    }
}
