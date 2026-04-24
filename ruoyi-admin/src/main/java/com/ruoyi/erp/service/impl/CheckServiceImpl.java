package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.CheckMapper;
import com.ruoyi.erp.domain.Check;
import com.ruoyi.erp.service.ICheckService;

/**
 * 大货核版Service业务层处理
 *
 * @author ruoyi
 * @date 2026-04-10
 */
@Service
public class CheckServiceImpl implements ICheckService
{
    @Autowired
    private CheckMapper checkMapper;

    /**
     * 查询大货核版
     *
     * @param checkId 大货核版主键
     * @return 大货核版
     */
    @Override
    public Check selectCheckByCheckId(Long checkId)
    {
        return checkMapper.selectCheckByCheckId(checkId);
    }

    /**
     * 查询大货核版列表
     *
     * @param check 大货核版
     * @return 大货核版
     */
    @Override
    public List<Check> selectCheckList(Check check)
    {
        return checkMapper.selectCheckList(check);
    }

    /**
     * 新增大货核版
     *
     * @param check 大货核版
     * @return 结果
     */
    @Override
    public int insertCheck(Check check)
    {
        check.setCreateBy(SecurityUtils.getUserId().toString());
        check.setCreateTime(DateUtils.getNowDate());
        return checkMapper.insertCheck(check);
    }

    /**
     * 修改大货核版
     *
     * @param check 大货核版
     * @return 结果
     */
    @Override
    public int updateCheck(Check check)
    {
        check.setUpdateTime(DateUtils.getNowDate());
        return checkMapper.updateCheck(check);
    }

    /**
     * 批量删除大货核版
     *
     * @param checkIds 需要删除的大货核版主键
     * @return 结果
     */
    @Override
    public int deleteCheckByIds(Long[] checkIds)
    {
        return checkMapper.deleteCheckByCheckIds(checkIds);
    }

    /**
     * 删除大货核版信息
     *
     * @param checkId 大货核版主键
     * @return 结果
     */
    @Override
    public int deleteCheckByCheckId(Long checkId)
    {
        return checkMapper.deleteCheckByCheckId(checkId);
    }

    /**
     * 审批大货核版
     *
     * @param check 大货核版
     * @return 结果
     */
    @Override
    public int approveCheck(Check check)
    {
        check.setAuditBy(SecurityUtils.getUserId());
        check.setAuditByName(SecurityUtils.getUsername());
        check.setAuditTime(DateUtils.getNowDate());
        check.setUpdateTime(DateUtils.getNowDate());
        return checkMapper.updateCheck(check);
    }

    /**
     * 导入大货核版数据
     *
     * @param checkList 大货核版数据列表
     * @param updateSupport 是否更新支持，如果已存在，则进行更新
     * @return 结果
     */
    @Override
    public String importCheck(List<Check> checkList, boolean updateSupport)
    {
        if (checkList == null || checkList.isEmpty())
        {
            throw new IllegalArgumentException("导入大货核版数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        int skipNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (Check check : checkList)
        {
            try
            {
                boolean isEmptyRecord = check == null;
                if (!isEmptyRecord) {
                    boolean hasCheckNo = check.getCheckNo() != null && !check.getCheckNo().trim().isEmpty();
                    boolean hasStyleNo = check.getStyleCode() != null && !check.getStyleCode().trim().isEmpty();
                    boolean hasCustomerName = check.getCustomerName() != null && !check.getCustomerName().trim().isEmpty();
                    isEmptyRecord = !hasCheckNo && !hasStyleNo && !hasCustomerName;
                }

                if (isEmptyRecord)
                {
                    skipNum++;
                    continue;
                }

                Check existingCheck = null;
                if (check.getCheckNo() != null && !check.getCheckNo().trim().isEmpty()) {
                    existingCheck = checkMapper.selectCheckByCheckNo(check.getCheckNo());
                }

                if (existingCheck != null)
                {
                    if (updateSupport)
                    {
                        check.setCheckId(existingCheck.getCheckId());
                        checkMapper.updateCheck(check);
                        successNum++;
                        successMsg.append("\n").append(successNum).append("、核版编号 " + check.getCheckNo() + " 更新成功");
                    }
                    else
                    {
                        failureNum++;
                        failureMsg.append("\n").append(failureNum).append("、核版编号 " + check.getCheckNo() + " 已存在");
                    }
                }
                else
                {
                    checkMapper.insertCheck(check);
                    successNum++;
                    successMsg.append("\n").append(successNum).append("、核版编号 " + check.getCheckNo() + " 导入成功");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String checkNo = (check != null && check.getCheckNo() != null) ? check.getCheckNo() : "空";
                String msg = "\n" + failureNum + "、核版编号 " + checkNo + " 导入失败：";
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
