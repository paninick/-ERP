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
    private java.math.BigDecimal defaultPrice;

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

    /** 拼接工艺：是否拼接工序 0否 1是 */
    @Excel(name = "是否拼接工序")
    private String isSpliceProcess;

    /** 缩水基线（%，拼接前预缩处理基准值） */
    @Excel(name = "缩水基线(%)")
    private java.math.BigDecimal shrinkageBaseline;

    /** 弹力补偿量（%，拼接时弹力损耗补偿） */
    @Excel(name = "弹力补偿(%)")
    private java.math.BigDecimal elasticityCompensation;

    /** 拼缝规格（mm，拼缝宽度标准） */
    @Excel(name = "拼缝规格(mm)")
    private java.math.BigDecimal seamWidth;

    /** 拼接方向要求：WARP=经向 WEFT=纬向 BIAS=斜向 ANY=不限 */
    @Excel(name = "拼接方向")
    private String spliceDirection;

    /** 拼接面料兼容性说明 */
    @Excel(name = "面料兼容性")
    private String fabricCompatibility;

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

    public void setDefaultPrice(java.math.BigDecimal defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public java.math.BigDecimal getDefaultPrice() {
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

    public String getIsSpliceProcess() { return isSpliceProcess; }
    public void setIsSpliceProcess(String isSpliceProcess) { this.isSpliceProcess = isSpliceProcess; }

    public java.math.BigDecimal getShrinkageBaseline() { return shrinkageBaseline; }
    public void setShrinkageBaseline(java.math.BigDecimal shrinkageBaseline) { this.shrinkageBaseline = shrinkageBaseline; }

    public java.math.BigDecimal getElasticityCompensation() { return elasticityCompensation; }
    public void setElasticityCompensation(java.math.BigDecimal elasticityCompensation) { this.elasticityCompensation = elasticityCompensation; }

    public java.math.BigDecimal getSeamWidth() { return seamWidth; }
    public void setSeamWidth(java.math.BigDecimal seamWidth) { this.seamWidth = seamWidth; }

    public String getSpliceDirection() { return spliceDirection; }
    public void setSpliceDirection(String spliceDirection) { this.spliceDirection = spliceDirection; }

    public String getFabricCompatibility() { return fabricCompatibility; }
    public void setFabricCompatibility(String fabricCompatibility) { this.fabricCompatibility = fabricCompatibility; }

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
            .append("isSpliceProcess", getIsSpliceProcess())
            .append("shrinkageBaseline", getShrinkageBaseline())
            .append("elasticityCompensation", getElasticityCompensation())
            .append("seamWidth", getSeamWidth())
            .append("spliceDirection", getSpliceDirection())
            .append("fabricCompatibility", getFabricCompatibility())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
