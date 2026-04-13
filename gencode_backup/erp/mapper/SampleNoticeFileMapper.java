package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.SampleNoticeFile;

/**
 * 打样通知-文件附录Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface SampleNoticeFileMapper {
    /**
     * 查询打样通知-文件附录
     *
     * @param id 打样通知-文件附录主键
     * @return 打样通知-文件附录
     */
    SampleNoticeFile selectSampleNoticeFileById(Long id);

    /**
     * 查询打样通知-文件附录列表
     *
     * @param sampleNoticeFile 打样通知-文件附录
     * @return 打样通知-文件附录集合
     */
    List<SampleNoticeFile> selectSampleNoticeFileList(SampleNoticeFile sampleNoticeFile);

    /**
     * 新增打样通知-文件附录
     *
     * @param sampleNoticeFile 打样通知-文件附录
     * @return 结果
     */
    int insertSampleNoticeFile(SampleNoticeFile sampleNoticeFile);

    /**
     * 修改打样通知-文件附录
     *
     * @param sampleNoticeFile 打样通知-文件附录
     * @return 结果
     */
    int updateSampleNoticeFile(SampleNoticeFile sampleNoticeFile);

    /**
     * 删除打样通知-文件附录
     *
     * @param id 打样通知-文件附录主键
     * @return 结果
     */
    int deleteSampleNoticeFileById(Long id);

    /**
     * 批量删除打样通知-文件附录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteSampleNoticeFileByIds(Long[] ids);

    /**
     * 批量新增打样通知-文件附录批量
     *
     * @param list 打样通知-文件附录
     * @return 结果
     */
    int insertSampleNoticeFileBatch(List<SampleNoticeFile> list);
}
