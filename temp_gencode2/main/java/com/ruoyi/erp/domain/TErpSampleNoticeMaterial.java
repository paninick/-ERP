package com.ruoyi.erp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 打样通知-面辅料要求对象 t_erp_sample_notice_material
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
public class TErpSampleNoticeMaterial extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 打样通知id */
    @Excel(name = "打样通知id")
    private Long noticeId;

    /** 主副类型 */
    @Excel(name = "主副类型")
    private String materialType;

    /** 供货方式 字典1厂供,2客供 */
    @Excel(name = "供货方式 字典1厂供,2客供")
    private String supplyMethod;

    /** 产品id */
    @Excel(name = "产品id")
    private Long materialId;

    /** 编号 */
    @Excel(name = "编号")
    private String materialNo;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 主料成分 */
    @Excel(name = "主料成分")
    private String composition;

    /** 门幅 */
    @Excel(name = "门幅")
    private String width;

    /** 主料克重 */
    @Excel(name = "主料克重")
    private String weight;

    /** 辅料成分 */
    @Excel(name = "辅料成分")
    private String substance;

    /** 辅料规格 */
    @Excel(name = "辅料规格")
    private String size;

    /** 计量单位 */
    @Excel(name = "计量单位")
    private String unit;

    /** 颜色 */
    @Excel(name = "颜色")
    private String color;

    /** 主/辅料要求 */
    @Excel(name = "主/辅料要求")
    private String requirement;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getNoticeId() {
        return noticeId;
    }
    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getMaterialType() {
        return materialType;
    }
    public void setSupplyMethod(String supplyMethod) {
        this.supplyMethod = supplyMethod;
    }

    public String getSupplyMethod() {
        return supplyMethod;
    }
    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Long getMaterialId() {
        return materialId;
    }
    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    public String getMaterialNo() {
        return materialNo;
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
    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getRequirement() {
        return requirement;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("noticeId", getNoticeId())
            .append("materialType", getMaterialType())
            .append("supplyMethod", getSupplyMethod())
            .append("materialId", getMaterialId())
            .append("materialNo", getMaterialNo())
            .append("name", getName())
            .append("composition", getComposition())
            .append("width", getWidth())
            .append("weight", getWeight())
            .append("substance", getSubstance())
            .append("size", getSize())
            .append("unit", getUnit())
            .append("color", getColor())
            .append("requirement", getRequirement())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
