package com.ruoyi.erp.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 计件工资汇总对象 t_erp_piece_wage
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public class PieceWage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 工资汇总ID */
    private Long id;

    /** 员工ID */
    @Excel(name = "员工ID")
    private Long employeeId;

    /** 员工姓名 */
    @Excel(name = "员工姓名")
    private String employeeName;

    /** 汇总月份 */
    @Excel(name = "汇总月份", width = 30, dateFormat = "yyyy-MM")
    private String wageMonth;

    /** 总工序数量 */
    @Excel(name = "总工序数量")
    private Integer totalProcessCount;

    /** 总合格产量 */
    @Excel(name = "总合格产量")
    private Integer totalOkQty;

    /** 总次品产量 */
    @Excel(name = "总次品产量")
    private Integer totalDefectQty;

    /** 应发工资 */
    @Excel(name = "应发工资")
    private BigDecimal shouldWage;

    /** 扣款金额 */
    @Excel(name = "扣款金额")
    private BigDecimal deductWage;

    /** 实际工资 */
    @Excel(name = "实际工资")
    private BigDecimal actualWage;

    /** 状态 0待审核 1已审核 2已发放 */
    @Excel(name = "状态", readConverterExp = "0=待审核,1=已审核,2=已发放")
    private String status;

    /** 审核人ID */
    private Long auditById;

    /** 审核人姓名 */
    private String auditByName;

    /** 审核时间 */
    @Excel(name = "审核时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 发放时间 */
    @Excel(name = "发放时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    /** 备注 */
    private String remark;

    /** 结算状态 DRAFT/CONFIRMED/PAID */
    private String settleStatus;

    /** 关联报工事件ID */
    private Long reportLogId;

    /** 工资成本金额 */
    @Excel(name = "工资成本")
    private BigDecimal costAmount;

    /** 确认人 */
    private String confirmBy;

    /** 确认时间 */
    private Date confirmTime;

    public String getSettleStatus() { return settleStatus; }
    public void setSettleStatus(String settleStatus) { this.settleStatus = settleStatus; }
    public Long getReportLogId() { return reportLogId; }
    public void setReportLogId(Long reportLogId) { this.reportLogId = reportLogId; }
    public BigDecimal getCostAmount() { return costAmount; }
    public void setCostAmount(BigDecimal costAmount) { this.costAmount = costAmount; }
    public String getConfirmBy() { return confirmBy; }
    public void setConfirmBy(String confirmBy) { this.confirmBy = confirmBy; }
    public Date getConfirmTime() { return confirmTime; }
    public void setConfirmTime(Date confirmTime) { this.confirmTime = confirmTime; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getWageMonth() {
        return wageMonth;
    }

    public void setWageMonth(String wageMonth) {
        this.wageMonth = wageMonth;
    }

    public Integer getTotalProcessCount() {
        return totalProcessCount;
    }

    public void setTotalProcessCount(Integer totalProcessCount) {
        this.totalProcessCount = totalProcessCount;
    }

    public Integer getTotalOkQty() {
        return totalOkQty;
    }

    public void setTotalOkQty(Integer totalOkQty) {
        this.totalOkQty = totalOkQty;
    }

    public Integer getTotalDefectQty() {
        return totalDefectQty;
    }

    public void setTotalDefectQty(Integer totalDefectQty) {
        this.totalDefectQty = totalDefectQty;
    }

    public BigDecimal getShouldWage() {
        return shouldWage;
    }

    public void setShouldWage(BigDecimal shouldWage) {
        this.shouldWage = shouldWage;
    }

    public BigDecimal getDeductWage() {
        return deductWage;
    }

    public void setDeductWage(BigDecimal deductWage) {
        this.deductWage = deductWage;
    }

    public BigDecimal getActualWage() {
        return actualWage;
    }

    public void setActualWage(BigDecimal actualWage) {
        this.actualWage = actualWage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getAuditById() {
        return auditById;
    }

    public void setAuditById(Long auditById) {
        this.auditById = auditById;
    }

    public String getAuditByName() {
        return auditByName;
    }

    public void setAuditByName(String auditByName) {
        this.auditByName = auditByName;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("employeeId", getEmployeeId())
            .append("employeeName", getEmployeeName())
            .append("wageMonth", getWageMonth())
            .append("totalProcessCount", getTotalProcessCount())
            .append("totalOkQty", getTotalOkQty())
            .append("totalDefectQty", getTotalDefectQty())
            .append("shouldWage", getShouldWage())
            .append("deductWage", getDeductWage())
            .append("actualWage", getActualWage())
            .append("status", getStatus())
            .append("auditById", getAuditById())
            .append("auditByName", getAuditByName())
            .append("auditTime", getAuditTime())
            .append("payTime", getPayTime())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
