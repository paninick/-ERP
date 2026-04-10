package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.SampleNoticeDetail;

/**
 * 打样通知-样衣要求Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
public interface ISampleNoticeDetailService {
    /**
     * 查询打样通知-样衣要求
     *
     * @param id 打样通知-样衣要求主键
     * @return 打样通知-样衣要求
     */
    SampleNoticeDetail selectSampleNoticeDetailById(Long id);

    /**
     * 查询打样通知-样衣要求列表
     *
     * @param sampleNoticeDetail 打样通知-样衣要求
     * @return 打样通知-样衣要求集合
     */
    List<SampleNoticeDetail> selectSampleNoticeDetailList(SampleNoticeDetail sampleNoticeDetail);

    /**
     * 新增打样通知-样衣要求
     *
     * @param sampleNoticeDetail 打样通知-样衣要求
     * @return 结果
     */
    int insertSampleNoticeDetail(SampleNoticeDetail sampleNoticeDetail);

    /**
     * 修改打样通知-样衣要求
     *
     * @param sampleNoticeDetail 打样通知-样衣要求
     * @return 结果
     */
    int updateSampleNoticeDetail(SampleNoticeDetail sampleNoticeDetail);

    /**
     * 批量删除打样通知-样衣要求
     *
     * @param ids 需要删除的打样通知-样衣要求主键集合
     * @return 结果
     */
    int deleteSampleNoticeDetailByIds(Long[] ids);

    /**
     * 删除打样通知-样衣要求信息
     *
     * @param id 打样通知-样衣要求主键
     * @return 结果
     */
    int deleteSampleNoticeDetailById(Long id);

    /**
     * 新增打样通知-样衣要求批量
     *
     * @param list 打样通知-样衣要求
     * @return 结果
     */
    int insertSampleNoticeDetailBatch(List<SampleNoticeDetail> list);
}
