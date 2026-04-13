package com.ruoyi.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.demo.domain.DemoOutsource;
import com.ruoyi.demo.domain.DemoOutsourceExtra;
import com.ruoyi.demo.mapper.DemoOutsourceMapper;
import com.ruoyi.demo.mapper.DemoOutsourceExtraMapper;
import com.ruoyi.demo.service.IDemoOutsourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
 * 外发加工管理Service实现类
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
@Service
public class DemoOutsourceServiceImpl extends ServiceImpl<DemoOutsourceMapper, DemoOutsource> implements IDemoOutsourceService {

    @Autowired
    private DemoOutsourceExtraMapper demoOutsourceExtraMapper;

    @Override
    public IPage<DemoOutsource> selectDemoOutsourcePage(DemoOutsource demoOutsource, Integer page, Integer size) {
        Page<DemoOutsource> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<DemoOutsource> wrapper = new LambdaQueryWrapper<>();
        
        // 订单ID查询
        if (demoOutsource.getOrderId() != null) {
            wrapper.eq(DemoOutsource::getOrderId, demoOutsource.getOrderId());
        }
        
        // 工厂名称查询
        if (demoOutsource.getFactoryName() != null && !demoOutsource.getFactoryName().isEmpty()) {
            wrapper.like(DemoOutsource::getFactoryName, demoOutsource.getFactoryName());
        }
        
        // 工序查询
        if (demoOutsource.getProcess() != null && !demoOutsource.getProcess().isEmpty()) {
            wrapper.like(DemoOutsource::getProcess, demoOutsource.getProcess());
        }
        
        // 状态查询
        if (demoOutsource.getStatus() != null && !demoOutsource.getStatus().isEmpty()) {
            wrapper.like(DemoOutsource::getStatus, demoOutsource.getStatus());
        }
        
        // 按创建时间倒序排序
        wrapper.orderByDesc(DemoOutsource::getCreateTime);
        
        return this.page(pageParam, wrapper);
    }

    @Override
    public List<DemoOutsource> selectDemoOutsourceList(DemoOutsource demoOutsource) {
        LambdaQueryWrapper<DemoOutsource> wrapper = new LambdaQueryWrapper<>();
        
        // 订单ID查询
        if (demoOutsource.getOrderId() != null) {
            wrapper.eq(DemoOutsource::getOrderId, demoOutsource.getOrderId());
        }
        
        // 工厂名称查询
        if (demoOutsource.getFactoryName() != null && !demoOutsource.getFactoryName().isEmpty()) {
            wrapper.like(DemoOutsource::getFactoryName, demoOutsource.getFactoryName());
        }
        
        // 工序查询
        if (demoOutsource.getProcess() != null && !demoOutsource.getProcess().isEmpty()) {
            wrapper.like(DemoOutsource::getProcess, demoOutsource.getProcess());
        }
        
        // 状态查询
        if (demoOutsource.getStatus() != null && !demoOutsource.getStatus().isEmpty()) {
            wrapper.like(DemoOutsource::getStatus, demoOutsource.getStatus());
        }
        
        // 按创建时间倒序排序
        wrapper.orderByDesc(DemoOutsource::getCreateTime);
        
        return this.list(wrapper);
    }

    @Override
    public DemoOutsource selectDemoOutsourceById(Long id) {
        return this.getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertDemoOutsource(DemoOutsource demoOutsource) {
        // 计算损耗率和加工费总额
        if (demoOutsource.getPlanQty() != null && demoOutsource.getPlanQty() > 0) {
            BigDecimal lossRate = calculateLossRate(demoOutsource);
            demoOutsource.setLossRate(lossRate);
        }
        
        if (demoOutsource.getUnitPrice() != null && demoOutsource.getPlanQty() != null) {
            BigDecimal totalAmount = calculateTotalAmount(demoOutsource);
            demoOutsource.setTotalAmount(totalAmount);
        }
        
        return this.save(demoOutsource);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateDemoOutsource(DemoOutsource demoOutsource) {
        // 计算损耗率和加工费总额
        if (demoOutsource.getPlanQty() != null && demoOutsource.getPlanQty() > 0) {
            BigDecimal lossRate = calculateLossRate(demoOutsource);
            demoOutsource.setLossRate(lossRate);
        }
        
        if (demoOutsource.getUnitPrice() != null && demoOutsource.getPlanQty() != null) {
            BigDecimal totalAmount = calculateTotalAmount(demoOutsource);
            demoOutsource.setTotalAmount(totalAmount);
        }
        
        return this.updateById(demoOutsource);
    }

    @Override
    public boolean deleteDemoOutsourceById(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean deleteDemoOutsourceByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return false;
        }
        
        return this.removeBatchByIds(List.of(ids));
    }

    @Override
    public boolean insertDemoOutsourceExtra(DemoOutsourceExtra demoOutsourceExtra) {
        return demoOutsourceExtraMapper.insert(demoOutsourceExtra) > 0;
    }

    @Override
    public List<DemoOutsourceExtra> selectDemoOutsourceExtraList(DemoOutsourceExtra demoOutsourceExtra) {
        LambdaQueryWrapper<DemoOutsourceExtra> wrapper = new LambdaQueryWrapper<>();
        
        // 外发加工订单ID查询
        if (demoOutsourceExtra.getOutsourceId() != null) {
            wrapper.eq(DemoOutsourceExtra::getOutsourceId, demoOutsourceExtra.getOutsourceId());
        }
        
        // 审批状态查询
        if (demoOutsourceExtra.getApproved() != null && !demoOutsourceExtra.getApproved().isEmpty()) {
            wrapper.like(DemoOutsourceExtra::getApproved, demoOutsourceExtra.getApproved());
        }
        
        // 按创建时间倒序排序
        wrapper.orderByDesc(DemoOutsourceExtra::getCreateTime);
        
        return demoOutsourceExtraMapper.selectList(wrapper);
    }

    @Override
    public DemoOutsourceExtra selectDemoOutsourceExtraById(Long id) {
        return demoOutsourceExtraMapper.selectById(id);
    }

    @Override
    public boolean approveDemoOutsourceExtra(Long id, String approved) {
        DemoOutsourceExtra demoOutsourceExtra = demoOutsourceExtraMapper.selectById(id);
        if (demoOutsourceExtra == null) {
            return false;
        }
        
        demoOutsourceExtra.setApproved(approved);
        return demoOutsourceExtraMapper.updateById(demoOutsourceExtra) > 0;
    }

    @Override
    public BigDecimal calculateLossRate(DemoOutsource demoOutsource) {
        if (demoOutsource.getPlanQty() == null || demoOutsource.getPlanQty() == 0) {
            return BigDecimal.ZERO;
        }
        
        if (demoOutsource.getLossQty() == null || demoOutsource.getLossQty() < 0) {
            return BigDecimal.ZERO;
        }
        
        return BigDecimal.valueOf(demoOutsource.getLossQty())
                .divide(BigDecimal.valueOf(demoOutsource.getPlanQty()), 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    @Override
    public BigDecimal calculateTotalAmount(DemoOutsource demoOutsource) {
        if (demoOutsource.getUnitPrice() == null || demoOutsource.getPlanQty() == null) {
            return BigDecimal.ZERO;
        }
        
        return demoOutsource.getUnitPrice().multiply(BigDecimal.valueOf(demoOutsource.getPlanQty()));
    }
}
