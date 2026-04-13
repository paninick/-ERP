package com.ruoyi.demo.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * 款式表
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
@Data
@TableName("demo_style")
public class DemoStyle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 款式ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 款号 */
    @NotBlank(message = "款号不能为空")
    @Size(max = 50, message = "款号长度不能超过50个字符")
    private String styleNo;

    /** 款式名称 */
    @NotBlank(message = "款式名称不能为空")
    @Size(max = 200, message = "款式名称长度不能超过200个字符")
    private String styleName;

    /** 品类 */
    @NotBlank(message = "品类不能为空")
    @Size(max = 50, message = "品类长度不能超过50个字符")
    private String category;

    /** 季节 */
    @Size(max = 20, message = "季节长度不能超过20个字符")
    private String season;

    /** 面料费 */
    @Digits(integer = 10, fraction = 4, message = "面料费格式不正确，整数位最多10位，小数位最多4位")
    @DecimalMin(value = "0", message = "面料费不能为负数")
    private BigDecimal fabricCost;

    /** 辅料费 */
    @Digits(integer = 10, fraction = 4, message = "辅料费格式不正确，整数位最多10位，小数位最多4位")
    @DecimalMin(value = "0", message = "辅料费不能为负数")
    private BigDecimal accessoryCost;

    /** CMT加工费 */
    @Digits(integer = 10, fraction = 4, message = "CMT加工费格式不正确，整数位最多10位，小数位最多4位")
    @DecimalMin(value = "0", message = "CMT加工费不能为负数")
    private BigDecimal cmtCost;

    /** 标准成本 */
    @Digits(integer = 10, fraction = 4, message = "标准成本格式不正确，整数位最多10位，小数位最多4位")
    @DecimalMin(value = "0", message = "标准成本不能为负数")
    private BigDecimal standardCost;

    /** 目标利润率 */
    @Digits(integer = 3, fraction = 4, message = "目标利润率格式不正确，整数位最多3位，小数位最多4位")
    @DecimalMin(value = "-100", message = "目标利润率不能小于-100%")
    @DecimalMax(value = "100", message = "目标利润率不能大于100%")
    private BigDecimal profitRate;

    /** 删除标志 */
    @TableLogic
    private String delFlag;
}
