package com.ruoyi.erp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工艺书尺寸细节对象 t_erp_sample_tech_size
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
public class SampleTechSize extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 工艺书id */
    @Excel(name = "工艺书id")
    private Long techId;

    /** 打样通知d */
    @Excel(name = "打样通知d")
    private Long noticeId;

    /** 属性名称 */
    @Excel(name = "属性名称")
    private String propertyName;

    /** 属性值1 */
    @Excel(name = "属性值1")
    private String propertyValueA;

    /** 属性值2 */
    @Excel(name = "属性值2")
    private String propertyValueB;

    /** 属性值3 */
    @Excel(name = "属性值3")
    private String propertyValueC;

    /** 属性值4 */
    @Excel(name = "属性值4")
    private String propertyValueD;

    /** 属性值5 */
    @Excel(name = "属性值5")
    private String propertyValueE;

    /** 属性值6 */
    @Excel(name = "属性值6")
    private String propertyValueF;

    /** 属性值7 */
    @Excel(name = "属性值7")
    private String propertyValueG;

    /** 属性值8 */
    @Excel(name = "属性值8")
    private String propertyValueH;

    /** 属性值9 */
    @Excel(name = "属性值9")
    private String propertyValueI;

    /** 属性值10 */
    @Excel(name = "属性值10")
    private String propertyValueJ;

    /** 属性值11 */
    @Excel(name = "属性值11")
    private String propertyValueK;

    /** 属性顺序 */
    @Excel(name = "属性顺序")
    private Long propertyOrder;

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
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }
    public void setPropertyValueA(String propertyValueA) {
        this.propertyValueA = propertyValueA;
    }

    public String getPropertyValueA() {
        return propertyValueA;
    }
    public void setPropertyValueB(String propertyValueB) {
        this.propertyValueB = propertyValueB;
    }

    public String getPropertyValueB() {
        return propertyValueB;
    }
    public void setPropertyValueC(String propertyValueC) {
        this.propertyValueC = propertyValueC;
    }

    public String getPropertyValueC() {
        return propertyValueC;
    }
    public void setPropertyValueD(String propertyValueD) {
        this.propertyValueD = propertyValueD;
    }

    public String getPropertyValueD() {
        return propertyValueD;
    }
    public void setPropertyValueE(String propertyValueE) {
        this.propertyValueE = propertyValueE;
    }

    public String getPropertyValueE() {
        return propertyValueE;
    }
    public void setPropertyValueF(String propertyValueF) {
        this.propertyValueF = propertyValueF;
    }

    public String getPropertyValueF() {
        return propertyValueF;
    }
    public void setPropertyValueG(String propertyValueG) {
        this.propertyValueG = propertyValueG;
    }

    public String getPropertyValueG() {
        return propertyValueG;
    }
    public void setPropertyValueH(String propertyValueH) {
        this.propertyValueH = propertyValueH;
    }

    public String getPropertyValueH() {
        return propertyValueH;
    }
    public void setPropertyValueI(String propertyValueI) {
        this.propertyValueI = propertyValueI;
    }

    public String getPropertyValueI() {
        return propertyValueI;
    }
    public void setPropertyValueJ(String propertyValueJ) {
        this.propertyValueJ = propertyValueJ;
    }

    public String getPropertyValueJ() {
        return propertyValueJ;
    }
    public void setPropertyValueK(String propertyValueK) {
        this.propertyValueK = propertyValueK;
    }

    public String getPropertyValueK() {
        return propertyValueK;
    }
    public void setPropertyOrder(Long propertyOrder) {
        this.propertyOrder = propertyOrder;
    }

    public Long getPropertyOrder() {
        return propertyOrder;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("techId", getTechId())
            .append("noticeId", getNoticeId())
            .append("propertyName", getPropertyName())
            .append("propertyValueA", getPropertyValueA())
            .append("propertyValueB", getPropertyValueB())
            .append("propertyValueC", getPropertyValueC())
            .append("propertyValueD", getPropertyValueD())
            .append("propertyValueE", getPropertyValueE())
            .append("propertyValueF", getPropertyValueF())
            .append("propertyValueG", getPropertyValueG())
            .append("propertyValueH", getPropertyValueH())
            .append("propertyValueI", getPropertyValueI())
            .append("propertyValueJ", getPropertyValueJ())
            .append("propertyValueK", getPropertyValueK())
            .append("propertyOrder", getPropertyOrder())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
