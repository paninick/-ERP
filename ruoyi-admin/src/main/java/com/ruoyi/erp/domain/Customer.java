package com.ruoyi.erp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 业务系统-客户对象 t_erp_customer
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public class Customer extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 客户id */
    private Long id;

    /** Excel第一列为空，跳过 */
    @Excel(name = "客户编码")
    private String emptyColumn;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String customerName;

    /** 客户简称 */
    @Excel(name = "客户简称")
    private String customerBrief;

    /** 贸易国别 - Excel第4列 */
    @Excel(name = "贸易国别")
    private String nationality;

    /** 业务范围 - Excel第5列，跳过 */
    @Excel(name = "业务范围")
    private String skipColumn;

    /** 业务员 */
    @Excel(name = "业务员")
    private String salesIdStr;

    /** 客户编码 - 自动生成 */
    private String customerNo;

    /** 业务范围 */
    private String businessScope;

    /** 业务员ID */
    private Long salesId;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setEmptyColumn(String emptyColumn) {
        this.emptyColumn = emptyColumn;
    }

    public String getEmptyColumn() {
        return emptyColumn;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerBrief(String customerBrief) {
        this.customerBrief = customerBrief;
    }

    public String getCustomerBrief() {
        return customerBrief;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }

    public void setSkipColumn(String skipColumn) {
        this.skipColumn = skipColumn;
    }

    public String getSkipColumn() {
        return skipColumn;
    }

    public void setSalesIdStr(String salesIdStr) {
        this.salesIdStr = salesIdStr;
    }

    public String getSalesIdStr() {
        return salesIdStr;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setSalesId(Long salesId) {
        this.salesId = salesId;
    }

    public Long getSalesId() {
        return salesId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("customerNo", getCustomerNo())
            .append("customerName", getCustomerName())
            .append("customerBrief", getCustomerBrief())
            .append("nationality", getNationality())
            .append("businessScope", getBusinessScope())
            .append("salesId", getSalesId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
