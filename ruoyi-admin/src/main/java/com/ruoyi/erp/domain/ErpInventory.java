package com.ruoyi.erp.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 库存总表对象 t_erp_inv_stock
 *
 * @author zhangmingjian
 * @date 2026-04-20
 */
public class ErpInventory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 仓库ID */
    private Long warehouseId;

    /** SKU ID */
    private Long skuId;

    /** 物料ID */
    private Long materialId;

    /** 可用库存数量 */
    private BigDecimal invQty;

    /** 锁定库存数量 */
    private BigDecimal lockQty;

    /** 乐观锁版本 */
    private Integer version;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setInvQty(BigDecimal invQty) {
        this.invQty = invQty;
    }

    public BigDecimal getInvQty() {
        return invQty;
    }

    public void setLockQty(BigDecimal lockQty) {
        this.lockQty = lockQty;
    }

    public BigDecimal getLockQty() {
        return lockQty;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("warehouseId", getWarehouseId())
            .append("skuId", getSkuId())
            .append("materialId", getMaterialId())
            .append("invQty", getInvQty())
            .append("lockQty", getLockQty())
            .append("version", getVersion())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
