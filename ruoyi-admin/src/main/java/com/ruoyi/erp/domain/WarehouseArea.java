package com.ruoyi.erp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 仓库管理对象 t_erp_warehouse_area
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public class WarehouseArea extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 客户id */
    private Long id;

    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String code;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String name;

    /** 仓库地址 */
    @Excel(name = "仓库地址")
    private String address;

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
    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("name", getName())
            .append("address", getAddress())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
