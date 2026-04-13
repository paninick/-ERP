package com.ruoyi.demo;

import com.ruoyi.demo.domain.DemoOrder;
import com.ruoyi.demo.domain.DemoStyle;
import com.ruoyi.demo.domain.DemoOutsource;
import com.ruoyi.demo.domain.DemoSchedule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ERP 模块集成测试
 *
 * @author ruoyi
 * @date 2026-04-01
 */
public class DemoIntegrationTest {

    /**
     * 测试订单-款式关联集成
     */
    @Test
    public void testOrderStyleIntegration() {
        DemoStyle style = new DemoStyle();
        style.setId(1L);
        style.setStyleNo("STYLE-001");
        style.setStyleName("测试款式");
        style.setFabricCost(new BigDecimal("30.00"));
        style.setAccessoryCost(new BigDecimal("20.00"));
        style.setCmtCost(new BigDecimal("50.00"));
        style.setStandardCost(new BigDecimal("100.00"));

        DemoOrder order = new DemoOrder();
        order.setId(1L);
        order.setStyleNo(style.getStyleNo());
        order.setQty(100);
        order.setFobPrice(new BigDecimal("125.00"));
        order.setRevenue(new BigDecimal("12500.00"));
        order.setCost(new BigDecimal("10000.00"));

        assertNotNull(style);
        assertNotNull(order);
        assertEquals(style.getStyleNo(), order.getStyleNo());

        BigDecimal styleCost = style.getFabricCost()
                .add(style.getAccessoryCost())
                .add(style.getCmtCost());
        assertEquals(style.getStandardCost(), styleCost);

        BigDecimal orderTotalCost = style.getStandardCost().multiply(new BigDecimal(order.getQty()));
        assertEquals(order.getCost(), orderTotalCost);
    }

    /**
     * 测试订单-外发单关联集成
     */
    @Test
    public void testOrderOutsourceIntegration() {
        DemoOrder order = new DemoOrder();
        order.setId(1L);
        order.setQty(100);

        DemoOutsource outsource = new DemoOutsource();
        outsource.setId(1L);
        outsource.setOrderId(order.getId());
        outsource.setPlanQty(100);
        outsource.setSentQty(100);
        outsource.setReceivedQty(95);
        outsource.setLossQty(5);
        outsource.setUnitPrice(new BigDecimal("10.00"));
        outsource.setTotalAmount(new BigDecimal("950.00"));

        assertNotNull(order);
        assertNotNull(outsource);
        assertEquals(order.getId(), outsource.getOrderId());
        assertEquals(order.getQty(), outsource.getPlanQty());

        Integer totalQty = outsource.getReceivedQty() + outsource.getLossQty();
        assertEquals(outsource.getSentQty(), totalQty);

        BigDecimal calculatedAmount = outsource.getUnitPrice().multiply(new BigDecimal(outsource.getReceivedQty()));
        assertEquals(outsource.getTotalAmount(), calculatedAmount);
    }

    /**
     * 测试订单-排程关联集成
     */
    @Test
    public void testOrderScheduleIntegration() {
        DemoOrder order = new DemoOrder();
        order.setId(1L);
        order.setQty(100);

        DemoSchedule schedule = new DemoSchedule();
        schedule.setId(1L);
        schedule.setOrderId(order.getId());
        schedule.setPlanQty(100);
        schedule.setCapacity(120);
        schedule.setLoad(new BigDecimal("83.33"));

        assertNotNull(order);
        assertNotNull(schedule);
        assertEquals(order.getId(), schedule.getOrderId());
        assertEquals(order.getQty(), schedule.getPlanQty());

        BigDecimal calculatedLoad = new BigDecimal(schedule.getPlanQty())
                .divide(new BigDecimal(schedule.getCapacity()), 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        assertTrue(calculatedLoad.compareTo(schedule.getLoad()) <= 0);
    }

    /**
     * 测试成本核算完整流程集成
     */
    @Test
    public void testCostCalculationFullFlow() {
        DemoStyle style = new DemoStyle();
        style.setFabricCost(new BigDecimal("30.00"));
        style.setAccessoryCost(new BigDecimal("20.00"));
        style.setCmtCost(new BigDecimal("50.00"));
        style.setStandardCost(new BigDecimal("100.00"));
        style.setProfitRate(new BigDecimal("20.00"));

        BigDecimal styleCost = style.getFabricCost()
                .add(style.getAccessoryCost())
                .add(style.getCmtCost());
        assertEquals(style.getStandardCost(), styleCost);

        BigDecimal fobPrice = style.getStandardCost().divide(
                BigDecimal.ONE.subtract(style.getProfitRate().divide(BigDecimal.valueOf(100), 4, BigDecimal.ROUND_HALF_UP)),
                4, BigDecimal.ROUND_HALF_UP
        );
        assertEquals(new BigDecimal("125.0000"), fobPrice);

        DemoOrder order = new DemoOrder();
        order.setQty(100);
        order.setFobPrice(fobPrice);
        order.setCost(styleCost.multiply(new BigDecimal(100)));
        order.setRevenue(order.getFobPrice().multiply(new BigDecimal(order.getQty())));

        BigDecimal profit = order.getRevenue().subtract(order.getCost());
        BigDecimal profitRate = order.getRevenue().compareTo(BigDecimal.ZERO) > 0
                ? profit.divide(order.getRevenue(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100))
                : BigDecimal.ZERO;

        assertEquals(style.getProfitRate(), profitRate.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 测试完整业务流程集成 - 订单创建到成本核算
     */
    @Test
    public void testCompleteBusinessFlow() {
        DemoStyle style = new DemoStyle();
        style.setStyleNo("STYLE-001");
        style.setFabricCost(new BigDecimal("30.00"));
        style.setAccessoryCost(new BigDecimal("20.00"));
        style.setCmtCost(new BigDecimal("50.00"));
        style.setStandardCost(new BigDecimal("100.00"));
        style.setProfitRate(new BigDecimal("20.00"));

        BigDecimal fobPrice = style.getStandardCost().divide(
                BigDecimal.ONE.subtract(style.getProfitRate().divide(BigDecimal.valueOf(100), 4, BigDecimal.ROUND_HALF_UP)),
                4, BigDecimal.ROUND_HALF_UP
        );

        DemoOrder order = new DemoOrder();
        order.setOrderNo("ORDER-001");
        order.setStyleNo(style.getStyleNo());
        order.setQty(100);
        order.setFobPrice(fobPrice);
        order.setCost(style.getStandardCost().multiply(new BigDecimal(100)));
        order.setRevenue(order.getFobPrice().multiply(new BigDecimal(100)));

        DemoOutsource outsource = new DemoOutsource();
        outsource.setOrderId(1L);
        outsource.setPlanQty(100);
        outsource.setSentQty(100);
        outsource.setReceivedQty(95);
        outsource.setLossQty(5);
        outsource.setUnitPrice(new BigDecimal("10.00"));
        outsource.setTotalAmount(new BigDecimal("950.00"));

        DemoSchedule schedule = new DemoSchedule();
        schedule.setOrderId(1L);
        schedule.setPlanQty(100);
        schedule.setCapacity(120);
        schedule.setLoad(new BigDecimal("83.33"));

        assertNotNull(style);
        assertNotNull(order);
        assertNotNull(outsource);
        assertNotNull(schedule);
        assertEquals(style.getStyleNo(), order.getStyleNo());

        BigDecimal profit = order.getRevenue().subtract(order.getCost());
        BigDecimal profitRate = order.getRevenue().compareTo(BigDecimal.ZERO) > 0
                ? profit.divide(order.getRevenue(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100))
                : BigDecimal.ZERO;

        assertEquals(style.getProfitRate(), profitRate.setScale(2, BigDecimal.ROUND_HALF_UP));
    }
}
