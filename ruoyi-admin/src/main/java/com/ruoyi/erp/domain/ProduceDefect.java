package com.ruoyi.erp.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 次品记录对象 t_erp_produce_defect
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public class ProduceDefect extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 次品ID */
    private Long id;

    /** 工票ID */
    @Excel(name = "工票ID")
    private Long jobId;

    /** 工序ID */
    @Excel(name = "工序ID")
    private Long processId;

    /** 工序名称 */
    @Excel(name = "工序名称")
    private String processName;

    /** 操作工ID */
    @Excel(name = "操作工ID")
    private Long employeeId;

    /** 操作工姓名 */
    @Excel(name = "操作工姓名")
    private String employeeName;

    /** 次品数量 */
    @Excel(name = "次品数量")
    private Integer defectQty;

    /** 次品原因编码 */
    @Excel(name = "次品原因编码")
    private String defectReasonCode;

    /** 次品原因描述 */
    @Excel(name = "次品原因描述")
    private String defectReasonDesc;

    /** 是否报废 0否 1是 */
    @Excel(name = "是否报废", readConverterExp = "0=否,1=是")
    private String isScrap;

    /** 是否修复复用 0否 1是 */
    @Excel(name = "是否修复复用", readConverterExp = "0=否,1=是")
    private String isRepair;

    /** 发现时间 */
    @Excel(name = "发现时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date findTime;

    /** 外协ID */
    private Long outsourceId;

    /** 是否外协次品 0否 1是 */
    @Excel(name = "是否外协次品", readConverterExp = "0=否,1=是")
    private String isOutsource;

    /** 缺陷大类：WEAVE=织造疵 DYE=染色疵 SPLICE=拼接疵 SEW=缝制疵 NEEDLE=残断针 PACK=包装疵 */
    @Excel(name = "缺陷大类")
    private String defectCategory;

    /** 缺陷严重等级：CRITICAL=致命 MAJOR=严重 MINOR=轻微 */
    @Excel(name = "缺陷等级")
    private String defectLevel;

    /** 处理方式：REPAIR=返工修复 DOWNGRADE=降级处理 SCRAP=报废 */
    @Excel(name = "处理方式")
    private String handleType;

    /** 处理结果描述 */
    @Excel(name = "处理结果")
    private String handleResult;

    /** 责任归属：SELF=本厂 OUTSOURCE=外协 MATERIAL=原料 */
    @Excel(name = "责任归属")
    private String responsibility;

    /** 是否残断针（对日零容忍红线）0否 1是 */
    @Excel(name = "是否残断针", readConverterExp = "0=否,1=是")
    private String isBrokenNeedle;

    /** 残断针处理确认人 */
    private String needleConfirmBy;

    /** 残断针处理确认时间 */
    @Excel(name = "残断针确认时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date needleConfirmTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getDefectQty() {
        return defectQty;
    }

    public void setDefectQty(Integer defectQty) {
        this.defectQty = defectQty;
    }

    public String getDefectReasonCode() {
        return defectReasonCode;
    }

    public void setDefectReasonCode(String defectReasonCode) {
        this.defectReasonCode = defectReasonCode;
    }

    public String getDefectReasonDesc() {
        return defectReasonDesc;
    }

    public void setDefectReasonDesc(String defectReasonDesc) {
        this.defectReasonDesc = defectReasonDesc;
    }

    public String getIsScrap() {
        return isScrap;
    }

    public void setIsScrap(String isScrap) {
        this.isScrap = isScrap;
    }

    public String getIsRepair() {
        return isRepair;
    }

    public void setIsRepair(String isRepair) {
        this.isRepair = isRepair;
    }

    public Date getFindTime() {
        return findTime;
    }

    public void setFindTime(Date findTime) {
        this.findTime = findTime;
    }

    public Long getOutsourceId() {
        return outsourceId;
    }

    public void setOutsourceId(Long outsourceId) {
        this.outsourceId = outsourceId;
    }

    public String getIsOutsource() {
        return isOutsource;
    }

    public void setIsOutsource(String isOutsource) {
        this.isOutsource = isOutsource;
    }

    public String getDefectCategory() { return defectCategory; }
    public void setDefectCategory(String defectCategory) { this.defectCategory = defectCategory; }

    public String getDefectLevel() { return defectLevel; }
    public void setDefectLevel(String defectLevel) { this.defectLevel = defectLevel; }

    public String getHandleType() { return handleType; }
    public void setHandleType(String handleType) { this.handleType = handleType; }

    public String getHandleResult() { return handleResult; }
    public void setHandleResult(String handleResult) { this.handleResult = handleResult; }

    public String getResponsibility() { return responsibility; }
    public void setResponsibility(String responsibility) { this.responsibility = responsibility; }

    public String getIsBrokenNeedle() { return isBrokenNeedle; }
    public void setIsBrokenNeedle(String isBrokenNeedle) { this.isBrokenNeedle = isBrokenNeedle; }

    public String getNeedleConfirmBy() { return needleConfirmBy; }
    public void setNeedleConfirmBy(String needleConfirmBy) { this.needleConfirmBy = needleConfirmBy; }

    public Date getNeedleConfirmTime() { return needleConfirmTime; }
    public void setNeedleConfirmTime(Date needleConfirmTime) { this.needleConfirmTime = needleConfirmTime; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("jobId", getJobId())
            .append("processId", getProcessId())
            .append("processName", getProcessName())
            .append("employeeId", getEmployeeId())
            .append("employeeName", getEmployeeName())
            .append("defectQty", getDefectQty())
            .append("defectReasonCode", getDefectReasonCode())
            .append("defectReasonDesc", getDefectReasonDesc())
            .append("isScrap", getIsScrap())
            .append("isRepair", getIsRepair())
            .append("findTime", getFindTime())
            .append("outsourceId", getOutsourceId())
            .append("isOutsource", getIsOutsource())
            .append("defectCategory", getDefectCategory())
            .append("defectLevel", getDefectLevel())
            .append("handleType", getHandleType())
            .append("handleResult", getHandleResult())
            .append("responsibility", getResponsibility())
            .append("isBrokenNeedle", getIsBrokenNeedle())
            .append("needleConfirmBy", getNeedleConfirmBy())
            .append("needleConfirmTime", getNeedleConfirmTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
