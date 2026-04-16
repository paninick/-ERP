package com.ruoyi.erp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工序定义对象 t_erp_process_def
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public class ProcessDef extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 工序ID */
    private Long id;

    /** 工序编码 */
    @Excel(name = "工序编码")
    private String processCode;

    /** 工序名称 */
    @Excel(name = "工序名称")
    private String processName;

    /** 工序类型 */
    @Excel(name = "工序类型")
    private String processType;

    /** 所属部门 */
    @Excel(name = "所属部门")
    private String department;

    /** 默认工价 */
    @Excel(name = "默认工价")
    private Double defaultPrice;

    /** 是否支持外协 */
    @Excel(name = "是否支持外协", readConverterExp = "0=否,1=是")
    private Integer enableOutsource;

    /** 需要质检 */
    @Excel(name = "需要质检", readConverterExp = "0=否,1=是")
    private Integer needQualityCheck;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sortOrder;

    /** 状态 */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 备注 */
    private String remark;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public String getProcessType() {
        return processType;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDefaultPrice(Double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public Double getDefaultPrice() {
        return defaultPrice;
    }

    public void setEnableOutsource(Integer enableOutsource) {
        this.enableOutsource = enableOutsource;
    }

    public Integer getEnableOutsource() {
        return enableOutsource;
    }

    public void setNeedQualityCheck(Integer needQualityCheck) {
        this.needQualityCheck = needQualityCheck;
    }

    public Integer getNeedQualityCheck() {
        return needQualityCheck;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
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
            .append("processCode", getProcessCode())
            .append("processName", getProcessName())
            .append("processType", getProcessType())
            .append("department", getDepartment())
            .append("defaultPrice", getDefaultPrice())
            .append("enableOutsource", getEnableOutsource())
            .append("needQualityCheck", getNeedQualityCheck())
            .append("sortOrder", getSortOrder())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
