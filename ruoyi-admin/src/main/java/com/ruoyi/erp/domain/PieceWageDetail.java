package com.ruoyi.erp.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 计件工资明细对象 t_erp_piece_wage_detail
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public class PieceWageDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 明细ID */
    private Long id;

    /** 工资汇总ID */
    @Excel(name = "工资汇总ID")
    private Long wageId;

    /** 员工ID */
    @Excel(name = "员工ID")
    private Long employeeId;

    /** 工序ID */
    @Excel(name = "工序ID")
    private Long processId;

    /** 工序名称 */
    @Excel(name = "工序名称")
    private String processName;

    /** 工票ID */
    @Excel(name = "工票ID")
    private Long jobId;

    /** 合格数量 */
    @Excel(name = "合格数量")
    private Integer okQty;

    /** 次品数量 */
    @Excel(name = "次品数量")
    private Integer defectQty;

    /** 工序单价 */
    @Excel(name = "工序单价")
    private BigDecimal processPrice;

    /** 应得工资 */
    @Excel(name = "应得工资")
    private BigDecimal shouldWage;

    /** 扣款金额 */
    @Excel(name = "扣款金额")
    private BigDecimal deductWage;

    /** 实际工资 */
    @Excel(name = "实际工资")
    private BigDecimal actualWage;

    /** 备注 */
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWageId() {
        return wageId;
    }

    public void setWageId(Long wageId) {
        this.wageId = wageId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Integer getOkQty() {
        return okQty;
    }

    public void setOkQty(Integer okQty) {
        this.okQty = okQty;
    }

    public Integer getDefectQty() {
        return defectQty;
    }

    public void setDefectQty(Integer defectQty) {
        this.defectQty = defectQty;
    }

    public BigDecimal getProcessPrice() {
        return processPrice;
    }

    public void setProcessPrice(BigDecimal processPrice) {
        this.processPrice = processPrice;
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
            .append("wageId", getWageId())
            .append("employeeId", getEmployeeId())
            .append("processId", getProcessId())
            .append("processName", getProcessName())
            .append("jobId", getJobId())
            .append("okQty", getOkQty())
            .append("defectQty", getDefectQty())
            .append("processPrice", getProcessPrice())
            .append("shouldWage", getShouldWage())
            .append("deductWage", getDeductWage())
            .append("actualWage", getActualWage())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
