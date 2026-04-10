package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.ProducePlanMapper;
import com.ruoyi.erp.domain.ProducePlan;
import com.ruoyi.erp.service.IProducePlanService;

/**
 * 生产计划Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
@RequiredArgsConstructor
@Service
public class ProducePlanServiceImpl implements IProducePlanService {
    private final ProducePlanMapper producePlanMapper;

    /**
     * 查询生产计划
     *
     * @param id 生产计划主键
     * @return 生产计划
     */
    @Override
    public ProducePlan selectProducePlanById(Long id) {
        return producePlanMapper.selectProducePlanById(id);
    }

    /**
     * 查询生产计划列表
     *
     * @param producePlan 生产计划
     * @return 生产计划
     */
    @Override
    public List<ProducePlan> selectProducePlanList(ProducePlan producePlan) {
        return producePlanMapper.selectProducePlanList(producePlan);
    }

    /**
     * 新增生产计划
     *
     * @param producePlan 生产计划
     * @return 结果
     */
    @Override
    public int insertProducePlan(ProducePlan producePlan) {
        producePlan.setCreateBy(SecurityUtils.getUserIdStr());
        producePlan.setCreateTime(DateUtils.getNowDate());
        return producePlanMapper.insertProducePlan(producePlan);
    }

    /**
     * 修改生产计划
     *
     * @param producePlan 生产计划
     * @return 结果
     */
    @Override
    public int updateProducePlan(ProducePlan producePlan) {
        producePlan.setUpdateTime(DateUtils.getNowDate());
        return producePlanMapper.updateProducePlan(producePlan);
    }

    /**
     * 批量删除生产计划
     *
     * @param ids 需要删除的生产计划主键
     * @return 结果
     */
    @Override
    public int deleteProducePlanByIds(Long[] ids) {
        return producePlanMapper.deleteProducePlanByIds(ids);
    }

    /**
     * 删除生产计划信息
     *
     * @param id 生产计划主键
     * @return 结果
     */
    @Override
    public int deleteProducePlanById(Long id) {
        return producePlanMapper.deleteProducePlanById(id);
    }

    /**
     * 批量新增生产计划
     *
     * @param list 生产计划
     * @return 结果
     */
    @Override
    public int insertProducePlanBatch(List<ProducePlan> list) {
        return producePlanMapper.insertProducePlanBatch(list);
    }
}
