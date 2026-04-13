package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.SampleTechMapper;
import com.ruoyi.erp.domain.SampleTech;
import com.ruoyi.erp.service.ISampleTechService;

/**
 * 工艺指示书Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@Service
public class SampleTechServiceImpl implements ISampleTechService {
    @Autowired
    private SampleTechMapper sampleTechMapper;

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
        sampleTech.setCreateBy(SecurityUtils.getUserId().toString());
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

    /**
     * 导入工艺指示书数据
     *
     * @param sampleTechList 工艺指示书数据列表
     * @param updateSupport 是否更新支持，如果已存在，则进行更新
     * @return 结果
     */
    @Override
    public String importSampleTech(List<SampleTech> sampleTechList, boolean updateSupport)
    {
        if (sampleTechList == null || sampleTechList.isEmpty())
        {
            throw new IllegalArgumentException("导入工艺指示书数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        int skipNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SampleTech sampleTech : sampleTechList)
        {
            try
            {
                boolean isEmptyRecord = sampleTech == null;
                if (!isEmptyRecord) {
                    boolean hasSampleType = sampleTech.getSampleType() != null && !sampleTech.getSampleType().trim().isEmpty();
                    boolean hasSampleId = sampleTech.getSampleId() != null;
                    boolean hasDueDate = sampleTech.getDueDate() != null;
                    isEmptyRecord = !hasSampleType && !hasSampleId && !hasDueDate;
                }
                
                if (isEmptyRecord)
                {
                    skipNum++;
                    continue;
                }
                
                SampleTech existingSampleTech = null;
                if (sampleTech.getSampleId() != null) {
                    existingSampleTech = sampleTechMapper.selectSampleTechById(sampleTech.getSampleId());
                }
                
                if (existingSampleTech != null)
                {
                    if (updateSupport)
                    {
                        sampleTech.setId(existingSampleTech.getId());
                        sampleTechMapper.updateSampleTech(sampleTech);
                        successNum++;
                        successMsg.append("\n").append(successNum).append("、打样id " + sampleTech.getSampleId() + " 更新成功");
                    }
                    else
                    {
                        failureNum++;
                        failureMsg.append("\n").append(failureNum).append("、打样id " + sampleTech.getSampleId() + " 已存在");
                    }
                }
                else
                {
                    sampleTechMapper.insertSampleTech(sampleTech);
                    successNum++;
                    successMsg.append("\n").append(successNum).append("、打样id " + sampleTech.getSampleId() + " 导入成功");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String sampleId = (sampleTech != null && sampleTech.getSampleId() != null) ? sampleTech.getSampleId().toString() : "空";
                String msg = "\n" + failureNum + "、打样id " + sampleId + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new IllegalArgumentException(failureMsg.toString());
        }
        else
        {
            String skipInfo = skipNum > 0 ? "（跳过空行 " + skipNum + " 条）" : "";
            successMsg.insert(0, "导入成功！共 " + successNum + " 条数据" + skipInfo + "，如下：");
        }
        return successMsg.toString();
    }
}
