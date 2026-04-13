package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.SampleNoticeDetailMapper;
import com.ruoyi.erp.domain.SampleNoticeDetail;
import com.ruoyi.erp.service.ISampleNoticeDetailService;

/**
 * 打样通知-样衣要求Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@Service
public class SampleNoticeDetailServiceImpl implements ISampleNoticeDetailService {
    @Autowired
    private SampleNoticeDetailMapper sampleNoticeDetailMapper;

    /**
     * 查询打样通知-样衣要求
     *
     * @param id 打样通知-样衣要求主键
     * @return 打样通知-样衣要求
     */
    @Override
    public SampleNoticeDetail selectSampleNoticeDetailById(Long id) {
        return sampleNoticeDetailMapper.selectSampleNoticeDetailById(id);
    }

    /**
     * 查询打样通知-样衣要求列表
     *
     * @param sampleNoticeDetail 打样通知-样衣要求
     * @return 打样通知-样衣要求
     */
    @Override
    public List<SampleNoticeDetail> selectSampleNoticeDetailList(SampleNoticeDetail sampleNoticeDetail) {
        return sampleNoticeDetailMapper.selectSampleNoticeDetailList(sampleNoticeDetail);
    }

    /**
     * 新增打样通知-样衣要求
     *
     * @param sampleNoticeDetail 打样通知-样衣要求
     * @return 结果
     */
    @Override
    public int insertSampleNoticeDetail(SampleNoticeDetail sampleNoticeDetail) {
        sampleNoticeDetail.setCreateBy(SecurityUtils.getUserId().toString());
        sampleNoticeDetail.setCreateTime(DateUtils.getNowDate());
        return sampleNoticeDetailMapper.insertSampleNoticeDetail(sampleNoticeDetail);
    }

    /**
     * 修改打样通知-样衣要求
     *
     * @param sampleNoticeDetail 打样通知-样衣要求
     * @return 结果
     */
    @Override
    public int updateSampleNoticeDetail(SampleNoticeDetail sampleNoticeDetail) {
        sampleNoticeDetail.setUpdateTime(DateUtils.getNowDate());
        return sampleNoticeDetailMapper.updateSampleNoticeDetail(sampleNoticeDetail);
    }

    /**
     * 批量删除打样通知-样衣要求
     *
     * @param ids 需要删除的打样通知-样衣要求主键
     * @return 结果
     */
    @Override
    public int deleteSampleNoticeDetailByIds(Long[] ids) {
        return sampleNoticeDetailMapper.deleteSampleNoticeDetailByIds(ids);
    }

    /**
     * 删除打样通知-样衣要求信息
     *
     * @param id 打样通知-样衣要求主键
     * @return 结果
     */
    @Override
    public int deleteSampleNoticeDetailById(Long id) {
        return sampleNoticeDetailMapper.deleteSampleNoticeDetailById(id);
    }

    /**
     * 批量新增打样通知-样衣要求
     *
     * @param list 打样通知-样衣要求
     * @return 结果
     */
    @Override
    public int insertSampleNoticeDetailBatch(List<SampleNoticeDetail> list) {
        return sampleNoticeDetailMapper.insertSampleNoticeDetailBatch(list);
    }
}
