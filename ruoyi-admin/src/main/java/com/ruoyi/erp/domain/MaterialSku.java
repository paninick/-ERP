package com.ruoyi.erp.domain;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 物料SKU实体
 * 按照Gemini设计：物料（SPU）下按颜色×尺码生成多个SKU
 * 每个SKU是实际可以入库/出库的独立实体
 *
 * @author ruoyi
 * @date 2026-04-16
 */
public class MaterialSku extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** SKU ID */
    private Long id;

    /** 物料ID（SPU） */
    private Long materialId;

    /** SKU编码（全局唯一） */
    private String skuCode;

    /** 辅助属性1 - 颜色（固定顺序不允许换） */
    private Long auxId1;

    /** 辅助属性2 - 尺码（固定顺序不允许换） */
    private Long auxId2;

    /** 辅助属性3 - 批次（可选） */
    private Long auxId3;

    /** SKU名称（物料名+颜色+尺码 */
    private String skuName;

    /** 状态 0正常 1停用 */
    private String status;

    /** 软删除标记 */
    private Long isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Long getAuxId1() {
        return auxId1;
    }

    public void setAuxId1(Long auxId1) {
        this.auxId1 = auxId1;
    }

    public Long getAuxId2() {
        return auxId2;
    }

    public void setAuxId2(Long auxId2) {
        this.auxId2 = auxId2;
    }

    public Long getAuxId3() {
        return auxId3;
    }

    public void setAuxId3(Long auxId3) {
        this.auxId3 = auxId3;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Long isDeleted) {
        this.isDeleted = isDeleted;
    }
}
