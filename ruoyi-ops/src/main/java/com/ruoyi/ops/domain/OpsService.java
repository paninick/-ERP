package com.ruoyi.ops.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import jakarta.validation.constraints.*;

/**
 * 服务表
 * 
 * @author ruoyi
 * @date 2026-04-02
 */
@Data
@TableName("ops_service")
public class OpsService extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 服务名称 */
    @NotBlank(message = "服务名称不能为空")
    @Size(max = 100, message = "服务名称长度不能超过100个字符")
    private String name;

    /** 服务类型 */
    @NotBlank(message = "服务类型不能为空")
    @Size(max = 50, message = "服务类型长度不能超过50个字符")
    private String type;

    /** 状态 */
    @NotBlank(message = "状态不能为空")
    @Size(max = 20, message = "状态长度不能超过20个字符")
    private String status;

    /** 健康检查URL */
    @Size(max = 500, message = "健康检查URL长度不能超过500个字符")
    private String healthCheckUrl;

    /** 主机地址 */
    @Size(max = 100, message = "主机地址长度不能超过100个字符")
    private String host;

    /** 端口号 */
    private Integer port;

    /** 服务描述 */
    @Size(max = 500, message = "服务描述长度不能超过500个字符")
    private String description;

    /** 创建人 */
    @Size(max = 100, message = "创建人长度不能超过100个字符")
    private String createdBy;

    /** 更新人 */
    @Size(max = 100, message = "更新人长度不能超过100个字符")
    private String updatedBy;

    /** 备注 */
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;

    /** 删除标志 */
    @TableLogic
    private String delFlag;
}
