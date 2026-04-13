package com.ruoyi.demo;

import com.ruoyi.demo.domain.DemoOrder;
import com.ruoyi.demo.service.IDemoOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 订单管理单元测试
 *
 * @author ruoyi
 * @date 2026-04-01
 */
@SpringBootTest
public class DemoOrderServiceTest {

    @Autowired
    private IDemoOrderService demoOrderService;

    /**
     * 测试计算利润
     */
    @Test
    public void testCalculateProfit() {
        BigDecimal revenue = new BigDecimal("1000.00");
        BigDecimal cost = new BigDecimal("600.00");

        BigDecimal profit = demoOrderService.calculateProfit(revenue, cost);

        assertNotNull(profit);
        assertEquals(new BigDecimal("400.00"), profit);
    }

    /**
     * 测试计算利润率
     */
    @Test
    public void testCalculateProfitRate() {
        BigDecimal revenue = new BigDecimal("1000.00");
        BigDecimal cost = new BigDecimal("600.00");

        BigDecimal profitRate = demoOrderService.calculateProfitRate(revenue, cost);

        assertNotNull(profitRate);
        assertEquals(new BigDecimal("40.00"), profitRate);
    }

    /**
     * 测试创建订单对象
     */
    @Test
    public void testCreateDemoOrder() {
        DemoOrder order = new DemoOrder();
        order.setFactoryId(1L);
        order.setOrderNo("TEST-001");
        order.setStyleNo("STYLE-001");
        order.setStyleName("测试款式");
        order.setQty(100);
        order.setFobPrice(new BigDecimal("10.00"));
        order.setRevenue(new BigDecimal("1000.00"));
        order.setCost(new BigDecimal("600.00"));
        order.setDueDays(30);
        order.setStatus("ACTIVE");

        assertNotNull(order);
        assertEquals(1L, order.getFactoryId());
        assertEquals("TEST-001", order.getOrderNo());
        assertEquals("STYLE-001", order.getStyleNo());
        assertEquals("测试款式", order.getStyleName());
        assertEquals(100, order.getQty());
        assertEquals(new BigDecimal("10.00"), order.getFobPrice());
        assertEquals(new BigDecimal("1000.00"), order.getRevenue());
        assertEquals(new BigDecimal("600.00"), order.getCost());
        assertEquals(30, order.getDueDays());
        assertEquals("ACTIVE", order.getStatus());
    }

    /**
     * 测试利润计算边界情况 - 零收入
     */
    @Test
    public void testCalculateProfitZeroRevenue() {
        BigDecimal revenue = BigDecimal.ZERO;
        BigDecimal cost = new BigDecimal("100.00");

        BigDecimal profit = demoOrderService.calculateProfit(revenue, cost);

        assertNotNull(profit);
        assertEquals(new BigDecimal("-100.00"), profit);
    }

    /**
     * 测试利润率边界情况 - 零收入
     */
    @Test
    public void testCalculateProfitRateZeroRevenue() {
        BigDecimal revenue = BigDecimal.ZERO;
        BigDecimal cost = new BigDecimal("100.00");

        BigDecimal profitRate = demoOrderService.calculateProfitRate(revenue, cost);

        assertNotNull(profitRate);
        assertEquals(new BigDecimal("-100.00"), profitRate);
    }
}
