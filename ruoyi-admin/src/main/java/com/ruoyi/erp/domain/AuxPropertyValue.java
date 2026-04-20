package com.ruoyi.erp.domain;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 辅助属性值实体
 * 存储颜色、尺码、批次等具体属性值
 *
 * @author ruoyi
 * @date 2026-04-16
 */
public class AuxPropertyValue extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 属性值ID */
    private Long id;

    /** 辅助属性类型：1=color颜色, 2=size尺码, 3=batch批次 */
    private Integer auxType;

    /** 属性值编码 */
    private String valueCode;

    /** 属性值名称 */
    private String valueName;

    /** 排序 */
    private Integer sortOrder;

    /** 状态 0正常 1停用 */
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAuxType() {
        return auxType;
    }

    public void setAuxType(Integer auxType) {
        this.auxType = auxType;
    }

    public String getValueCode() {
        return valueCode;
    }

    public void setValueCode(String valueCode) {
        this.valueCode = valueCode;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
