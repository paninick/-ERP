package com.ruoyi.erp.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 入库明细对象 t_erp_stock_in_item
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public class StockInItem extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 入库编号 */
    private Long inId;

    /** 入库单号 */
    @Excel(name = "入库单号")
    private String sn;

    /** 产品id */
    @Excel(name = "产品id")
    private Long materialId;

    /** 1 主料， 2辅料 */
    @Excel(name = "1 主料， 2辅料")
    private String materialType;

    /** 采购价 */
    private BigDecimal purchasePrice;

    /** 售价 */
    private BigDecimal price;

    /** 数量 */
    private BigDecimal count;

    /** 生产日期 */
    private Date birthingTime;

    /** 仓库 */
    @Excel(name = "仓库")
    private Long warehouseId;

    /** 库区 */
    @Excel(name = "库区")
    private Long warehouseAreaId;

    /** 库位 */
    private Long warehouseLocationId;

    /** 存放位置 */
    private String saveLocation;

    /** 入库小计 */
    private BigDecimal inTotalPrice;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setInId(Long inId) {
        this.inId = inId;
    }

    public Long getInId() {
        return inId;
    }
    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getSn() {
        return sn;
    }
    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Long getMaterialId() {
        return materialId;
    }
    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getMaterialType() {
        return materialType;
    }
    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public BigDecimal getCount() {
        return count;
    }
    public void setBirthingTime(Date birthingTime) {
        this.birthingTime = birthingTime;
    }

    public Date getBirthingTime() {
        return birthingTime;
    }
    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }
    public void setWarehouseAreaId(Long warehouseAreaId) {
        this.warehouseAreaId = warehouseAreaId;
    }

    public Long getWarehouseAreaId() {
        return warehouseAreaId;
    }
    public void setWarehouseLocationId(Long warehouseLocationId) {
        this.warehouseLocationId = warehouseLocationId;
    }

    public Long getWarehouseLocationId() {
        return warehouseLocationId;
    }
    public void setSaveLocation(String saveLocation) {
        this.saveLocation = saveLocation;
    }

    public String getSaveLocation() {
        return saveLocation;
    }
    public void setInTotalPrice(BigDecimal inTotalPrice) {
        this.inTotalPrice = inTotalPrice;
    }

    public BigDecimal getInTotalPrice() {
        return inTotalPrice;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("inId", getInId())
            .append("sn", getSn())
            .append("materialId", getMaterialId())
            .append("materialType", getMaterialType())
            .append("purchasePrice", getPurchasePrice())
            .append("price", getPrice())
            .append("count", getCount())
            .append("birthingTime", getBirthingTime())
            .append("warehouseId", getWarehouseId())
            .append("warehouseAreaId", getWarehouseAreaId())
            .append("warehouseLocationId", getWarehouseLocationId())
            .append("saveLocation", getSaveLocation())
            .append("inTotalPrice", getInTotalPrice())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
