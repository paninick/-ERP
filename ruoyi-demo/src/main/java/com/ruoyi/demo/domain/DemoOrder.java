package com.ruoyi.demo.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * 订单表
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
@Data
@TableName("demo_order")
public class DemoOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 工厂ID */
    @NotNull(message = "工厂ID不能为空")
    private Long factoryId;

    /** 订单号 */
    @NotBlank(message = "订单号不能为空")
    @Size(max = 50, message = "订单号长度不能超过50个字符")
    private String orderNo;

    /** 款号 */
    @NotBlank(message = "款号不能为空")
    @Size(max = 50, message = "款号长度不能超过50个字符")
    private String styleNo;

    /** 款式名称 */
    @NotBlank(message = "款式名称不能为空")
    @Size(max = 200, message = "款式名称长度不能超过200个字符")
    private String styleName;

    /** 数量 */
    @NotNull(message = "数量不能为空")
    @Min(value = 1, message = "数量必须大于0")
    private Integer qty;

    /** FOB价格 */
    @Digits(integer = 10, fraction = 4, message = "FOB价格格式不正确，整数位最多10位，小数位最多4位")
    @DecimalMin(value = "0", message = "FOB价格不能为负数")
    private BigDecimal fobPrice;

    /** CIF价格 */
    @Digits(integer = 10, fraction = 4, message = "CIF价格格式不正确，整数位最多10位，小数位最多4位")
    @DecimalMin(value = "0", message = "CIF价格不能为负数")
    private BigDecimal cifPrice;

    /** CNF价格 */
    @Digits(integer = 10, fraction = 4, message = "CNF价格格式不正确，整数位最多10位，小数位最多4位")
    @DecimalMin(value = "0", message = "CNF价格不能为负数")
    private BigDecimal cnfPrice;

    /** EXW价格 */
    @Digits(integer = 10, fraction = 4, message = "EXW价格格式不正确，整数位最多10位，小数位最多4位")
    @DecimalMin(value = "0", message = "EXW价格不能为负数")
    private BigDecimal exwPrice;

    /** 收入 */
    @Digits(integer = 12, fraction = 4, message = "收入格式不正确，整数位最多12位，小数位最多4位")
    @DecimalMin(value = "0", message = "收入不能为负数")
    private BigDecimal revenue;

    /** 成本 */
    @Digits(integer = 12, fraction = 4, message = "成本格式不正确，整数位最多12位，小数位最多4位")
    @DecimalMin(value = "0", message = "成本不能为负数")
    private BigDecimal cost;

    /** 利润 */
    @Digits(integer = 12, fraction = 4, message = "利润格式不正确，整数位最多12位，小数位最多4位")
    private BigDecimal profit;

    /** 利润率 */
    @Digits(integer = 3, fraction = 4, message = "利润率格式不正确，整数位最多3位，小数位最多4位")
    @DecimalMin(value = "-100", message = "利润率不能小于-100%")
    @DecimalMax(value = "100", message = "利润率不能大于100%")
    private BigDecimal profitRate;

    /** 交货期（天） */
    @NotNull(message = "交货期不能为空")
    @Min(value = 1, message = "交货期必须大于0")
    @Max(value = 3650, message = "交货期不能超过3650天")
    private Integer dueDays;

    /** 状态 */
    @NotBlank(message = "状态不能为空")
    @Size(max = 20, message = "状态长度不能超过20个字符")
    private String status;

    /** 删除标志 */
    @TableLogic
    private String delFlag;
}
