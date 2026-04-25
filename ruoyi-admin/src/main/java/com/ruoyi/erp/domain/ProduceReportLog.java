package com.ruoyi.erp.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * Production report event log object t_erp_produce_report_log.
 *
 * This is the execution-event layer. ProduceJobProcess remains the snapshot layer.
 */
public class ProduceReportLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "jobId")
    private Long jobId;

    @Excel(name = "jobProcessId")
    private Long jobProcessId;

    @Excel(name = "processId")
    private Long processId;

    @Excel(name = "processSeq")
    private Integer processSeq;

    @Excel(name = "employeeId")
    private Long employeeId;

    @Excel(name = "employeeName")
    private String employeeName;

    @Excel(name = "operatorName")
    private String operatorName;

    private String teamName;

    private String machineNo;

    @Excel(name = "reportQty")
    private Integer reportQty;

    @Excel(name = "completedQty")
    private Integer completedQty;

    @Excel(name = "defectQty")
    private Integer defectQty;

    @Excel(name = "lossQty")
    private Integer lossQty;

    @Excel(name = "reportType")
    private String reportType;

    @Excel(name = "reportSource")
    private String reportSource;

    private String batchNo;

    private String scanToken;

    private String billingGroupId;

    @Excel(name = "isOutsourced")
    private String isOutsourced;

    private Long vendorId;

    private String validationStatus;

    private String validationMessage;

    @Excel(name = "eventTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date eventTime;

    private String delFlag;

    private String processName;

    private String jobNo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }

    public Long getJobProcessId() { return jobProcessId; }
    public void setJobProcessId(Long jobProcessId) { this.jobProcessId = jobProcessId; }

    public Long getProcessId() { return processId; }
    public void setProcessId(Long processId) { this.processId = processId; }

    public Integer getProcessSeq() { return processSeq; }
    public void setProcessSeq(Integer processSeq) { this.processSeq = processSeq; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public String getOperatorName() { return operatorName; }
    public void setOperatorName(String operatorName) { this.operatorName = operatorName; }

    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }

    public String getMachineNo() { return machineNo; }
    public void setMachineNo(String machineNo) { this.machineNo = machineNo; }

    public Integer getReportQty() { return reportQty; }
    public void setReportQty(Integer reportQty) { this.reportQty = reportQty; }

    public Integer getCompletedQty() { return completedQty; }
    public void setCompletedQty(Integer completedQty) { this.completedQty = completedQty; }

    public Integer getDefectQty() { return defectQty; }
    public void setDefectQty(Integer defectQty) { this.defectQty = defectQty; }

    public Integer getLossQty() { return lossQty; }
    public void setLossQty(Integer lossQty) { this.lossQty = lossQty; }

    public String getReportType() { return reportType; }
    public void setReportType(String reportType) { this.reportType = reportType; }

    public String getReportSource() { return reportSource; }
    public void setReportSource(String reportSource) { this.reportSource = reportSource; }

    public String getBatchNo() { return batchNo; }
    public void setBatchNo(String batchNo) { this.batchNo = batchNo; }

    public String getScanToken() { return scanToken; }
    public void setScanToken(String scanToken) { this.scanToken = scanToken; }

    public String getBillingGroupId() { return billingGroupId; }
    public void setBillingGroupId(String billingGroupId) { this.billingGroupId = billingGroupId; }

    public String getIsOutsourced() { return isOutsourced; }
    public void setIsOutsourced(String isOutsourced) { this.isOutsourced = isOutsourced; }

    public Long getVendorId() { return vendorId; }
    public void setVendorId(Long vendorId) { this.vendorId = vendorId; }

    public String getValidationStatus() { return validationStatus; }
    public void setValidationStatus(String validationStatus) { this.validationStatus = validationStatus; }

    public String getValidationMessage() { return validationMessage; }
    public void setValidationMessage(String validationMessage) { this.validationMessage = validationMessage; }

    public Date getEventTime() { return eventTime; }
    public void setEventTime(Date eventTime) { this.eventTime = eventTime; }

    public String getDelFlag() { return delFlag; }
    public void setDelFlag(String delFlag) { this.delFlag = delFlag; }

    public String getProcessName() { return processName; }
    public void setProcessName(String processName) { this.processName = processName; }

    public String getJobNo() { return jobNo; }
    public void setJobNo(String jobNo) { this.jobNo = jobNo; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("jobId", getJobId())
            .append("jobProcessId", getJobProcessId())
            .append("processId", getProcessId())
            .append("processSeq", getProcessSeq())
            .append("employeeId", getEmployeeId())
            .append("employeeName", getEmployeeName())
            .append("operatorName", getOperatorName())
            .append("reportQty", getReportQty())
            .append("completedQty", getCompletedQty())
            .append("defectQty", getDefectQty())
            .append("lossQty", getLossQty())
            .append("reportType", getReportType())
            .append("reportSource", getReportSource())
            .append("isOutsourced", getIsOutsourced())
            .append("vendorId", getVendorId())
            .append("validationStatus", getValidationStatus())
            .append("eventTime", getEventTime())
            .append("remark", getRemark())
            .toString();
    }
}
