package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.SampleNotice;

/**
 * 打样通知Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface ISampleNoticeService {
    /**
     * 查询打样通知
     *
     * @param id 打样通知主键
     * @return 打样通知
     */
    SampleNotice selectSampleNoticeById(Long id);

    /**
     * 查询打样通知列表
     *
     * @param sampleNotice 打样通知
     * @return 打样通知集合
     */
    List<SampleNotice> selectSampleNoticeList(SampleNotice sampleNotice);

    /**
     * 新增打样通知
     *
     * @param sampleNotice 打样通知
     * @return 结果
     */
    int insertSampleNotice(SampleNotice sampleNotice);

    /**
     * 修改打样通知
     *
     * @param sampleNotice 打样通知
     * @return 结果
     */
    int updateSampleNotice(SampleNotice sampleNotice);

    /**
     * 批量删除打样通知
     *
     * @param ids 需要删除的打样通知主键集合
     * @return 结果
     */
    int deleteSampleNoticeByIds(Long[] ids);

    /**
     * 删除打样通知信息
     *
     * @param id 打样通知主键
     * @return 结果
     */
    int deleteSampleNoticeById(Long id);

    /**
     * 新增打样通知批量
     *
     * @param list 打样通知
     * @return 结果
     */
    int insertSampleNoticeBatch(List<SampleNotice> list);
}
