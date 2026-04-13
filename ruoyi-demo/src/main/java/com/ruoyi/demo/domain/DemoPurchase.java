package com.ruoyi.demo.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * 采购管理表
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
@Data
@TableName("demo_purchase")
public class DemoPurchase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 采购ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 工厂ID */
    @NotNull(message = "工厂ID不能为空")
    private Long factoryId;

    /** 采购单号 */
    @NotBlank(message = "采购单号不能为空")
    @Size(max = 50, message = "采购单号长度不能超过50个字符")
    private String purchaseNo;

    /** 供应商ID */
    private Long supplierId;

    /** 供应商名称 */
    @Size(max = 200, message = "供应商名称长度不能超过200个字符")
    private String supplierName;

    /** 物料类型（FABRIC面料 ACCESSORY辅料） */
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
    @Min(value = 1, message = "数量必须大于0")
    private Integer quantity;

    /** 单价 */
    @NotNull(message = "单价不能为空")
    @Digits(integer = 12, fraction = 4, message = "单价格式不正确")
    @DecimalMin(value = "0.0000", message = "单价不能小于0")
    private BigDecimal unitPrice;

    /** 总金额 */
    @NotNull(message = "总金额不能为空")
    @Digits(integer = 14, fraction = 2, message = "总金额格式不正确")
    @DecimalMin(value = "0.00", message = "总金额不能小于0")
    private BigDecimal totalAmount;

    /** 采购日期 */
    @NotBlank(message = "采购日期不能为空")
    @Size(max = 20, message = "采购日期长度不能超过20个字符")
    private String purchaseDate;

    /** 预计到货日期 */
    @Size(max = 20, message = "预计到货日期长度不能超过20个字符")
    private String expectedDate;

    /** 实际到货日期 */
    @Size(max = 20, message = "实际到货日期长度不能超过20个字符")
    private String actualDate;

    /** 状态（PENDING待收货 RECEIVED已收货 CANCELLED已取消） */
    @Size(max = 20, message = "状态长度不能超过20个字符")
    private String status;

    /** 删除标志 */
    @TableLogic
    private String delFlag;
}
