package com.ruoyi.erp.service;

import com.ruoyi.erp.domain.BizAbnormalPool;

import java.util.List;

/**
 * 业务异常池Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public interface IBizAbnormalPoolService {
    /**
     * 查询业务异常池
     *
     * @param id 业务异常池ID
     * @return 业务异常池
     */
    public BizAbnormalPool selectBizAbnormalPoolById(Long id);

    /**
     * 查询业务异常池列表
     *
     * @param bizAbnormalPool 业务异常池
     * @return 业务异常池集合
     */
    public List<BizAbnormalPool> selectBizAbnormalPoolList(BizAbnormalPool bizAbnormalPool);

    /**
     * 新增业务异常池
     *
     * @param bizAbnormalPool 业务异常池
     * @return 结果
     */
    public int insertBizAbnormalPool(BizAbnormalPool bizAbnormalPool);

    /**
     * 修改业务异常池
     *
     * @param bizAbnormalPool 业务异常池
     * @return 结果
     */
    public int updateBizAbnormalPool(BizAbnormalPool bizAbnormalPool);

    /**
     * 删除业务异常池信息
     *
     * @param id 业务异常池ID
     * @return 结果
     */
    public int deleteBizAbnormalPoolById(Long id);

    /**
     * 批量删除业务异常池
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizAbnormalPoolByIds(Long[] ids);

    /**
     * 检查业务是否存在未处理异常
     *
     * @param bizType 业务类型
     * @param bizId 业务ID
     * @return true 存在未处理异常，false 不存在
     */
    public boolean hasUnhandledAbnormal(String bizType, Long bizId);
}
