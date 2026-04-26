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
    private java.math.BigDecimal price;

    /** 最低库存预警 */
    @Excel(name = "最低库存预警")
    private java.math.BigDecimal minStockQty;

    /** 最高库存预警 */
    @Excel(name = "最高库存预警")
    private java.math.BigDecimal maxStockQty;

    /** 安全库存 */
    @Excel(name = "安全库存")
    private java.math.BigDecimal safeStockQty;

    /** 最小订货量 */
    @Excel(name = "最小订货量")
    private java.math.BigDecimal minOrderQty;

    /** 采购价 */
    @Excel(name = "采购价")
    private java.math.BigDecimal purchasePrice;

    /** 委外价 */
    @Excel(name = "委外价")
    private java.math.BigDecimal outsourcePrice;

    /** 标准成本 */
    @Excel(name = "标准成本")
    private java.math.BigDecimal standardCost;

    /** 零售价 */
    @Excel(name = "零售价")
    private java.math.BigDecimal retailPrice;

    /** 批发价 */
    @Excel(name = "批发价")
    private java.math.BigDecimal wholesalePrice;

    /** 当前库存 */
    @Excel(name = "当前库存")
    private java.math.BigDecimal currentStockQty;

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

    public java.math.BigDecimal getPrice() {
        return price;
    }

    public void setPrice(java.math.BigDecimal price) {
        this.price = price;
    }

    public java.math.BigDecimal getMinStockQty() {
        return minStockQty;
    }

    public void setMinStockQty(java.math.BigDecimal minStockQty) {
        this.minStockQty = minStockQty;
    }

    public java.math.BigDecimal getMaxStockQty() {
        return maxStockQty;
    }

    public void setMaxStockQty(java.math.BigDecimal maxStockQty) {
        this.maxStockQty = maxStockQty;
    }

    public java.math.BigDecimal getSafeStockQty() {
        return safeStockQty;
    }

    public void setSafeStockQty(java.math.BigDecimal safeStockQty) {
        this.safeStockQty = safeStockQty;
    }

    public java.math.BigDecimal getMinOrderQty() {
        return minOrderQty;
    }

    public void setMinOrderQty(java.math.BigDecimal minOrderQty) {
        this.minOrderQty = minOrderQty;
    }

    public java.math.BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(java.math.BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public java.math.BigDecimal getOutsourcePrice() {
        return outsourcePrice;
    }

    public void setOutsourcePrice(java.math.BigDecimal outsourcePrice) {
        this.outsourcePrice = outsourcePrice;
    }

    public java.math.BigDecimal getStandardCost() {
        return standardCost;
    }

    public void setStandardCost(java.math.BigDecimal standardCost) {
        this.standardCost = standardCost;
    }

    public java.math.BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(java.math.BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public java.math.BigDecimal getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(java.math.BigDecimal wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public java.math.BigDecimal getCurrentStockQty() { return currentStockQty; }
    public void setCurrentStockQty(java.math.BigDecimal v) { this.currentStockQty = v; }

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
            .append("minStockQty", getMinStockQty())
            .append("maxStockQty", getMaxStockQty())
            .append("safeStockQty", getSafeStockQty())
            .append("minOrderQty", getMinOrderQty())
            .append("purchasePrice", getPurchasePrice())
            .append("outsourcePrice", getOutsourcePrice())
            .append("standardCost", getStandardCost())
            .append("retailPrice", getRetailPrice())
            .append("wholesalePrice", getWholesalePrice())
            .append("currentStockQty", getCurrentStockQty())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
