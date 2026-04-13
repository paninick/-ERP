package com.ruoyi.demo.service;

import com.ruoyi.demo.domain.DemoOrder;
import com.ruoyi.demo.domain.DemoStyle;
import java.math.BigDecimal;
import java.util.List;

/**
 * 成本管理Service接口
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
public interface IDemoCostService {
    /**
     * 计算FOB价格
     * 
     * @param styleNo 款号
     * @param quantity 数量
     * @param profitRate 利润率
     * @return FOB价格
     */
    BigDecimal calculateFobPrice(String styleNo, Integer quantity, BigDecimal profitRate);

    /**
     * 计算CIF价格
     * 
     * @param fobPrice FOB价格
     * @param freight 运费
     * @param insurance 保险费
     * @return CIF价格
     */
    BigDecimal calculateCifPrice(BigDecimal fobPrice, BigDecimal freight, BigDecimal insurance);

    /**
     * 计算CNF价格
     * 
     * @param fobPrice FOB价格
     * @param freight 运费
     * @return CNF价格
     */
    BigDecimal calculateCnfPrice(BigDecimal fobPrice, BigDecimal freight);

    /**
     * 计算EXW价格
     * 
     * @param costPrice 成本价
     * @param profitRate 利润率
     * @return EXW价格
     */
    BigDecimal calculateExwPrice(BigDecimal costPrice, BigDecimal profitRate);

    /**
     * 计算订单成本
     * 
     * @param orderId 订单ID
     * @return 订单成本
     */
    BigDecimal calculateOrderCost(Long orderId);

    /**
     * 计算订单利润
     * 
     * @param orderId 订单ID
     * @return 订单利润
     */
    BigDecimal calculateOrderProfit(Long orderId);

    /**
     * 计算订单利润率
     * 
     * @param orderId 订单ID
     * @return 订单利润率
     */
    BigDecimal calculateOrderProfitRate(Long orderId);

    /**
     * 计算款式成本
     * 
     * @param style 款式信息
     * @return 款式成本
     */
    BigDecimal calculateStyleCost(DemoStyle style);

    /**
     * 计算订单价格明细
     * 
     * @param order 订单信息
     * @return 价格明细
     */
    List<BigDecimal> calculateOrderPriceDetails(DemoOrder order);
}
