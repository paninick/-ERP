package com.ruoyi.erp.domain;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 出库明细对象 t_erp_stock_out_item
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public class StockOutItem extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 出库编号 */
    @Excel(name = "出库编号")
    private Long outId;

    /** 出库单号 */
    @Excel(name = "出库单号")
    private String sn;

    /** 产品id，材料出库时 */
    @Excel(name = "产品id，材料出库时")
    private Long materialId;

    /** 1 主料， 2辅料 */
    @Excel(name = "1 主料， 2辅料")
    private String materialType;

    /** 编号（材料就是材料编号，成品就是打样通知时的款号） */
    @Excel(name = "编号", readConverterExp = "材=料就是材料编号，成品就是打样通知时的款号")
    private String materialNo;

    /** 材料名称（材料就是材料名称，成品就是品类） */
    @Excel(name = "材料名称", readConverterExp = "材=料就是材料名称，成品就是品类")
    private String name;

    /** 出库数量 */
    @Excel(name = "出库数量")
    private BigDecimal count;

    /** 主料成分 */
    @Excel(name = "主料成分")
    private String composition;

    /** 主料门幅 */
    @Excel(name = "主料门幅")
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

    /** 仓库 */
    @Excel(name = "仓库")
    private Long warehouseId;

    /** 库区 */
    @Excel(name = "库区")
    private Long warehouseAreaId;

    /** 库位 */
    @Excel(name = "库位")
    private Long warehouseLocationId;

    /** 存放位置 */
    @Excel(name = "存放位置")
    private String saveLocation;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setOutId(Long outId) {
        this.outId = outId;
    }

    public Long getOutId() {
        return outId;
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
    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public BigDecimal getCount() {
        return count;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("outId", getOutId())
            .append("sn", getSn())
            .append("materialId", getMaterialId())
            .append("materialType", getMaterialType())
            .append("materialNo", getMaterialNo())
            .append("name", getName())
            .append("count", getCount())
            .append("composition", getComposition())
            .append("width", getWidth())
            .append("weight", getWeight())
            .append("substance", getSubstance())
            .append("size", getSize())
            .append("warehouseId", getWarehouseId())
            .append("warehouseAreaId", getWarehouseAreaId())
            .append("warehouseLocationId", getWarehouseLocationId())
            .append("saveLocation", getSaveLocation())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
