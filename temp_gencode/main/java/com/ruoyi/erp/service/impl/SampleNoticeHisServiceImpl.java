package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.SampleNoticeHisMapper;
import com.ruoyi.erp.domain.SampleNoticeHis;
import com.ruoyi.erp.service.ISampleNoticeHisService;

/**
 * 打样通知历史Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
@RequiredArgsConstructor
@Service
public class SampleNoticeHisServiceImpl implements ISampleNoticeHisService {
    private final SampleNoticeHisMapper sampleNoticeHisMapper;

    /**
     * 查询打样通知历史
     *
     * @param id 打样通知历史主键
     * @return 打样通知历史
     */
    @Override
    public SampleNoticeHis selectSampleNoticeHisById(Long id) {
        return sampleNoticeHisMapper.selectSampleNoticeHisById(id);
    }

    /**
     * 查询打样通知历史列表
     *
     * @param sampleNoticeHis 打样通知历史
     * @return 打样通知历史
     */
    @Override
    public List<SampleNoticeHis> selectSampleNoticeHisList(SampleNoticeHis sampleNoticeHis) {
        return sampleNoticeHisMapper.selectSampleNoticeHisList(sampleNoticeHis);
    }

    /**
     * 新增打样通知历史
     *
     * @param sampleNoticeHis 打样通知历史
     * @return 结果
     */
    @Override
    public int insertSampleNoticeHis(SampleNoticeHis sampleNoticeHis) {
        sampleNoticeHis.setCreateBy(SecurityUtils.getUserIdStr());
        sampleNoticeHis.setCreateTime(DateUtils.getNowDate());
        return sampleNoticeHisMapper.insertSampleNoticeHis(sampleNoticeHis);
    }

    /**
     * 修改打样通知历史
     *
     * @param sampleNoticeHis 打样通知历史
     * @return 结果
     */
    @Override
    public int updateSampleNoticeHis(SampleNoticeHis sampleNoticeHis) {
        sampleNoticeHis.setUpdateTime(DateUtils.getNowDate());
        return sampleNoticeHisMapper.updateSampleNoticeHis(sampleNoticeHis);
    }

    /**
     * 批量删除打样通知历史
     *
     * @param ids 需要删除的打样通知历史主键
     * @return 结果
     */
    @Override
    public int deleteSampleNoticeHisByIds(Long[] ids) {
        return sampleNoticeHisMapper.deleteSampleNoticeHisByIds(ids);
    }

    /**
     * 删除打样通知历史信息
     *
     * @param id 打样通知历史主键
     * @return 结果
     */
    @Override
    public int deleteSampleNoticeHisById(Long id) {
        return sampleNoticeHisMapper.deleteSampleNoticeHisById(id);
    }

    /**
     * 批量新增打样通知历史
     *
     * @param list 打样通知历史
     * @return 结果
     */
    @Override
    public int insertSampleNoticeHisBatch(List<SampleNoticeHis> list) {
        return sampleNoticeHisMapper.insertSampleNoticeHisBatch(list);
    }
}
