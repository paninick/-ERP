package com.ruoyi.demo.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * 库存管理表
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
@Data
@TableName("demo_inventory")
public class DemoInventory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 库存ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 工厂ID */
    @NotNull(message = "工厂ID不能为空")
    private Long factoryId;

    /** 关联款式ID */
    private Long styleId;

    /** 库位编号 */
    @NotBlank(message = "库位编号不能为空")
    @Size(max = 50, message = "库位编号长度不能超过50个字符")
    private String locationNo;

    /** 物料类型（FABRIC面料 ACCESSORY辅料 FINISHED成品） */
    @NotBlank(message = "物料类型不能为空")
    @Size(max = 20, message = "物料类型长度不能超过20个字符")
    private String materialType;

    /** 物料编号 */
    @NotBlank(message = "物料编号不能为空")
    @Size(max = 50, message = "物料编号长度不能超过50个字符")
    private String materialNo;

    /** 物料名称 */
    @NotBlank(message = "物料名称不能为空")
    @Size(max = 200, message = "物料名称长度不能超过200个字符")
    private String materialName;

    /** 规格型号 */
    @Size(max = 100, message = "规格型号长度不能超过100个字符")
    private String specification;

    /** 单位 */
    @NotBlank(message = "单位不能为空")
    @Size(max = 20, message = "单位长度不能超过20个字符")
    private String unit;

    /** 数量 */
    @NotNull(message = "数量不能为空")
    @Min(value = 0, message = "数量不能小于0")
    private Integer quantity;

    /** 单价 */
    @Digits(integer = 12, fraction = 4, message = "单价格式不正确")
    @DecimalMin(value = "0.0000", message = "单价不能小于0")
    private BigDecimal unitPrice;

    /** 总价值 */
    @Digits(integer = 14, fraction = 2, message = "总价值格式不正确")
    @DecimalMin(value = "0.00", message = "总价值不能小于0")
    private BigDecimal totalValue;

    /** 最低库存预警 */
    @Min(value = 0, message = "最低库存预警不能小于0")
    private Integer minStock;

    /** 最高库存预警 */
    @Min(value = 0, message = "最高库存预警不能小于0")
    private Integer maxStock;

    /** 状态（NORMAL正常 LOCKED锁定） */
    @Size(max = 20, message = "状态长度不能超过20个字符")
    private String status;

    /** 删除标志 */
    @TableLogic
    private String delFlag;
}
