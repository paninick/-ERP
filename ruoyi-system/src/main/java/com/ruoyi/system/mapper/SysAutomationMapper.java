package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysAutomation;

/**
 * 自动化任务Mapper接口
 * 
 * @author ruoyi
 * @date 2026-04-02
 */
public interface SysAutomationMapper
{
    /**
     * 查询自动化任务
     * 
     * @param taskId 自动化任务ID
     * @return 自动化任务
     */
    public SysAutomation selectSysAutomationById(Long taskId);

    /**
     * 查询自动化任务列表
     * 
     * @param sysAutomation 自动化任务
     * @return 自动化任务集合
     */
    public List<SysAutomation> selectSysAutomationList(SysAutomation sysAutomation);

    /**
     * 新增自动化任务
     * 
     * @param sysAutomation 自动化任务
     * @return 结果
     */
    public int insertSysAutomation(SysAutomation sysAutomation);

    /**
     * 修改自动化任务
     * 
     * @param sysAutomation 自动化任务
     * @return 结果
     */
    public int updateSysAutomation(SysAutomation sysAutomation);

    /**
     * 删除自动化任务
     * 
     * @param taskId 自动化任务ID
     * @return 结果
     */
    public int deleteSysAutomationById(Long taskId);

    /**
     * 批量删除自动化任务
     * 
     * @param taskIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysAutomationByIds(Long[] taskIds);
}
