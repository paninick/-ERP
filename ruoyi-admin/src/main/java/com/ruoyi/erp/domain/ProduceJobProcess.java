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

    /** 工序流转状态：PENDING=待开工 RUNNING=进行中 WAIT_CHECK=完工待检 PASS=检验通过 FAIL=检验不合格 OUTSOURCE=外发中 */
    @Excel(name = "流转状态")
    private String processStatus;

    /** 放行人（质检通过后签字） */
    private String releaseBy;

    /** 放行时间 */
    @Excel(name = "放行时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date releaseTime;

    /** 不合格处理方式：REPAIR=返工 DOWNGRADE=降级 SCRAP=报废 */
    @Excel(name = "不合格处理")
    private String failHandleType;

    /** 损耗数量（本工序实际损耗） */
    @Excel(name = "损耗数量")
    private Integer lossQty;

    /** 损耗是否超标 0否 1是 */
    @Excel(name = "损耗超标")
    private String lossExceed;
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

    public String getProcessStatus() { return processStatus; }
    public void setProcessStatus(String processStatus) { this.processStatus = processStatus; }

    public String getReleaseBy() { return releaseBy; }
    public void setReleaseBy(String releaseBy) { this.releaseBy = releaseBy; }

    public Date getReleaseTime() { return releaseTime; }
    public void setReleaseTime(Date releaseTime) { this.releaseTime = releaseTime; }

    public String getFailHandleType() { return failHandleType; }
    public void setFailHandleType(String failHandleType) { this.failHandleType = failHandleType; }

    public Integer getLossQty() { return lossQty; }
    public void setLossQty(Integer lossQty) { this.lossQty = lossQty; }

    public String getLossExceed() { return lossExceed; }
    public void setLossExceed(String lossExceed) { this.lossExceed = lossExceed; }

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
