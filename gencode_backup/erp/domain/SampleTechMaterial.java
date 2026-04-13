package com.ruoyi.erp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工艺书面料信息对象 t_erp_sample_tech_material
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public class SampleTechMaterial extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 工艺书id */
    @Excel(name = "工艺书id")
    private Long techId;

    /** 打样通知d */
    @Excel(name = "打样通知d")
    private Long noticeId;

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
    public void setTechId(Long techId) {
        this.techId = techId;
    }

    public Long getTechId() {
        return techId;
    }
    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getNoticeId() {
        return noticeId;
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
            .append("techId", getTechId())
            .append("noticeId", getNoticeId())
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
