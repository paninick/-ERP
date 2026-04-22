package com.ruoyi.demo.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 排程表
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
@Data
@TableName("demo_schedule")
public class DemoSchedule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 排程ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 工厂ID */
    @NotNull(message = "工厂ID不能为空")
    private Long factoryId;

    /** 订单ID */
    @NotNull(message = "订单ID不能为空")
    private Long orderId;

    /** 工作日 */
    @NotBlank(message = "工作日不能为空")
    @Size(max = 20, message = "工作日长度不能超过20个字符")
    private String workDay;

    /** 工序 */
    @NotBlank(message = "工序不能为空")
    @Size(max = 100, message = "工序长度不能超过100个字符")
    private String process;

    /** 计划数量 */
    @NotNull(message = "计划数量不能为空")
    @Min(value = 1, message = "计划数量必须大于0")
    private Integer planQty;

    /** 产能 */
    @Min(value = 0, message = "产能不能为负数")
    private Integer capacity;

    /** 负载率 */
    @Digits(integer = 5, fraction = 4, message = "负载率格式不正确，整数位最多5位，小数位最多4位")
    @DecimalMin(value = "0", message = "负载率不能为负数")
    @DecimalMax(value = "100", message = "负载率不能大于100%")
    private BigDecimal load;

    /** 优先级（1最高-10最低） */
    @Min(value = 1, message = "优先级最小为1")
    @Max(value = 10, message = "优先级最大为10")
    private Integer priority;

    /** 计划开始日期 */
    private LocalDate startDate;

    /** 计划完成日期 */
    private LocalDate dueDate;

    /** 排单状态（0待排 1已排 2进行中 3已完成） */
    @Size(max = 1, message = "排单状态长度不能超过1个字符")
    private String scheduleStatus;

    /** 冲突标志（0无冲突 1产能冲突 2日期冲突） */
    @Size(max = 1, message = "冲突标志长度不能超过1个字符")
    private String conflictFlag;

    /** 删除标志 */
    @TableLogic
    private String delFlag;
}
