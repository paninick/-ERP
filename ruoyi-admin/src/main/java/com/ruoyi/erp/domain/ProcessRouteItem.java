package com.ruoyi.erp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工艺路线明细对象 t_erp_process_route_item
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public class ProcessRouteItem extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 明细ID */
    private Long id;

    /** 工艺路线ID */
    @Excel(name = "工艺路线ID")
    private Long routeId;

    /** 工序ID */
    @Excel(name = "工序ID")
    private Long processId;

    /** 排序顺序 */
    @Excel(name = "排序顺序")
    private Integer sortOrder;

    /** 是否齐套控制点 */
    @Excel(name = "是否齐套控制点", readConverterExp = "0=否,1=是")
    private Integer isControlPoint;

    /** 齐套要求比率 */
    @Excel(name = "齐套要求比率")
    private Double requireCompleteRatio;

    /** 允许强制开工 */
    @Excel(name = "允许强制开工", readConverterExp = "0=否,1=是")
    private Integer allowForceStart;

    /** 是否外协 */
    @Excel(name = "是否外协", readConverterExp = "0=否,1=是")
    private Integer isOutsource;

    /** 标准工时（小时） */
    @Excel(name = "标准工时（小时）")
    private Double standardCycleHours;

    /** 备注 */
    private String remark;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setIsControlPoint(Integer isControlPoint) {
        this.isControlPoint = isControlPoint;
    }

    public Integer getIsControlPoint() {
        return isControlPoint;
    }

    public void setRequireCompleteRatio(Double requireCompleteRatio) {
        this.requireCompleteRatio = requireCompleteRatio;
    }

    public Double getRequireCompleteRatio() {
        return requireCompleteRatio;
    }

    public void setAllowForceStart(Integer allowForceStart) {
        this.allowForceStart = allowForceStart;
    }

    public Integer getAllowForceStart() {
        return allowForceStart;
    }

    public void setIsOutsource(Integer isOutsource) {
        this.isOutsource = isOutsource;
    }

    public Integer getIsOutsource() {
        return isOutsource;
    }

    public void setStandardCycleHours(Double standardCycleHours) {
        this.standardCycleHours = standardCycleHours;
    }

    public Double getStandardCycleHours() {
        return standardCycleHours;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("routeId", getRouteId())
            .append("processId", getProcessId())
            .append("sortOrder", getSortOrder())
            .append("isControlPoint", getIsControlPoint())
            .append("requireCompleteRatio", getRequireCompleteRatio())
            .append("allowForceStart", getAllowForceStart())
            .append("isOutsource", getIsOutsource())
            .append("standardCycleHours", getStandardCycleHours())
            .append("remark", getRemark())
            .toString();
    }
}
