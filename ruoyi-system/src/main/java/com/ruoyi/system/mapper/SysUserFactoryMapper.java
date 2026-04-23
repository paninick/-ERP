package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysUserFactory;

/**
 * 用户与工厂关联表 数据层
 * 
 * @author ruoyi
 */
public interface SysUserFactoryMapper
{
    /**
     * 通过用户ID删除用户和工厂关联
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserFactoryByUserId(Long userId);

    /**
     * 批量删除用户和工厂关联
     * 
     * @param userIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserFactory(Long[] userIds);

    /**
     * 查询用户关联的工厂ID集合
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public List<Long> selectFactoryListByUserId(Long userId);

    /**
     * 批量新增用户工厂信息
     * 
     * @param userFactoryList 用户工厂列表
     * @return 结果
     */
    public int batchUserFactory(List<SysUserFactory> userFactoryList);
}
