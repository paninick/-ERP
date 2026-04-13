package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.SampleNoticeMapper;
import com.ruoyi.erp.domain.SampleNotice;
import com.ruoyi.erp.service.ISampleNoticeService;

/**
 * 打样通知Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@Service
public class SampleNoticeServiceImpl implements ISampleNoticeService {
    @Autowired
    private SampleNoticeMapper sampleNoticeMapper;

    /**
     * 查询打样通知
     *
     * @param id 打样通知主键
     * @return 打样通知
     */
    @Override
    public SampleNotice selectSampleNoticeById(Long id) {
        return sampleNoticeMapper.selectSampleNoticeById(id);
    }

    /**
     * 查询打样通知列表
     *
     * @param sampleNotice 打样通知
     * @return 打样通知
     */
    @Override
    public List<SampleNotice> selectSampleNoticeList(SampleNotice sampleNotice) {
        return sampleNoticeMapper.selectSampleNoticeList(sampleNotice);
    }

    /**
     * 新增打样通知
     *
     * @param sampleNotice 打样通知
     * @return 结果
     */
    @Override
    public int insertSampleNotice(SampleNotice sampleNotice) {
        sampleNotice.setCreateBy(SecurityUtils.getUserId().toString());
        sampleNotice.setCreateTime(DateUtils.getNowDate());
        return sampleNoticeMapper.insertSampleNotice(sampleNotice);
    }

    /**
     * 修改打样通知
     *
     * @param sampleNotice 打样通知
     * @return 结果
     */
    @Override
    public int updateSampleNotice(SampleNotice sampleNotice) {
        sampleNotice.setUpdateTime(DateUtils.getNowDate());
        return sampleNoticeMapper.updateSampleNotice(sampleNotice);
    }

    /**
     * 批量删除打样通知
     *
     * @param ids 需要删除的打样通知主键
     * @return 结果
     */
    @Override
    public int deleteSampleNoticeByIds(Long[] ids) {
        return sampleNoticeMapper.deleteSampleNoticeByIds(ids);
    }

    /**
     * 删除打样通知信息
     *
     * @param id 打样通知主键
     * @return 结果
     */
    @Override
    public int deleteSampleNoticeById(Long id) {
        return sampleNoticeMapper.deleteSampleNoticeById(id);
    }

    /**
     * 批量新增打样通知
     *
     * @param list 打样通知
     * @return 结果
     */
    @Override
    public int insertSampleNoticeBatch(List<SampleNotice> list) {
        return sampleNoticeMapper.insertSampleNoticeBatch(list);
    }
}
