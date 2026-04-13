package com.ruoyi.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.demo.domain.DemoOrder;
import com.ruoyi.demo.mapper.DemoOrderMapper;
import com.ruoyi.demo.service.IDemoOrderService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单管理Service实现类
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
@Service
public class DemoOrderServiceImpl extends ServiceImpl<DemoOrderMapper, DemoOrder> implements IDemoOrderService {

    @Override
    public IPage<DemoOrder> selectDemoOrderPage(DemoOrder demoOrder, Integer page, Integer size) {
        Page<DemoOrder> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<DemoOrder> wrapper = new LambdaQueryWrapper<>();
        
        // 订单号查询
        if (demoOrder.getOrderNo() != null && !demoOrder.getOrderNo().isEmpty()) {
            wrapper.like(DemoOrder::getOrderNo, demoOrder.getOrderNo());
        }
        
        // 客户名称查询
        if (demoOrder.getCustomerName() != null && !demoOrder.getCustomerName().isEmpty()) {
            wrapper.like(DemoOrder::getCustomerName, demoOrder.getCustomerName());
        }
        
        // 款号查询
        if (demoOrder.getStyleNo() != null && !demoOrder.getStyleNo().isEmpty()) {
            wrapper.like(DemoOrder::getStyleNo, demoOrder.getStyleNo());
        }
        
        // 状态查询
        if (demoOrder.getStatus() != null && !demoOrder.getStatus().isEmpty()) {
            wrapper.eq(DemoOrder::getStatus, demoOrder.getStatus());
        }
        
        // 按创建时间倒序排序
        wrapper.orderByDesc(DemoOrder::getCreateTime);
        
        return this.page(pageParam, wrapper);
    }

    @Override
    public List<DemoOrder> selectDemoOrderList(DemoOrder demoOrder) {
        LambdaQueryWrapper<DemoOrder> wrapper = new LambdaQueryWrapper<>();
        
        // 订单号查询
        if (demoOrder.getOrderNo() != null && !demoOrder.getOrderNo().isEmpty()) {
            wrapper.like(DemoOrder::getOrderNo, demoOrder.getOrderNo());
        }
        
        // 客户名称查询
        if (demoOrder.getCustomerName() != null && !demoOrder.getCustomerName().isEmpty()) {
            wrapper.like(DemoOrder::getCustomerName, demoOrder.getCustomerName());
        }
        
        // 款号查询
        if (demoOrder.getStyleNo() != null && !demoOrder.getStyleNo().isEmpty()) {
            wrapper.like(DemoOrder::getStyleNo, demoOrder.getStyleNo());
        }
        
        // 状态查询
        if (demoOrder.getStatus() != null && !demoOrder.getStatus().isEmpty()) {
            wrapper.eq(DemoOrder::getStatus, demoOrder.getStatus());
        }
        
        // 按创建时间倒序排序
        wrapper.orderByDesc(DemoOrder::getCreateTime);
        
        return this.list(wrapper);
    }

    @Override
    public DemoOrder selectDemoOrderById(Long id) {
        return this.getById(id);
    }

    @Override
    public boolean insertDemoOrder(DemoOrder demoOrder) {
        return this.save(demoOrder);
    }

    @Override
    public boolean updateDemoOrder(DemoOrder demoOrder) {
        return this.updateById(demoOrder);
    }

    @Override
    public boolean deleteDemoOrderById(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean deleteDemoOrderByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return false;
        }
        
        return this.removeBatchByIds(List.of(ids));
    }

    @Override
    public BigDecimal calculateProfit(BigDecimal revenue, BigDecimal cost) {
        if (revenue == null || cost == null) {
            return BigDecimal.ZERO;
        }
        
        return revenue.subtract(cost);
    }

    @Override
    public BigDecimal calculateProfitRate(BigDecimal revenue, BigDecimal cost) {
        if (revenue == null || cost == null || revenue.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal profit = calculateProfit(revenue, cost);
        return profit.divide(revenue, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
    }
}
