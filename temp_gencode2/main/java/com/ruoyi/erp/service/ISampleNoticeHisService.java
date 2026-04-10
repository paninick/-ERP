package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.SampleNoticeHis;

/**
 * 打样通知历史Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
public interface ISampleNoticeHisService {
    /**
     * 查询打样通知历史
     *
     * @param id 打样通知历史主键
     * @return 打样通知历史
     */
    SampleNoticeHis selectSampleNoticeHisById(Long id);

    /**
     * 查询打样通知历史列表
     *
     * @param sampleNoticeHis 打样通知历史
     * @return 打样通知历史集合
     */
    List<SampleNoticeHis> selectSampleNoticeHisList(SampleNoticeHis sampleNoticeHis);

    /**
     * 新增打样通知历史
     *
     * @param sampleNoticeHis 打样通知历史
     * @return 结果
     */
    int insertSampleNoticeHis(SampleNoticeHis sampleNoticeHis);

    /**
     * 修改打样通知历史
     *
     * @param sampleNoticeHis 打样通知历史
     * @return 结果
     */
    int updateSampleNoticeHis(SampleNoticeHis sampleNoticeHis);

    /**
     * 批量删除打样通知历史
     *
     * @param ids 需要删除的打样通知历史主键集合
     * @return 结果
     */
    int deleteSampleNoticeHisByIds(Long[] ids);

    /**
     * 删除打样通知历史信息
     *
     * @param id 打样通知历史主键
     * @return 结果
     */
    int deleteSampleNoticeHisById(Long id);

    /**
     * 新增打样通知历史批量
     *
     * @param list 打样通知历史
     * @return 结果
     */
    int insertSampleNoticeHisBatch(List<SampleNoticeHis> list);
}
