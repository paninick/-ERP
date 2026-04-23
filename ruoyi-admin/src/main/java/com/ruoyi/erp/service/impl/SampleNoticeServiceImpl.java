package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.SampleNoticeMapper;
import com.ruoyi.erp.domain.SampleNotice;
import com.ruoyi.erp.service.ISampleNoticeService;
import com.ruoyi.erp.utils.BillNoGenerator;

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

    @Autowired
    private BillNoGenerator billNoGenerator;

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
        if (sampleNotice.getSampleNo() == null || sampleNotice.getSampleNo().isEmpty()) {
            sampleNotice.setSampleNo(billNoGenerator.generate("SN"));
        }
        if (sampleNotice.getStyleCode() == null || sampleNotice.getStyleCode().isEmpty()) {
            sampleNotice.setStyleCode(billNoGenerator.generateStyleNo(null));
        }
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

    @Override
    public String importSampleNotice(List<SampleNotice> list, boolean updateSupport) {
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("导入打样通知数据不能为空");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SampleNotice sampleNotice : list) {
            try {
                insertSampleNotice(sampleNotice);
                successNum++;
                successMsg.append("\n").append(successNum).append("、打样通知 ")
                        .append(sampleNotice.getSampleNo()).append(" 导入成功");
            } catch (Exception e) {
                failureNum++;
                failureMsg.append("\n").append(failureNum).append("、打样通知导入失败：")
                        .append(e.getMessage());
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new RuntimeException(failureMsg.toString());
        }
        successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条。");
        return successMsg.toString();
    }
}
