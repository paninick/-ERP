package com.ruoyi.erp.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class QcDefectRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "检验单ID")
    private Long inspectionId;

    @Excel(name = "缺陷类型")
    private String defectType;

    @Excel(name = "缺陷等级")
    private String defectLevel;

    @Excel(name = "数量")
    private Integer qty;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getInspectionId() { return inspectionId; }
    public void setInspectionId(Long id) { this.inspectionId = id; }

    public String getDefectType() { return defectType; }
    public void setDefectType(String t) { this.defectType = t; }

    public String getDefectLevel() { return defectLevel; }
    public void setDefectLevel(String l) { this.defectLevel = l; }

    public Integer getQty() { return qty; }
    public void setQty(Integer q) { this.qty = q; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("inspectionId", getInspectionId())
            .append("defectType", getDefectType())
            .append("defectLevel", getDefectLevel())
            .append("qty", getQty())
            .toString();
    }
}
