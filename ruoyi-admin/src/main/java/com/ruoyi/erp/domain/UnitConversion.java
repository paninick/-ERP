package com.ruoyi.erp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 单位换算对象 t_erp_unit_conversion
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public class UnitConversion extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 换算ID */
    private Long id;

    /** 物料ID */
    @Excel(name = "物料ID")
    private Long materialId;

    /** 采购单位 */
    @Excel(name = "采购单位")
    private String purchaseUnit;

    /** 库存单位 */
    @Excel(name = "库存单位")
    private String stockUnit;

    /** 生产单位 */
    @Excel(name = "生产单位")
    private String produceUnit;

    /** 采购转库存换算率 */
    @Excel(name = "采购转库存换算率")
    private java.math.BigDecimal purchaseToStock;

    /** 库存转生产换算率 */
    @Excel(name = "库存转生产换算率")
    private java.math.BigDecimal stockToProduce;

    /** 备注 */
    private String remark;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setPurchaseUnit(String purchaseUnit) {
        this.purchaseUnit = purchaseUnit;
    }

    public String getPurchaseUnit() {
        return purchaseUnit;
    }

    public void setStockUnit(String stockUnit) {
        this.stockUnit = stockUnit;
    }

    public String getStockUnit() {
        return stockUnit;
    }

    public void setProduceUnit(String produceUnit) {
        this.produceUnit = produceUnit;
    }

    public String getProduceUnit() {
        return produceUnit;
    }

    public void setPurchaseToStock(java.math.BigDecimal purchaseToStock) {
        this.purchaseToStock = purchaseToStock;
    }

    public java.math.BigDecimal getPurchaseToStock() {
        return purchaseToStock;
    }

    public void setStockToProduce(java.math.BigDecimal stockToProduce) {
        this.stockToProduce = stockToProduce;
    }

    public java.math.BigDecimal getStockToProduce() {
        return stockToProduce;
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
            .append("materialId", getMaterialId())
            .append("purchaseUnit", getPurchaseUnit())
            .append("stockUnit", getStockUnit())
            .append("produceUnit", getProduceUnit())
            .append("purchaseToStock", getPurchaseToStock())
            .append("stockToProduce", getStockToProduce())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
