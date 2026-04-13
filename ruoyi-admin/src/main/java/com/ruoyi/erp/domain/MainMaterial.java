package com.ruoyi.erp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 主料对象 t_erp_main_material
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public class MainMaterial extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 主料类型 */
    @Excel(name = "主料类型")
    private String mainMaterialType;

    /** 主料编号 */
    @Excel(name = "主料编号")
    private String mainMaterialNo;

    /** 供货方式 */
    @Excel(name = "供货方式")
    private String supplyMethod;

    /** 厂供编号 */
    @Excel(name = "厂供编号")
    private String factoryNo;

    /** 主料供应商id */
    @Excel(name = "主料供应商id")
    private Long supplierId;

    /** 主料品名 */
    @Excel(name = "主料品名")
    private String name;

    /** 主料成分 */
    @Excel(name = "主料成分")
    private String composition;

    /** 主料门幅 */
    @Excel(name = "主料门幅")
    private String width;

    /** 主料克重 */
    @Excel(name = "主料克重")
    private String weight;

    /** 主料纱支 */
    @Excel(name = "主料纱支")
    private String yarnCount;

    /** 计量单位 */
    @Excel(name = "计量单位")
    private String unit;

    /** 图片 */
    @Excel(name = "图片")
    private String pictrueUrl;

    /** 单价 - 跳过 */
    @Excel(name = "单价")
    private Double price;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setMainMaterialType(String mainMaterialType) {
        this.mainMaterialType = mainMaterialType;
    }

    public String getMainMaterialType() {
        return mainMaterialType;
    }
    public void setMainMaterialNo(String mainMaterialNo) {
        this.mainMaterialNo = mainMaterialNo;
    }

    public String getMainMaterialNo() {
        return mainMaterialNo;
    }
    public void setSupplyMethod(String supplyMethod) {
        this.supplyMethod = supplyMethod;
    }

    public String getSupplyMethod() {
        return supplyMethod;
    }

    public String getFactoryNo() {
        return factoryNo;
    }

    public void setFactoryNo(String factoryNo) {
        this.factoryNo = factoryNo;
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
    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getComposition() {
        return composition;
    }
    public void setWidth(String width) {
        this.width = width;
    }

    public String getWidth() {
        return width;
    }
    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWeight() {
        return weight;
    }
    public void setYarnCount(String yarnCount) {
        this.yarnCount = yarnCount;
    }

    public String getYarnCount() {
        return yarnCount;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
    public void setPictrueUrl(String pictrueUrl) {
        this.pictrueUrl = pictrueUrl;
    }

    public String getPictrueUrl() {
        return pictrueUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("mainMaterialType", getMainMaterialType())
            .append("mainMaterialNo", getMainMaterialNo())
            .append("supplyMethod", getSupplyMethod())
            .append("factoryNo", getFactoryNo())
            .append("supplierId", getSupplierId())
            .append("name", getName())
            .append("composition", getComposition())
            .append("width", getWidth())
            .append("weight", getWeight())
            .append("yarnCount", getYarnCount())
            .append("unit", getUnit())
            .append("pictrueUrl", getPictrueUrl())
            .append("price", getPrice())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
