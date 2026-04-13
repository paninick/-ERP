package com.ruoyi.demo.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * 报工表
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
@Data
@TableName("demo_report")
public class DemoReport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 报工ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 工厂ID */
    @NotNull(message = "工厂ID不能为空")
    private Long factoryId;

    /** 订单ID */
    @NotNull(message = "订单ID不能为空")
    private Long orderId;

    /** 工序 */
    @NotBlank(message = "工序不能为空")
    @Size(max = 100, message = "工序长度不能超过100个字符")
    private String process;

    /** 日期 */
    @NotBlank(message = "日期不能为空")
    @Size(max = 20, message = "日期长度不能超过20个字符")
    private String workDate;

    /** 数量 */
    @NotNull(message = "数量不能为空")
    @Min(value = 1, message = "数量必须大于0")
    private Integer qty;

    /** 是否返工（0否 1是） */
    @NotBlank(message = "是否返工不能为空")
    @Size(max = 1, message = "是否返工长度不能超过1个字符")
    private String isRework;

    /** 返工成本 */
    @Digits(integer = 10, fraction = 4, message = "返工成本格式不正确，整数位最多10位，小数位最多4位")
    @DecimalMin(value = "0", message = "返工成本不能为负数")
    private BigDecimal reworkCost;

    /** 删除标志 */
    @TableLogic
    private String delFlag;
}
