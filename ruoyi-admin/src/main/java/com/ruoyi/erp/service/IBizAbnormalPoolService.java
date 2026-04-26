package com.ruoyi.erp.service;

import com.ruoyi.erp.domain.BizAbnormalPool;

import java.util.List;

/**
 * Business abnormal pool service.
 */
public interface IBizAbnormalPoolService {
    BizAbnormalPool selectBizAbnormalPoolById(Long id);

    List<BizAbnormalPool> selectBizAbnormalPoolList(BizAbnormalPool bizAbnormalPool);

    int insertBizAbnormalPool(BizAbnormalPool bizAbnormalPool);

    int updateBizAbnormalPool(BizAbnormalPool bizAbnormalPool);

    int deleteBizAbnormalPoolById(Long id);

    int deleteBizAbnormalPoolByIds(Long[] ids);

    boolean hasUnhandledAbnormal(String bizType, Long bizId);

    boolean hasLockedUnhandledAbnormal(String bizType, Long bizId);
}
