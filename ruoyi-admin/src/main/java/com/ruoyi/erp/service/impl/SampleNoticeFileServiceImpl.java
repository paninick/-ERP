package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.SampleNoticeFileMapper;
import com.ruoyi.erp.domain.SampleNoticeFile;
import com.ruoyi.erp.service.ISampleNoticeFileService;

/**
 * 打样通知-文件附录Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@Service
public class SampleNoticeFileServiceImpl implements ISampleNoticeFileService {
    @Autowired
    private SampleNoticeFileMapper sampleNoticeFileMapper;

    /**
     * 查询打样通知-文件附录
     *
     * @param id 打样通知-文件附录主键
     * @return 打样通知-文件附录
     */
    @Override
    public SampleNoticeFile selectSampleNoticeFileById(Long id) {
        return sampleNoticeFileMapper.selectSampleNoticeFileById(id);
    }

    /**
     * 查询打样通知-文件附录列表
     *
     * @param sampleNoticeFile 打样通知-文件附录
     * @return 打样通知-文件附录
     */
    @Override
    public List<SampleNoticeFile> selectSampleNoticeFileList(SampleNoticeFile sampleNoticeFile) {
        return sampleNoticeFileMapper.selectSampleNoticeFileList(sampleNoticeFile);
    }

    /**
     * 新增打样通知-文件附录
     *
     * @param sampleNoticeFile 打样通知-文件附录
     * @return 结果
     */
    @Override
    public int insertSampleNoticeFile(SampleNoticeFile sampleNoticeFile) {
        sampleNoticeFile.setCreateBy(SecurityUtils.getUserId().toString());
        sampleNoticeFile.setCreateTime(DateUtils.getNowDate());
        return sampleNoticeFileMapper.insertSampleNoticeFile(sampleNoticeFile);
    }

    /**
     * 修改打样通知-文件附录
     *
     * @param sampleNoticeFile 打样通知-文件附录
     * @return 结果
     */
    @Override
    public int updateSampleNoticeFile(SampleNoticeFile sampleNoticeFile) {
        sampleNoticeFile.setUpdateTime(DateUtils.getNowDate());
        return sampleNoticeFileMapper.updateSampleNoticeFile(sampleNoticeFile);
    }

    /**
     * 批量删除打样通知-文件附录
     *
     * @param ids 需要删除的打样通知-文件附录主键
     * @return 结果
     */
    @Override
    public int deleteSampleNoticeFileByIds(Long[] ids) {
        return sampleNoticeFileMapper.deleteSampleNoticeFileByIds(ids);
    }

    /**
     * 删除打样通知-文件附录信息
     *
     * @param id 打样通知-文件附录主键
     * @return 结果
     */
    @Override
    public int deleteSampleNoticeFileById(Long id) {
        return sampleNoticeFileMapper.deleteSampleNoticeFileById(id);
    }

    /**
     * 批量新增打样通知-文件附录
     *
     * @param list 打样通知-文件附录
     * @return 结果
     */
    @Override
    public int insertSampleNoticeFileBatch(List<SampleNoticeFile> list) {
        return sampleNoticeFileMapper.insertSampleNoticeFileBatch(list);
    }
}
