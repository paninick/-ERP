package com.ruoyi.demo.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * 财务记录表
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
@Data
@TableName("demo_finance")
public class DemoFinance extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 工厂ID */
    @NotNull(message = "工厂ID不能为空")
    private Long factoryId;

    /** 关联订单ID */
    private Long orderId;

    /** 财务编号 */
    @NotBlank(message = "财务编号不能为空")
    @Size(max = 50, message = "财务编号长度不能超过50个字符")
    private String financeNo;

    /** 财务类型（REVENUE收入 COST成本 EXPENSE费用） */
    @NotBlank(message = "财务类型不能为空")
    @Size(max = 20, message = "财务类型长度不能超过20个字符")
    private String financeType;

    /** 金额 */
    @NotNull(message = "金额不能为空")
    @Digits(integer = 12, fraction = 2, message = "金额格式不正确")
    @DecimalMin(value = "0.00", message = "金额不能小于0")
    private BigDecimal amount;

    /** 币种 */
    @Size(max = 10, message = "币种长度不能超过10个字符")
    private String currency;

    /** 交易日期 */
    @NotBlank(message = "交易日期不能为空")
    @Size(max = 20, message = "交易日期长度不能超过20个字符")
    private String transDate;

    /** 描述 */
    @Size(max = 500, message = "描述长度不能超过500个字符")
    private String description;

    /** 删除标志 */
    @TableLogic
    private String delFlag;
}
