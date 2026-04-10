package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.SampleTechMapper;
import com.ruoyi.erp.domain.SampleTech;
import com.ruoyi.erp.service.ISampleTechService;

/**
 * 工艺指示书Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
@RequiredArgsConstructor
@Service
public class SampleTechServiceImpl implements ISampleTechService {
    private final SampleTechMapper sampleTechMapper;

    /**
     * 查询工艺指示书
     *
     * @param id 工艺指示书主键
     * @return 工艺指示书
     */
    @Override
    public SampleTech selectSampleTechById(Long id) {
        return sampleTechMapper.selectSampleTechById(id);
    }

    /**
     * 查询工艺指示书列表
     *
     * @param sampleTech 工艺指示书
     * @return 工艺指示书
     */
    @Override
    public List<SampleTech> selectSampleTechList(SampleTech sampleTech) {
        return sampleTechMapper.selectSampleTechList(sampleTech);
    }

    /**
     * 新增工艺指示书
     *
     * @param sampleTech 工艺指示书
     * @return 结果
     */
    @Override
    public int insertSampleTech(SampleTech sampleTech) {
        sampleTech.setCreateBy(SecurityUtils.getUserIdStr());
        sampleTech.setCreateTime(DateUtils.getNowDate());
        return sampleTechMapper.insertSampleTech(sampleTech);
    }

    /**
     * 修改工艺指示书
     *
     * @param sampleTech 工艺指示书
     * @return 结果
     */
    @Override
    public int updateSampleTech(SampleTech sampleTech) {
        sampleTech.setUpdateTime(DateUtils.getNowDate());
        return sampleTechMapper.updateSampleTech(sampleTech);
    }

    /**
     * 批量删除工艺指示书
     *
     * @param ids 需要删除的工艺指示书主键
     * @return 结果
     */
    @Override
    public int deleteSampleTechByIds(Long[] ids) {
        return sampleTechMapper.deleteSampleTechByIds(ids);
    }

    /**
     * 删除工艺指示书信息
     *
     * @param id 工艺指示书主键
     * @return 结果
     */
    @Override
    public int deleteSampleTechById(Long id) {
        return sampleTechMapper.deleteSampleTechById(id);
    }

    /**
     * 批量新增工艺指示书
     *
     * @param list 工艺指示书
     * @return 结果
     */
    @Override
    public int insertSampleTechBatch(List<SampleTech> list) {
        return sampleTechMapper.insertSampleTechBatch(list);
    }
}
