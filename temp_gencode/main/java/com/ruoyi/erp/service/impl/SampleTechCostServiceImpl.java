package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.SampleTechCostMapper;
import com.ruoyi.erp.domain.SampleTechCost;
import com.ruoyi.erp.service.ISampleTechCostService;

/**
 * 工艺书单耗Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
@RequiredArgsConstructor
@Service
public class SampleTechCostServiceImpl implements ISampleTechCostService {
    private final SampleTechCostMapper sampleTechCostMapper;

    /**
     * 查询工艺书单耗
     *
     * @param id 工艺书单耗主键
     * @return 工艺书单耗
     */
    @Override
    public SampleTechCost selectSampleTechCostById(Long id) {
        return sampleTechCostMapper.selectSampleTechCostById(id);
    }

    /**
     * 查询工艺书单耗列表
     *
     * @param sampleTechCost 工艺书单耗
     * @return 工艺书单耗
     */
    @Override
    public List<SampleTechCost> selectSampleTechCostList(SampleTechCost sampleTechCost) {
        return sampleTechCostMapper.selectSampleTechCostList(sampleTechCost);
    }

    /**
     * 新增工艺书单耗
     *
     * @param sampleTechCost 工艺书单耗
     * @return 结果
     */
    @Override
    public int insertSampleTechCost(SampleTechCost sampleTechCost) {
        sampleTechCost.setCreateBy(SecurityUtils.getUserIdStr());
        sampleTechCost.setCreateTime(DateUtils.getNowDate());
        return sampleTechCostMapper.insertSampleTechCost(sampleTechCost);
    }

    /**
     * 修改工艺书单耗
     *
     * @param sampleTechCost 工艺书单耗
     * @return 结果
     */
    @Override
    public int updateSampleTechCost(SampleTechCost sampleTechCost) {
        sampleTechCost.setUpdateTime(DateUtils.getNowDate());
        return sampleTechCostMapper.updateSampleTechCost(sampleTechCost);
    }

    /**
     * 批量删除工艺书单耗
     *
     * @param ids 需要删除的工艺书单耗主键
     * @return 结果
     */
    @Override
    public int deleteSampleTechCostByIds(Long[] ids) {
        return sampleTechCostMapper.deleteSampleTechCostByIds(ids);
    }

    /**
     * 删除工艺书单耗信息
     *
     * @param id 工艺书单耗主键
     * @return 结果
     */
    @Override
    public int deleteSampleTechCostById(Long id) {
        return sampleTechCostMapper.deleteSampleTechCostById(id);
    }

    /**
     * 批量新增工艺书单耗
     *
     * @param list 工艺书单耗
     * @return 结果
     */
    @Override
    public int insertSampleTechCostBatch(List<SampleTechCost> list) {
        return sampleTechCostMapper.insertSampleTechCostBatch(list);
    }
}
