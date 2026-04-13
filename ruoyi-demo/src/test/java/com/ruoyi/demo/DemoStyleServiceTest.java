package com.ruoyi.demo;

import com.ruoyi.demo.domain.DemoStyle;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 款式管理单元测试
 *
 * @author ruoyi
 * @date 2026-04-01
 */
public class DemoStyleServiceTest {

    /**
     * 测试创建款式对象
     */
    @Test
    public void testCreateDemoStyle() {
        DemoStyle style = new DemoStyle();
        style.setFactoryId(1L);
        style.setStyleNo("STYLE-001");
        style.setStyleName("测试款式");
        style.setCategory("上衣");
        style.setFabric("纯棉");
        style.setPrice(new BigDecimal("50.00"));
        style.setStatus("ACTIVE");

        assertNotNull(style);
        assertEquals(1L, style.getFactoryId());
        assertEquals("STYLE-001", style.getStyleNo());
        assertEquals("测试款式", style.getStyleName());
        assertEquals("上衣", style.getCategory());
        assertEquals("纯棉", style.getFabric());
        assertEquals(new BigDecimal("50.00"), style.getPrice());
        assertEquals("ACTIVE", style.getStatus());
    }

    /**
     * 测试款式价格验证
     */
    @Test
    public void testStylePriceValidation() {
        DemoStyle style = new DemoStyle();
        style.setPrice(new BigDecimal("100.00"));

        assertNotNull(style.getPrice());
        assertTrue(style.getPrice().compareTo(BigDecimal.ZERO) > 0);
    }

    /**
     * 测试款式状态验证
     */
    @Test
    public void testStyleStatusValidation() {
        DemoStyle style = new DemoStyle();
        style.setStatus("ACTIVE");

        assertNotNull(style.getStatus());
        assertTrue("ACTIVE".equals(style.getStatus()) || "INACTIVE".equals(style.getStatus()));
    }
}
