package com.ruoyi.erp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 仓位管理对象 t_erp_warehouse_location
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public class WarehouseLocation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 客户id */
    private Long id;

    /** 库位编码 */
    @Excel(name = "库位编码")
    private String code;

    /** 库位名称 */
    @Excel(name = "库位名称")
    private String name;

    /** 所属库区 */
    @Excel(name = "所属库区")
    private Long warehouseAreaId;

    /** 所属库区名称 */
    private String warehouseAreaName;

    /** 所属仓库 */
    @Excel(name = "所属仓库")
    private Long warehouseId;

    /** 所属仓库名称 */
    private String warehouseName;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setWarehouseAreaId(Long warehouseAreaId) {
        this.warehouseAreaId = warehouseAreaId;
    }

    public Long getWarehouseAreaId() {
        return warehouseAreaId;
    }

    public void setWarehouseAreaName(String warehouseAreaName) {
        this.warehouseAreaName = warehouseAreaName;
    }

    public String getWarehouseAreaName() {
        return warehouseAreaName;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("name", getName())
            .append("warehouseAreaId", getWarehouseAreaId())
            .append("warehouseAreaName", getWarehouseAreaName())
            .append("warehouseId", getWarehouseId())
            .append("warehouseName", getWarehouseName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
