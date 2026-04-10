package com.ruoyi.erp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 供应商对象 t_erp_supplier
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
public class Supplier extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 供应商id */
    private Long id;

    /** 供应商类型 -字典 面料供应商 辅料供应商 成衣供应商 其他供应商 */
    @Excel(name = "供应商类型 -字典 面料供应商 辅料供应商 成衣供应商 其他供应商")
    private String supplierType;

    /** 供应商编号 */
    @Excel(name = "供应商编号")
    private String supplierNo;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String supplierName;

    /** 供应商简称 */
    @Excel(name = "供应商简称")
    private String supplierBrief;

    /** 业务范围 */
    private String businessScope;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    public String getSupplierType() {
        return supplierType;
    }
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    public String getSupplierNo() {
        return supplierNo;
    }
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierName() {
        return supplierName;
    }
    public void setSupplierBrief(String supplierBrief) {
        this.supplierBrief = supplierBrief;
    }

    public String getSupplierBrief() {
        return supplierBrief;
    }
    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("supplierType", getSupplierType())
            .append("supplierNo", getSupplierNo())
            .append("supplierName", getSupplierName())
            .append("supplierBrief", getSupplierBrief())
            .append("businessScope", getBusinessScope())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
