package com.ruoyi.demo.service;

import com.ruoyi.demo.domain.DemoOrder;
import com.ruoyi.demo.domain.DemoStyle;

import java.math.BigDecimal;
import java.util.List;

/**
 * Demo cost service.
 */
public interface IDemoCostService {

    BigDecimal calculateFobPrice(String styleCode, Integer quantity, BigDecimal profitRate);

    BigDecimal calculateCifPrice(BigDecimal fobPrice, BigDecimal freight, BigDecimal insurance);

    BigDecimal calculateCnfPrice(BigDecimal fobPrice, BigDecimal freight);

    BigDecimal calculateExwPrice(BigDecimal costPrice, BigDecimal profitRate);

    BigDecimal calculateOrderCost(Long orderId);

    BigDecimal calculateOrderProfit(Long orderId);

    BigDecimal calculateOrderProfitRate(Long orderId);

    BigDecimal calculateStyleCost(DemoStyle style);

    List<BigDecimal> calculateOrderPriceDetails(DemoOrder order);
}
