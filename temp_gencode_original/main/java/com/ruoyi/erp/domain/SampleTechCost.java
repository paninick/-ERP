package com.ruoyi.erp.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工艺书单耗对象 t_erp_sample_tech_cost
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
public class SampleTechCost extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 打样id */
    @Excel(name = "打样id")
    private Long noticeId;

    /** 工艺书id */
    @Excel(name = "工艺书id")
    private Long techId;

    /** 材料类型 */
    @Excel(name = "材料类型")
    private String materialType;

    /** 主料id */
    @Excel(name = "主料id")
    private Long materialId;

    /** 颜色 */
    @Excel(name = "颜色")
    private String color;

    /** 单耗 */
    @Excel(name = "单耗")
    private BigDecimal unitConsumption​;

    /** 损耗方式 */
    @Excel(name = "损耗方式")
    private String lossType;

    /** 损耗 */
    @Excel(name = "损耗")
    private BigDecimal wastage;

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
    public void setTechId(Long techId) {
        this.techId = techId;
    }

    public Long getTechId() {
        return techId;
    }
    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getMaterialType() {
        return materialType;
    }
    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Long getMaterialId() {
        return materialId;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
    public void setUnitConsumption​(BigDecimal unitConsumption​) {
        this.unitConsumption​ = unitConsumption​;
    }

    public BigDecimal getUnitConsumption​() {
        return unitConsumption​;
    }
    public void setLossType(String lossType) {
        this.lossType = lossType;
    }

    public String getLossType() {
        return lossType;
    }
    public void setWastage(BigDecimal wastage) {
        this.wastage = wastage;
    }

    public BigDecimal getWastage() {
        return wastage;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("noticeId", getNoticeId())
            .append("techId", getTechId())
            .append("materialType", getMaterialType())
            .append("materialId", getMaterialId())
            .append("color", getColor())
            .append("unitConsumption​", getUnitConsumption​())
            .append("lossType", getLossType())
            .append("wastage", getWastage())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
