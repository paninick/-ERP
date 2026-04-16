package com.ruoyi.erp.service.impl;

import com.ruoyi.erp.domain.BizAbnormalPool;
import com.ruoyi.erp.mapper.BizAbnormalPoolMapper;
import com.ruoyi.erp.service.IBizAbnormalPoolService;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 业务异常池Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
@Service
public class BizAbnormalPoolServiceImpl implements IBizAbnormalPoolService {

    @Autowired
    private BizAbnormalPoolMapper bizAbnormalPoolMapper;

    /**
     * 查询业务异常池
     *
     * @param id 业务异常池ID
     * @return 业务异常池
     */
    @Override
    public BizAbnormalPool selectBizAbnormalPoolById(Long id) {
        return bizAbnormalPoolMapper.selectBizAbnormalPoolById(id);
    }

    /**
     * 查询业务异常池列表
     *
     * @param bizAbnormalPool 业务异常池
     * @return 业务异常池
     */
    @Override
    public List<BizAbnormalPool> selectBizAbnormalPoolList(BizAbnormalPool bizAbnormalPool) {
        return bizAbnormalPoolMapper.selectBizAbnormalPoolList(bizAbnormalPool);
    }

    /**
     * 新增业务异常池
     *
     * @param bizAbnormalPool 业务异常池
     * @return 结果
     */
    @Override
    public int insertBizAbnormalPool(BizAbnormalPool bizAbnormalPool) {
        bizAbnormalPool.setCreateBy(SecurityUtils.getUsername());
        bizAbnormalPool.setCreateTime(DateUtils.getNowDate());
        // 初始状态为待处理
        if (bizAbnormalPool.getStatus() == null) {
            bizAbnormalPool.setStatus("0");
        }
        return bizAbnormalPoolMapper.insertBizAbnormalPool(bizAbnormalPool);
    }

    /**
     * 修改业务异常池
     *
     * @param bizAbnormalPool 业务异常池
     * @return 结果
     */
    @Override
    public int updateBizAbnormalPool(BizAbnormalPool bizAbnormalPool) {
        bizAbnormalPool.setUpdateBy(SecurityUtils.getUsername());
        bizAbnormalPool.setUpdateTime(DateUtils.getNowDate());
        // 如果状态变为处理中，记录处理人
        if ("1".equals(bizAbnormalPool.getStatus()) && bizAbnormalPool.getHandleById() == null) {
            bizAbnormalPool.setHandleById(SecurityUtils.getUserId());
            bizAbnormalPool.setHandleByName(SecurityUtils.getLoginUser().getUser().getNickName());
        }
        // 如果状态变为已处理或已关闭，记录处理时间
        if (("2".equals(bizAbnormalPool.getStatus()) || "3".equals(bizAbnormalPool.getStatus())) && bizAbnormalPool.getHandleTime() == null) {
            bizAbnormalPool.setHandleTime(new Date());
        }
        return bizAbnormalPoolMapper.updateBizAbnormalPool(bizAbnormalPool);
    }

    /**
     * 删除业务异常池信息
     *
     * @param id 业务异常池ID
     * @return 结果
     */
    @Override
    public int deleteBizAbnormalPoolById(Long id) {
        return bizAbnormalPoolMapper.deleteBizAbnormalPoolById(id);
    }

    /**
     * 批量删除业务异常池
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBizAbnormalPoolByIds(Long[] ids) {
        return bizAbnormalPoolMapper.deleteBizAbnormalPoolByIds(ids);
    }

    /**
     * 检查业务是否存在未处理异常
     *
     * @param bizType 业务类型
     * @param bizId 业务ID
     * @return true 存在未处理异常，false 不存在
     */
    @Override
    public boolean hasUnhandledAbnormal(String bizType, Long bizId) {
        int count = bizAbnormalPoolMapper.countUnhandledByBiz(bizType, bizId);
        return count > 0;
    }
}
