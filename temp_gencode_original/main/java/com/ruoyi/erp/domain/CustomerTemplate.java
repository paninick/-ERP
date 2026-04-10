package com.ruoyi.erp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客户尺寸对象 t_erp_customer_template
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
public class CustomerTemplate extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 客户 */
    @Excel(name = "客户")
    private Long customerId;

    /** 款式种类 */
    @Excel(name = "款式种类")
    private String styleId;

    /** 模板名称 */
    @Excel(name = "模板名称")
    private String templateName;

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
    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getStyleId() {
        return styleId;
    }
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateName() {
        return templateName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("customerId", getCustomerId())
            .append("styleId", getStyleId())
            .append("templateName", getTemplateName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
