package com.ruoyi.demo;

import com.ruoyi.demo.domain.DemoStyle;
import com.ruoyi.demo.domain.DemoOrder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 成本管理单元测试
 *
 * @author ruoyi
 * @date 2026-04-01
 */
public class DemoCostServiceTest {

    /**
     * 测试计算FOB价格
     */
    @Test
    public void testCalculateFobPrice() {
        BigDecimal costPrice = new BigDecimal("100.00");
        BigDecimal profitRate = new BigDecimal("20.00");

        BigDecimal fobPrice = costPrice.divide(
                BigDecimal.ONE.subtract(profitRate.divide(BigDecimal.valueOf(100), 4, BigDecimal.ROUND_HALF_UP)),
                4, BigDecimal.ROUND_HALF_UP
        );

        assertEquals(new BigDecimal("125.0000"), fobPrice);
    }

    /**
     * 测试计算CIF价格
     */
    @Test
    public void testCalculateCifPrice() {
        BigDecimal fobPrice = new BigDecimal("100.00");
        BigDecimal freight = new BigDecimal("20.00");
        BigDecimal insurance = new BigDecimal("5.00");

        BigDecimal cifPrice = fobPrice.add(freight).add(insurance);

        assertEquals(new BigDecimal("125.00"), cifPrice);
    }

    /**
     * 测试计算CNF价格
     */
    @Test
    public void testCalculateCnfPrice() {
        BigDecimal fobPrice = new BigDecimal("100.00");
        BigDecimal freight = new BigDecimal("20.00");

        BigDecimal cnfPrice = fobPrice.add(freight);

        assertEquals(new BigDecimal("120.00"), cnfPrice);
    }

    /**
     * 测试计算EXW价格
     */
    @Test
    public void testCalculateExwPrice() {
        BigDecimal costPrice = new BigDecimal("100.00");
        BigDecimal profitRate = new BigDecimal("20.00");

        BigDecimal exwPrice = costPrice.divide(
                BigDecimal.ONE.subtract(profitRate.divide(BigDecimal.valueOf(100), 4, BigDecimal.ROUND_HALF_UP)),
                4, BigDecimal.ROUND_HALF_UP
        );

        assertEquals(new BigDecimal("125.0000"), exwPrice);
    }

    /**
     * 测试计算款式成本
     */
    @Test
    public void testCalculateStyleCost() {
        DemoStyle style = new DemoStyle();
        style.setFabricCost(new BigDecimal("30.00"));
        style.setAccessoryCost(new BigDecimal("20.00"));
        style.setCmtCost(new BigDecimal("50.00"));

        BigDecimal styleCost = style.getFabricCost()
                .add(style.getAccessoryCost())
                .add(style.getCmtCost());

        assertEquals(new BigDecimal("100.00"), styleCost);
    }

    /**
     * 测试计算订单利润
     */
    @Test
    public void testCalculateOrderProfit() {
        BigDecimal revenue = new BigDecimal("1000.00");
        BigDecimal cost = new BigDecimal("600.00");

        BigDecimal profit = revenue.subtract(cost);

        assertEquals(new BigDecimal("400.00"), profit);
    }

    /**
     * 测试计算订单利润率
     */
    @Test
    public void testCalculateOrderProfitRate() {
        BigDecimal revenue = new BigDecimal("1000.00");
        BigDecimal cost = new BigDecimal("600.00");

        BigDecimal profit = revenue.subtract(cost);
        BigDecimal profitRate = revenue.compareTo(BigDecimal.ZERO) > 0
                ? profit.divide(revenue, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100))
                : BigDecimal.ZERO;

        assertEquals(new BigDecimal("40.0000"), profitRate);
    }

    /**
     * 测试FOB价格边界情况 - 零利润率
     */
    @Test
    public void testFobPriceZeroProfitRate() {
        BigDecimal costPrice = new BigDecimal("100.00");
        BigDecimal profitRate = BigDecimal.ZERO;

        BigDecimal fobPrice = costPrice;

        assertEquals(new BigDecimal("100.00"), fobPrice);
    }

    /**
     * 测试FOB价格边界情况 - 100%利润率
     */
    @Test
    public void testFobPriceFullProfitRate() {
        BigDecimal costPrice = new BigDecimal("100.00");
        BigDecimal profitRate = new BigDecimal("50.00");

        BigDecimal fobPrice = costPrice.divide(
                BigDecimal.ONE.subtract(profitRate.divide(BigDecimal.valueOf(100), 4, BigDecimal.ROUND_HALF_UP)),
                4, BigDecimal.ROUND_HALF_UP
        );

        assertEquals(new BigDecimal("200.0000"), fobPrice);
    }

    /**
     * 测试CIF价格边界情况 - 零运费和保险费
     */
    @Test
    public void testCifPriceZeroFreightInsurance() {
        BigDecimal fobPrice = new BigDecimal("100.00");
        BigDecimal freight = BigDecimal.ZERO;
        BigDecimal insurance = BigDecimal.ZERO;

        BigDecimal cifPrice = fobPrice.add(freight).add(insurance);

        assertEquals(new BigDecimal("100.00"), cifPrice);
    }

    /**
     * 测试款式成本边界情况 - 零成本
     */
    @Test
    public void testStyleCostZero() {
        DemoStyle style = new DemoStyle();
        style.setFabricCost(BigDecimal.ZERO);
        style.setAccessoryCost(BigDecimal.ZERO);
        style.setCmtCost(BigDecimal.ZERO);

        BigDecimal styleCost = style.getFabricCost()
                .add(style.getAccessoryCost())
                .add(style.getCmtCost());

        assertEquals(BigDecimal.ZERO, styleCost);
    }
}
