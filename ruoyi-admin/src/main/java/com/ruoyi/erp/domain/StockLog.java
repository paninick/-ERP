package com.ruoyi.erp.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 出入库流水对象 t_erp_stock_log
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public class StockLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 批号 */
    @Excel(name = "批号")
    private String sn;

    /** 关联id */
    @Excel(name = "关联id")
    private Long relationId;

    /** 来源单据类型 */
    private String srcBillType;

    /** 来源单据ID */
    private Long srcBillId;

    /** 来源单据号 */
    private String srcBillNo;

    /** SKU ID */
    private Long skuId;

    /** 材料id */
    @Excel(name = "材料id")
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

    /** 出入库类型：1:入库 2:出库 */
    @Excel(name = "出入库类型：1:入库 2:出库")
    private Long inOut;

    /** 入库/出库数量 */
    @Excel(name = "入库/出库数量")
    private BigDecimal count;

    /** 仓库 */
    private Long warehouseId;

    /** 库区 */
    private Long warehouseAreaId;

    /** 库位 */
    private Long warehouseLocationId;

    /** 存放位置 */
    private String saveLocation;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getSn() {
        return sn;
    }
    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public Long getRelationId() {
        return relationId;
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
    public void setInOut(Long inOut) {
        this.inOut = inOut;
    }

    public Long getInOut() {
        return inOut;
    }
    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public BigDecimal getCount() {
        return count;
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

    public String getSrcBillType() {
        return srcBillType;
    }

    public void setSrcBillType(String srcBillType) {
        this.srcBillType = srcBillType;
    }

    public Long getSrcBillId() {
        return srcBillId;
    }

    public void setSrcBillId(Long srcBillId) {
        this.srcBillId = srcBillId;
    }

    public String getSrcBillNo() {
        return srcBillNo;
    }

    public void setSrcBillNo(String srcBillNo) {
        this.srcBillNo = srcBillNo;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sn", getSn())
            .append("relationId", getRelationId())
            .append("srcBillType", getSrcBillType())
            .append("srcBillId", getSrcBillId())
            .append("srcBillNo", getSrcBillNo())
            .append("skuId", getSkuId())
            .append("materialId", getMaterialId())
            .append("materialType", getMaterialType())
            .append("materialNo", getMaterialNo())
            .append("name", getName())
            .append("inOut", getInOut())
            .append("count", getCount())
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
