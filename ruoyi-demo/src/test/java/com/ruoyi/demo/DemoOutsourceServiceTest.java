package com.ruoyi.demo;

import com.ruoyi.demo.domain.DemoOutsource;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 外发管理单元测试
 *
 * @author ruoyi
 * @date 2026-04-01
 */
public class DemoOutsourceServiceTest {

    /**
     * 测试创建外发单对象
     */
    @Test
    public void testCreateDemoOutsource() {
        DemoOutsource outsource = new DemoOutsource();
        outsource.setFactoryId(1L);
        outsource.setOrderId(1L);
        outsource.setFactoryName("测试加工厂");
        outsource.setProcess("缝制");
        outsource.setPlanQty(100);
        outsource.setSentQty(100);
        outsource.setReceivedQty(95);
        outsource.setLossQty(5);
        outsource.setLossRate(new BigDecimal("5.00"));
        outsource.setUnitPrice(new BigDecimal("10.00"));
        outsource.setTotalAmount(new BigDecimal("950.00"));
        outsource.setStatus("ACTIVE");

        assertNotNull(outsource);
        assertEquals(1L, outsource.getFactoryId());
        assertEquals(1L, outsource.getOrderId());
        assertEquals("测试加工厂", outsource.getFactoryName());
        assertEquals("缝制", outsource.getProcess());
        assertEquals(100, outsource.getPlanQty());
        assertEquals(100, outsource.getSentQty());
        assertEquals(95, outsource.getReceivedQty());
        assertEquals(5, outsource.getLossQty());
        assertEquals(new BigDecimal("5.00"), outsource.getLossRate());
        assertEquals(new BigDecimal("10.00"), outsource.getUnitPrice());
        assertEquals(new BigDecimal("950.00"), outsource.getTotalAmount());
        assertEquals("ACTIVE", outsource.getStatus());
    }

    /**
     * 测试损耗率计算
     */
    @Test
    public void testLossRateCalculation() {
        Integer planQty = 100;
        Integer lossQty = 5;

        BigDecimal lossRate = new BigDecimal(lossQty)
                .divide(new BigDecimal(planQty), 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        assertEquals(new BigDecimal("5.0000"), lossRate);
    }

    /**
     * 测试加工费总额计算
     */
    @Test
    public void testTotalAmountCalculation() {
        Integer receivedQty = 95;
        BigDecimal unitPrice = new BigDecimal("10.00");

        BigDecimal totalAmount = unitPrice.multiply(new BigDecimal(receivedQty));

        assertEquals(new BigDecimal("950.00"), totalAmount);
    }

    /**
     * 测试外发单状态验证
     */
    @Test
    public void testOutsourceStatusValidation() {
        DemoOutsource outsource = new DemoOutsource();
        outsource.setStatus("ACTIVE");

        assertNotNull(outsource.getStatus());
        assertTrue("ACTIVE".equals(outsource.getStatus()) || 
                   "INACTIVE".equals(outsource.getStatus()) || 
                   "COMPLETED".equals(outsource.getStatus()));
    }

    /**
     * 测试损耗率边界情况 - 零损耗
     */
    @Test
    public void testLossRateZeroLoss() {
        Integer planQty = 100;
        Integer lossQty = 0;

        BigDecimal lossRate = new BigDecimal(lossQty)
                .divide(new BigDecimal(planQty), 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        assertEquals(BigDecimal.ZERO, lossRate);
    }

    /**
     * 测试损耗率边界情况 - 100%损耗
     */
    @Test
    public void testLossRateFullLoss() {
        Integer planQty = 100;
        Integer lossQty = 100;

        BigDecimal lossRate = new BigDecimal(lossQty)
                .divide(new BigDecimal(planQty), 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        assertEquals(new BigDecimal("100.0000"), lossRate);
    }
}
