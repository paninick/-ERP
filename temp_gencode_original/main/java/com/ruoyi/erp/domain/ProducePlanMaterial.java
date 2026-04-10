package com.ruoyi.erp.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 生产计划物料明细对象 t_erp_produce_plan_material
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
public class ProducePlanMaterial extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 销售订单id */
    @Excel(name = "销售订单id")
    private Long planId;

    /** 销售明细id */
    @Excel(name = "销售明细id")
    private Long salesItemId;

    /** 材料类型 1-主料 2-辅料 */
    @Excel(name = "材料类型 1-主料 2-辅料")
    private String materialType;

    /** 主料id */
    @Excel(name = "主料id")
    private Long materialId;

    /** 颜色 */
    @Excel(name = "颜色")
    private String color;

    /** 单耗 */
    @Excel(name = "单耗")
    private BigDecimal unitConsumption;

    /** 损耗方式 */
    @Excel(name = "损耗方式")
    private String lossType;

    /** 损耗 */
    @Excel(name = "损耗")
    private BigDecimal wastage;

    /** 排产数量 */
    @Excel(name = "排产数量")
    private BigDecimal planQuantity;

    /** 需求总量 */
    @Excel(name = "需求总量")
    private BigDecimal totalQuantity;

    /** 状态 */
    @Excel(name = "状态")
    private String materialStatus;

    /** 库存状态 */
    @Excel(name = "库存状态")
    private String inventoryStatus;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getPlanId() {
        return planId;
    }
    public void setSalesItemId(Long salesItemId) {
        this.salesItemId = salesItemId;
    }

    public Long getSalesItemId() {
        return salesItemId;
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
    public void setUnitConsumption(BigDecimal unitConsumption) {
        this.unitConsumption = unitConsumption;
    }

    public BigDecimal getUnitConsumption() {
        return unitConsumption;
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
    public void setPlanQuantity(BigDecimal planQuantity) {
        this.planQuantity = planQuantity;
    }

    public BigDecimal getPlanQuantity() {
        return planQuantity;
    }
    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }
    public void setMaterialStatus(String materialStatus) {
        this.materialStatus = materialStatus;
    }

    public String getMaterialStatus() {
        return materialStatus;
    }
    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("planId", getPlanId())
            .append("salesItemId", getSalesItemId())
            .append("materialType", getMaterialType())
            .append("materialId", getMaterialId())
            .append("color", getColor())
            .append("unitConsumption", getUnitConsumption())
            .append("lossType", getLossType())
            .append("wastage", getWastage())
            .append("planQuantity", getPlanQuantity())
            .append("totalQuantity", getTotalQuantity())
            .append("materialStatus", getMaterialStatus())
            .append("inventoryStatus", getInventoryStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
