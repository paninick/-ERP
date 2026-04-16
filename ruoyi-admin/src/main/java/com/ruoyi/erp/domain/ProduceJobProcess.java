package com.ruoyi.erp.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 工序流转记录对象 t_erp_produce_job_process
 *
 * @author ruoyi
 */
public class ProduceJobProcess extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    private Long id;

    /**
     * 工票ID
     */
    @Excel(name = "工票ID")
    private Long jobId;

    /**
     * 工序ID
     */
    @Excel(name = "工序ID")
    private Long processId;

    /**
     * 工序顺序
     */
    @Excel(name = "工序顺序")
    private Integer processSeq;

    /**
     * 操作工ID
     */
    @Excel(name = "操作工ID")
    private Long employeeId;

    /**
     * 操作工姓名
     */
    @Excel(name = "操作工姓名")
    private String employeeName;

    /**
     * 接收数量
     */
    @Excel(name = "接收数量")
    private Integer inQty;

    /**
     * 转出数量
     */
    @Excel(name = "转出数量")
    private Integer outQty;

    /**
     * 本工序次品数量
     */
    @Excel(name = "本工序次品数量")
    private Integer defectQty;

    /**
     * 开始时间
     */
    @Excel(name = "开始时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 完成时间
     */
    @Excel(name = "完成时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    /**
     * 是否外协 0自产 1外协
     */
    @Excel(name = "是否外协", readConverterExp = "0=自产,1=外协")
    private String isOutsource;

    /**
     * 外协ID
     */
    @Excel(name = "外协ID")
    private Long outsourceId;

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

    public Integer getProcessSeq() {
        return processSeq;
    }

    public void setProcessSeq(Integer processSeq) {
        this.processSeq = processSeq;
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

    public Integer getInQty() {
        return inQty;
    }

    public void setInQty(Integer inQty) {
        this.inQty = inQty;
    }

    public Integer getOutQty() {
        return outQty;
    }

    public void setOutQty(Integer outQty) {
        this.outQty = outQty;
    }

    public Integer getDefectQty() {
        return defectQty;
    }

    public void setDefectQty(Integer defectQty) {
        this.defectQty = defectQty;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getIsOutsource() {
        return isOutsource;
    }

    public void setIsOutsource(String isOutsource) {
        this.isOutsource = isOutsource;
    }

    public Long getOutsourceId() {
        return outsourceId;
    }

    public void setOutsourceId(Long outsourceId) {
        this.outsourceId = outsourceId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("jobId", getJobId())
                .append("processId", getProcessId())
                .append("processSeq", getProcessSeq())
                .append("employeeId", getEmployeeId())
                .append("employeeName", getEmployeeName())
                .append("inQty", getInQty())
                .append("outQty", getOutQty())
                .append("defectQty", getDefectQty())
                .append("startTime", getStartTime())
                .append("finishTime", getFinishTime())
                .append("isOutsource", getIsOutsource())
                .append("outsourceId", getOutsourceId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("remark", getRemark())
                .toString();
    }
}
