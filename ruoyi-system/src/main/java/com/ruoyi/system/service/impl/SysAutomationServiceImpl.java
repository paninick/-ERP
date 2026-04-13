package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.SysAutomation;
import com.ruoyi.system.mapper.SysAutomationMapper;
import com.ruoyi.system.service.ISysAutomationService;

/**
 * 自动化任务Service实现
 * 
 * @author ruoyi
 * @date 2026-04-02
 */
@Service
public class SysAutomationServiceImpl implements ISysAutomationService
{
    @Autowired
    private SysAutomationMapper sysAutomationMapper;

    /**
     * 查询自动化任务
     * 
     * @param taskId 自动化任务ID
     * @return 自动化任务
     */
    @Override
    public SysAutomation selectSysAutomationById(Long taskId)
    {
        return sysAutomationMapper.selectSysAutomationById(taskId);
    }

    /**
     * 查询自动化任务列表
     * 
     * @param sysAutomation 自动化任务
     * @return 自动化任务集合
     */
    @Override
    public List<SysAutomation> selectSysAutomationList(SysAutomation sysAutomation)
    {
        return sysAutomationMapper.selectSysAutomationList(sysAutomation);
    }

    /**
     * 新增自动化任务
     * 
     * @param sysAutomation 自动化任务
     * @return 结果
     */
    @Override
    public int insertSysAutomation(SysAutomation sysAutomation)
    {
        sysAutomation.setCreateTime(DateUtils.getNowDate());
        return sysAutomationMapper.insertSysAutomation(sysAutomation);
    }

    /**
     * 修改自动化任务
     * 
     * @param sysAutomation 自动化任务
     * @return 结果
     */
    @Override
    public int updateSysAutomation(SysAutomation sysAutomation)
    {
        sysAutomation.setUpdateTime(DateUtils.getNowDate());
        return sysAutomationMapper.updateSysAutomation(sysAutomation);
    }

    /**
     * 删除自动化任务
     * 
     * @param taskId 自动化任务ID
     * @return 结果
     */
    @Override
    public int deleteSysAutomationById(Long taskId)
    {
        return sysAutomationMapper.deleteSysAutomationById(taskId);
    }

    /**
     * 批量删除自动化任务
     * 
     * @param taskIds 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysAutomationByIds(Long[] taskIds)
    {
        return sysAutomationMapper.deleteSysAutomationByIds(taskIds);
    }

    /**
     * 运行自动化任务
     * 
     * @param taskId 自动化任务ID
     * @return 结果
     */
    @Override
    public int runSysAutomation(Long taskId)
    {
        SysAutomation automation = new SysAutomation();
        automation.setTaskId(taskId);
        automation.setStatus("running");
        automation.setExecuteTime(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD_HH_MM_SS));
        automation.setUpdateTime(DateUtils.getNowDate());
        return sysAutomationMapper.updateSysAutomation(automation);
    }

    /**
     * 暂停自动化任务
     * 
     * @param taskId 自动化任务ID
     * @return 结果
     */
    @Override
    public int pauseSysAutomation(Long taskId)
    {
        SysAutomation automation = new SysAutomation();
        automation.setTaskId(taskId);
        automation.setStatus("paused");
        automation.setUpdateTime(DateUtils.getNowDate());
        return sysAutomationMapper.updateSysAutomation(automation);
    }
}
