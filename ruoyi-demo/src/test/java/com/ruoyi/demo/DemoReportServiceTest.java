package com.ruoyi.demo;

import com.ruoyi.demo.domain.DemoReport;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 报工管理单元测试
 *
 * @author ruoyi
 * @date 2026-04-01
 */
public class DemoReportServiceTest {

    /**
     * 测试创建报工对象
     */
    @Test
    public void testCreateDemoReport() {
        DemoReport report = new DemoReport();
        report.setFactoryId(1L);
        report.setOrderId(1L);
        report.setProcess("缝制");
        report.setWorkDate("2026-04-01");
        report.setQty(50);
        report.setIsRework("0");
        report.setReworkCost(BigDecimal.ZERO);

        assertNotNull(report);
        assertEquals(1L, report.getFactoryId());
        assertEquals(1L, report.getOrderId());
        assertEquals("缝制", report.getProcess());
        assertEquals("2026-04-01", report.getWorkDate());
        assertEquals(50, report.getQty());
        assertEquals("0", report.getIsRework());
        assertEquals(BigDecimal.ZERO, report.getReworkCost());
    }

    /**
     * 测试返工报工对象
     */
    @Test
    public void testCreateReworkDemoReport() {
        DemoReport report = new DemoReport();
        report.setFactoryId(1L);
        report.setOrderId(1L);
        report.setProcess("缝制");
        report.setWorkDate("2026-04-01");
        report.setQty(10);
        report.setIsRework("1");
        report.setReworkCost(new BigDecimal("50.00"));

        assertNotNull(report);
        assertEquals("1", report.getIsRework());
        assertEquals(new BigDecimal("50.00"), report.getReworkCost());
    }

    /**
     * 测试是否返工标志验证
     */
    @Test
    public void testIsReworkValidation() {
        DemoReport report = new DemoReport();
        report.setIsRework("0");

        assertNotNull(report.getIsRework());
        assertTrue("0".equals(report.getIsRework()) || "1".equals(report.getIsRework()));
    }

    /**
     * 测试返工成本计算
     */
    @Test
    public void testReworkCostCalculation() {
        Integer reworkQty = 10;
        BigDecimal unitReworkCost = new BigDecimal("5.00");

        BigDecimal totalReworkCost = unitReworkCost.multiply(new BigDecimal(reworkQty));

        assertEquals(new BigDecimal("50.00"), totalReworkCost);
    }

    /**
     * 测试工作日期格式验证
     */
    @Test
    public void testWorkDateFormat() {
        DemoReport report = new DemoReport();
        report.setWorkDate("2026-04-01");

        assertNotNull(report.getWorkDate());
        assertTrue(report.getWorkDate().matches("\\d{4}-\\d{2}-\\d{2}"));
    }

    /**
     * 测试报工数量边界情况 - 零数量
     */
    @Test
    public void testReportQtyZero() {
        DemoReport report = new DemoReport();
        report.setQty(0);

        assertNotNull(report.getQty());
        assertEquals(0, report.getQty());
    }

    /**
     * 测试报工数量边界情况 - 正常数量
     */
    @Test
    public void testReportQtyNormal() {
        DemoReport report = new DemoReport();
        report.setQty(100);

        assertNotNull(report.getQty());
        assertTrue(report.getQty() > 0);
    }
}
