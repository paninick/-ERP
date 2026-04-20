package com.ruoyi.erp.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 采购明细对象 t_erp_purchase_item
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public class PurchaseItem extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 采购单id */
    @Excel(name = "采购单id")
    private Long purchaseId;

    /** 采购单号 */
    @Excel(name = "采购单号")
    private String sn;

    /** 主料id */
    @Excel(name = "主料id")
    private Long materialId;

    /** 1 主料， 2辅料 */
    @Excel(name = "1 主料， 2辅料")
    private String materialType;

    /** 采购数量 */
    @Excel(name = "采购数量")
    private BigDecimal purchaseCount;

    /** 采购单价 */
    @Excel(name = "采购单价")
    private BigDecimal purchasePrice;

    /** 已执行数量（已入库） */
    private BigDecimal executeQty;

    /** 入库状态（0待入库） */
    private String status;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Long getPurchaseId() {
        return purchaseId;
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
    public void setPurchaseCount(BigDecimal purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public BigDecimal getPurchaseCount() {
        return purchaseCount;
    }
    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public BigDecimal getExecuteQty() {
        return executeQty;
    }

    public void setExecuteQty(BigDecimal executeQty) {
        this.executeQty = executeQty;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("purchaseId", getPurchaseId())
            .append("sn", getSn())
            .append("materialId", getMaterialId())
            .append("materialType", getMaterialType())
            .append("purchaseCount", getPurchaseCount())
            .append("purchasePrice", getPurchasePrice())
            .append("executeQty", getExecuteQty())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
