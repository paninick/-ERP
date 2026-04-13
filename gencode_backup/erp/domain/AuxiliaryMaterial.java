package com.ruoyi.erp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 辅料对象 t_erp_auxiliary_material
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public class AuxiliaryMaterial extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 辅料类型 */
    @Excel(name = "辅料类型")
    private String auxiliaryMaterialType;

    /** 辅料编号 */
    @Excel(name = "辅料编号")
    private String auxiliaryMaterialNo;

    /** 供货方式 */
    @Excel(name = "供货方式")
    private String supplyMethod;

    /** 供应商id */
    @Excel(name = "供应商id")
    private Long supplierId;

    /** 辅料品名 */
    @Excel(name = "辅料品名")
    private String name;

    /** 辅料成分 */
    @Excel(name = "辅料成分")
    private String substance;

    /** 辅料规格 */
    @Excel(name = "辅料规格")
    private String size;

    /** 计量单位 */
    @Excel(name = "计量单位")
    private String unit;

    /** 图片 */
    private String pictureUrl;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setAuxiliaryMaterialType(String auxiliaryMaterialType) {
        this.auxiliaryMaterialType = auxiliaryMaterialType;
    }

    public String getAuxiliaryMaterialType() {
        return auxiliaryMaterialType;
    }
    public void setAuxiliaryMaterialNo(String auxiliaryMaterialNo) {
        this.auxiliaryMaterialNo = auxiliaryMaterialNo;
    }

    public String getAuxiliaryMaterialNo() {
        return auxiliaryMaterialNo;
    }
    public void setSupplyMethod(String supplyMethod) {
        this.supplyMethod = supplyMethod;
    }

    public String getSupplyMethod() {
        return supplyMethod;
    }
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() {
        return supplierId;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setSubstance(String substance) {
        this.substance = substance;
    }

    public String getSubstance() {
        return substance;
    }
    public void setSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("auxiliaryMaterialType", getAuxiliaryMaterialType())
            .append("auxiliaryMaterialNo", getAuxiliaryMaterialNo())
            .append("supplyMethod", getSupplyMethod())
            .append("supplierId", getSupplierId())
            .append("name", getName())
            .append("substance", getSubstance())
            .append("size", getSize())
            .append("unit", getUnit())
            .append("pictureUrl", getPictureUrl())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
