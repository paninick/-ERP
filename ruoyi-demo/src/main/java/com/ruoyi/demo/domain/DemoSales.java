package com.ruoyi.demo.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * 销售管理表
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
@Data
@TableName("demo_sales")
public class DemoSales extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 销售ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 工厂ID */
    @NotNull(message = "工厂ID不能为空")
    private Long factoryId;

    /** 销售单号 */
    @NotBlank(message = "销售单号不能为空")
    @Size(max = 50, message = "销售单号长度不能超过50个字符")
    private String salesNo;

    /** 关联订单ID */
    private Long orderId;

    /** 客户ID */
    private Long customerId;

    /** 客户名称 */
    @Size(max = 200, message = "客户名称长度不能超过200个字符")
    private String customerName;

    /** 款号 */
    @Size(max = 50, message = "款号长度不能超过50个字符")
    private String styleNo;

    /** 款式名称 */
    @Size(max = 200, message = "款式名称长度不能超过200个字符")
    private String styleName;

    /** 数量 */
    @NotNull(message = "数量不能为空")
    @Min(value = 1, message = "数量必须大于0")
    private Integer quantity;

    /** 单价（FOB/CIF/CNF/EXW） */
    @NotNull(message = "单价不能为空")
    @Digits(integer = 12, fraction = 2, message = "单价格式不正确")
    @DecimalMin(value = "0.00", message = "单价不能小于0")
    private BigDecimal unitPrice;

    /** 总金额 */
    @NotNull(message = "总金额不能为空")
    @Digits(integer = 14, fraction = 2, message = "总金额格式不正确")
    @DecimalMin(value = "0.00", message = "总金额不能小于0")
    private BigDecimal totalAmount;

    /** 价格条款（FOB CIF CNF EXW） */
    @NotBlank(message = "价格条款不能为空")
    @Size(max = 10, message = "价格条款长度不能超过10个字符")
    private String priceTerm;

    /** 销售日期 */
    @NotBlank(message = "销售日期不能为空")
    @Size(max = 20, message = "销售日期长度不能超过20个字符")
    private String salesDate;

    /** 预计发货日期 */
    @Size(max = 20, message = "预计发货日期长度不能超过20个字符")
    private String expectedShipDate;

    /** 实际发货日期 */
    @Size(max = 20, message = "实际发货日期长度不能超过20个字符")
    private String actualShipDate;

    /** 状态（PENDING待发货 SHIPPED已发货 DELIVERED已签收 CANCELLED已取消） */
    @Size(max = 20, message = "状态长度不能超过20个字符")
    private String status;

    /** 删除标志 */
    @TableLogic
    private String delFlag;
}
