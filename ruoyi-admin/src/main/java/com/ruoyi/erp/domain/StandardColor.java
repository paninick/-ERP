package com.ruoyi.erp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 标准色卡对象 t_erp_standard_color
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public class StandardColor extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 色卡ID */
    private Long id;

    /** 颜色编码 */
    @Excel(name = "颜色编码")
    private String colorCode;

    /** 颜色名称 */
    @Excel(name = "颜色名称")
    private String colorName;

    /** LAB值 */
    @Excel(name = "LAB值")
    private String colorLab;

    /** 默认色差允差值ΔE */
    @Excel(name = "默认色差允差值ΔE")
    private java.math.BigDecimal defaultDeltaE;

    /** 色卡图片路径 */
    private String colorImage;

    /** 备注 */
    private String remark;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorLab(String colorLab) {
        this.colorLab = colorLab;
    }

    public String getColorLab() {
        return colorLab;
    }

    public void setDefaultDeltaE(java.math.BigDecimal defaultDeltaE) {
        this.defaultDeltaE = defaultDeltaE;
    }

    public java.math.BigDecimal getDefaultDeltaE() {
        return defaultDeltaE;
    }

    public void setColorImage(String colorImage) {
        this.colorImage = colorImage;
    }

    public String getColorImage() {
        return colorImage;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("colorCode", getColorCode())
            .append("colorName", getColorName())
            .append("colorLab", getColorLab())
            .append("defaultDeltaE", getDefaultDeltaE())
            .append("colorImage", getColorImage())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
