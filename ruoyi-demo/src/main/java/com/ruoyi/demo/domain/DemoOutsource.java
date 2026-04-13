package com.ruoyi.demo.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * 外发订单表
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
@Data
@TableName("demo_outsource")
public class DemoOutsource extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 外发单ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 工厂ID */
    @NotNull(message = "工厂ID不能为空")
    private Long factoryId;

    /** 订单ID */
    @NotNull(message = "订单ID不能为空")
    private Long orderId;

    /** 加工厂名称 */
    @NotBlank(message = "加工厂名称不能为空")
    @Size(max = 200, message = "加工厂名称长度不能超过200个字符")
    private String factoryName;

    /** 工序 */
    @NotBlank(message = "工序不能为空")
    @Size(max = 100, message = "工序长度不能超过100个字符")
    private String process;

    /** 计划数量 */
    @NotNull(message = "计划数量不能为空")
    @Min(value = 1, message = "计划数量必须大于0")
    private Integer planQty;

    /** 发出数量 */
    @Min(value = 0, message = "发出数量不能为负数")
    private Integer sentQty;

    /** 实收数量 */
    @Min(value = 0, message = "实收数量不能为负数")
    private Integer receivedQty;

    /** 损耗数量 */
    @Min(value = 0, message = "损耗数量不能为负数")
    private Integer lossQty;

    /** 损耗率 */
    @Digits(integer = 5, fraction = 4, message = "损耗率格式不正确，整数位最多5位，小数位最多4位")
    @DecimalMin(value = "0", message = "损耗率不能为负数")
    @DecimalMax(value = "100", message = "损耗率不能大于100%")
    private BigDecimal lossRate;

    /** 加工单价 */
    @NotNull(message = "加工单价不能为空")
    @Digits(integer = 10, fraction = 4, message = "加工单价格式不正确，整数位最多10位，小数位最多4位")
    @DecimalMin(value = "0", message = "加工单价不能为负数")
    private BigDecimal unitPrice;

    /** 加工费总额 */
    @Digits(integer = 12, fraction = 4, message = "加工费总额格式不正确，整数位最多12位，小数位最多4位")
    @DecimalMin(value = "0", message = "加工费总额不能为负数")
    private BigDecimal totalAmount;

    /** 状态 */
    @NotBlank(message = "状态不能为空")
    @Size(max = 20, message = "状态长度不能超过20个字符")
    private String status;

    /** 删除标志 */
    @TableLogic
    private String delFlag;
}
