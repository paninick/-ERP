package com.ruoyi.demo.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import jakarta.validation.constraints.*;

/**
 * 外发补料表
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
@Data
@TableName("demo_outsource_extra")
public class DemoOutsourceExtra extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 补料ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 外发单ID */
    @NotNull(message = "外发单ID不能为空")
    private Long outsourceId;

    /** 补料数量 */
    @NotNull(message = "补料数量不能为空")
    @Min(value = 1, message = "补料数量必须大于0")
    private Integer extraQty;

    /** 原因 */
    @NotBlank(message = "原因不能为空")
    @Size(max = 500, message = "原因长度不能超过500个字符")
    private String reason;

    /** 审批状态（0待审批 1已审批 2已拒绝） */
    @NotBlank(message = "审批状态不能为空")
    @Size(max = 1, message = "审批状态长度不能超过1个字符")
    private String approved;

    /** 审批人 */
    @Size(max = 50, message = "审批人长度不能超过50个字符")
    private String approvedBy;

    /** 审批时间 */
    @Size(max = 20, message = "审批时间长度不能超过20个字符")
    private String approvedTime;

    /** 删除标志 */
    @TableLogic
    private String delFlag;
}
