package com.ruoyi.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.demo.domain.DemoSchedule;
import com.ruoyi.demo.mapper.DemoScheduleMapper;
import com.ruoyi.demo.service.IDemoScheduleService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

/**
 * 生产排程管理Service实现类
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
@Service
public class DemoScheduleServiceImpl extends ServiceImpl<DemoScheduleMapper, DemoSchedule> implements IDemoScheduleService {

    @Override
    public IPage<DemoSchedule> selectDemoSchedulePage(DemoSchedule demoSchedule, Integer page, Integer size) {
        Page<DemoSchedule> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<DemoSchedule> wrapper = new LambdaQueryWrapper<>();
        
        // 订单ID查询
        if (demoSchedule.getOrderId() != null) {
            wrapper.eq(DemoSchedule::getOrderId, demoSchedule.getOrderId());
        }
        
        // 工序查询
        if (demoSchedule.getProcess() != null && !demoSchedule.getProcess().isEmpty()) {
            wrapper.like(DemoSchedule::getProcess, demoSchedule.getProcess());
        }
        
        // 按创建时间倒序排序
        wrapper.orderByDesc(DemoSchedule::getCreateTime);
        
        return this.page(pageParam, wrapper);
    }

    @Override
    public List<DemoSchedule> selectDemoScheduleList(DemoSchedule demoSchedule) {
        LambdaQueryWrapper<DemoSchedule> wrapper = new LambdaQueryWrapper<>();
        
        // 订单ID查询
        if (demoSchedule.getOrderId() != null) {
            wrapper.eq(DemoSchedule::getOrderId, demoSchedule.getOrderId());
        }
        
        // 工序查询
        if (demoSchedule.getProcess() != null && !demoSchedule.getProcess().isEmpty()) {
            wrapper.like(DemoSchedule::getProcess, demoSchedule.getProcess());
        }
        
        // 按创建时间倒序排序
        wrapper.orderByDesc(DemoSchedule::getCreateTime);
        
        return this.list(wrapper);
    }

    @Override
    public DemoSchedule selectDemoScheduleById(Long id) {
        return this.getById(id);
    }

    @Override
    public boolean insertDemoSchedule(DemoSchedule demoSchedule) {
        // 计算负载率和产能利用率
        if (demoSchedule.getPlanQty() != null && demoSchedule.getCapacity() != null && demoSchedule.getCapacity() > 0) {
            BigDecimal loadRate = calculateLoadRate(demoSchedule);
            demoSchedule.setLoad(loadRate);
        }
        
        return this.save(demoSchedule);
    }

    @Override
    public boolean updateDemoSchedule(DemoSchedule demoSchedule) {
        // 计算负载率和产能利用率
        if (demoSchedule.getPlanQty() != null && demoSchedule.getCapacity() != null && demoSchedule.getCapacity() > 0) {
            BigDecimal loadRate = calculateLoadRate(demoSchedule);
            demoSchedule.setLoad(loadRate);
        }
        
        return this.updateById(demoSchedule);
    }

    @Override
    public boolean deleteDemoScheduleById(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean deleteDemoScheduleByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return false;
        }
        
        return this.removeBatchByIds(List.of(ids));
    }

    @Override
    public BigDecimal calculateLoadRate(DemoSchedule demoSchedule) {
        if (demoSchedule.getPlanQty() == null || demoSchedule.getCapacity() == null || demoSchedule.getCapacity() == 0) {
            return BigDecimal.ZERO;
        }
        
        return BigDecimal.valueOf(demoSchedule.getPlanQty())
                .divide(BigDecimal.valueOf(demoSchedule.getCapacity()), 4, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public boolean isFullLoad(DemoSchedule demoSchedule) {
        BigDecimal loadRate = calculateLoadRate(demoSchedule);
        return loadRate.compareTo(BigDecimal.ONE) > 0; // 负载率超过100%认为是爆满
    }

    @Override
    public boolean isIdleLoad(DemoSchedule demoSchedule) {
        BigDecimal loadRate = calculateLoadRate(demoSchedule);
        return loadRate.compareTo(new BigDecimal("0.6")) < 0; // 负载率低于60%认为是空闲
    }

    @Override
    public BigDecimal calculateCapacityUtilization(DemoSchedule demoSchedule) {
        if (demoSchedule.getPlanQty() == null || demoSchedule.getCapacity() == null || demoSchedule.getCapacity() == 0) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal loadRate = calculateLoadRate(demoSchedule);
        return loadRate.compareTo(BigDecimal.ONE) > 0 ? BigDecimal.ONE : loadRate; // 产能利用率最高100%
    }
}
