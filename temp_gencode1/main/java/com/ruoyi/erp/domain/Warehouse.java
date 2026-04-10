package com.ruoyi.erp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 库区管理对象 t_erp_warehouse
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
public class Warehouse extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 客户id */
    private Long id;

    /** 库区编码 */
    @Excel(name = "库区编码")
    private String code;

    /** 库区名称 */
    @Excel(name = "库区名称")
    private String name;

    /** 所属仓库 */
    @Excel(name = "所属仓库")
    private String warehouseAreaId;

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
    public void setWarehouseAreaId(String warehouseAreaId) {
        this.warehouseAreaId = warehouseAreaId;
    }

    public String getWarehouseAreaId() {
        return warehouseAreaId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("name", getName())
            .append("warehouseAreaId", getWarehouseAreaId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
