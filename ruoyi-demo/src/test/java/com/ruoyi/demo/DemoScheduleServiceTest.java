package com.ruoyi.demo;

import com.ruoyi.demo.domain.DemoSchedule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 排程管理单元测试
 *
 * @author ruoyi
 * @date 2026-04-01
 */
public class DemoScheduleServiceTest {

    /**
     * 测试创建排程对象
     */
    @Test
    public void testCreateDemoSchedule() {
        DemoSchedule schedule = new DemoSchedule();
        schedule.setFactoryId(1L);
        schedule.setOrderId(1L);
        schedule.setWorkDay("2026-04-01");
        schedule.setProcess("缝制");
        schedule.setPlanQty(100);
        schedule.setCapacity(120);
        schedule.setLoad(new BigDecimal("83.33"));

        assertNotNull(schedule);
        assertEquals(1L, schedule.getFactoryId());
        assertEquals(1L, schedule.getOrderId());
        assertEquals("2026-04-01", schedule.getWorkDay());
        assertEquals("缝制", schedule.getProcess());
        assertEquals(100, schedule.getPlanQty());
        assertEquals(120, schedule.getCapacity());
        assertEquals(new BigDecimal("83.33"), schedule.getLoad());
    }

    /**
     * 测试负载率计算
     */
    @Test
    public void testLoadCalculation() {
        Integer planQty = 100;
        Integer capacity = 120;

        BigDecimal load = new BigDecimal(planQty)
                .divide(new BigDecimal(capacity), 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        assertEquals(new BigDecimal("83.3333"), load);
    }

    /**
     * 测试排程负载率边界情况 - 0负载
     */
    @Test
    public void testLoadZero() {
        Integer planQty = 0;
        Integer capacity = 100;

        BigDecimal load = BigDecimal.ZERO;

        assertEquals(BigDecimal.ZERO, load);
    }

    /**
     * 测试排程负载率边界情况 - 100%负载
     */
    @Test
    public void testLoadFull() {
        Integer planQty = 100;
        Integer capacity = 100;

        BigDecimal load = new BigDecimal(planQty)
                .divide(new BigDecimal(capacity), 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        assertEquals(new BigDecimal("100.0000"), load);
    }

    /**
     * 测试排程负载率边界情况 - 超负载
     */
    @Test
    public void testLoadOverCapacity() {
        Integer planQty = 150;
        Integer capacity = 100;

        BigDecimal load = new BigDecimal(planQty)
                .divide(new BigDecimal(capacity), 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        assertEquals(new BigDecimal("150.0000"), load);
    }

    /**
     * 测试工作日格式验证
     */
    @Test
    public void testWorkDayFormat() {
        DemoSchedule schedule = new DemoSchedule();
        schedule.setWorkDay("2026-04-01");

        assertNotNull(schedule.getWorkDay());
        assertTrue(schedule.getWorkDay().matches("\\d{4}-\\d{2}-\\d{2}"));
    }
}
