package com.ruoyi.demo.service.impl;

import com.ruoyi.demo.domain.DemoOrder;
import com.ruoyi.demo.domain.DemoStyle;
import com.ruoyi.demo.mapper.DemoOrderMapper;
import com.ruoyi.demo.mapper.DemoStyleMapper;
import com.ruoyi.demo.service.IDemoCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 成本管理Service实现类
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
@Service
public class DemoCostServiceImpl implements IDemoCostService {

    @Autowired
    private DemoStyleMapper demoStyleMapper;

    @Autowired
    private DemoOrderMapper demoOrderMapper;

    @Override
    public BigDecimal calculateFobPrice(String styleNo, Integer quantity, BigDecimal profitRate) {
        // 查询款式信息
        DemoStyle style = demoStyleMapper.selectOne(wrapper -> wrapper.eq("style_no", styleNo));
        if (style == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal costPrice = calculateStyleCost(style);
        BigDecimal totalCost = costPrice.multiply(BigDecimal.valueOf(quantity));
        BigDecimal profit = totalCost.multiply(profitRate);
        
        // 假设FOB价格计算包含10%的运费和保险费
        BigDecimal fobPrice = totalCost.add(profit).multiply(BigDecimal.valueOf(1.10));
        return fobPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public BigDecimal calculateCifPrice(BigDecimal fobPrice, BigDecimal freight, BigDecimal insurance) {
        return fobPrice.add(freight).add(insurance).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public BigDecimal calculateCnfPrice(BigDecimal fobPrice, BigDecimal freight) {
        return fobPrice.add(freight).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public BigDecimal calculateExwPrice(BigDecimal costPrice, BigDecimal profitRate) {
        return costPrice.multiply(BigDecimal.ONE.add(profitRate)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public BigDecimal calculateOrderCost(Long orderId) {
        DemoOrder order = demoOrderMapper.selectById(orderId);
        if (order == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal cost = BigDecimal.ZERO;
        if (order.getCost() != null) {
            cost = order.getCost();
        }

        return cost.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public BigDecimal calculateOrderProfit(Long orderId) {
        DemoOrder order = demoOrderMapper.selectById(orderId);
        if (order == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal revenue = order.getRevenue() != null ? order.getRevenue() : BigDecimal.ZERO;
        BigDecimal cost = calculateOrderCost(orderId);
        
        return revenue.subtract(cost).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public BigDecimal calculateOrderProfitRate(Long orderId) {
        BigDecimal profit = calculateOrderProfit(orderId);
        DemoOrder order = demoOrderMapper.selectById(orderId);
        
        if (order == null || order.getRevenue() == null || order.getRevenue().compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return profit.divide(order.getRevenue(), 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public BigDecimal calculateStyleCost(DemoStyle style) {
        if (style == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal fabricCost = style.getFabricCost() != null ? style.getFabricCost() : BigDecimal.ZERO;
        BigDecimal accessoryCost = style.getAccessoryCost() != null ? style.getAccessoryCost() : BigDecimal.ZERO;
        BigDecimal cmtCost = style.getCmtCost() != null ? style.getCmtCost() : BigDecimal.ZERO;
        
        return fabricCost.add(accessoryCost).add(cmtCost).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public List<BigDecimal> calculateOrderPriceDetails(DemoOrder order) {
        List<BigDecimal> details = new ArrayList<>();
        
        if (order == null) {
            return details;
        }

        details.add(order.getFobPrice() != null ? order.getFobPrice() : BigDecimal.ZERO);
        details.add(order.getCifPrice() != null ? order.getCifPrice() : BigDecimal.ZERO);
        details.add(order.getCnfPrice() != null ? order.getCnfPrice() : BigDecimal.ZERO);
        details.add(order.getExwPrice() != null ? order.getExwPrice() : BigDecimal.ZERO);
        
        return details;
    }
}
