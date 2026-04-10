package com.ruoyi.erp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客户模板面料对象 t_erp_customer_template_material
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
public class CustomerTemplateMaterial extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 客户id */
    @Excel(name = "客户id")
    private Long customerId;

    /** 模板id */
    @Excel(name = "模板id")
    private Long templateId;

    /** 属性顺序 */
    @Excel(name = "属性顺序")
    private Long propertyOrder;

    /** 属性值 */
    @Excel(name = "属性值")
    private String propertyValue;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getTemplateId() {
        return templateId;
    }
    public void setPropertyOrder(Long propertyOrder) {
        this.propertyOrder = propertyOrder;
    }

    public Long getPropertyOrder() {
        return propertyOrder;
    }
    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("customerId", getCustomerId())
            .append("templateId", getTemplateId())
            .append("propertyOrder", getPropertyOrder())
            .append("propertyValue", getPropertyValue())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
