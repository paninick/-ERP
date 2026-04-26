package com.ruoyi.erp.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.erp.domain.base.ErpBaseBillEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 外协加工单对象 t_erp_outsource_order
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public class OutsourceOrder extends ErpBaseBillEntity {

    /** 外协订单ID */
    private Long id;

    /** 外协单号 */
    @Excel(name = "外协单号")
    private String outsourceNo;

    /** 工序ID */
    @Excel(name = "工序ID")
    private Long processId;

    /** 工序名称 */
    @Excel(name = "工序名称")
    private String processName;

    /** 外协厂商ID */
    @Excel(name = "外协厂商ID")
    private Long supplierId;

    /** 外协厂商名称 */
    @Excel(name = "外协厂商名称")
    private String supplierName;

    /** 是否调拨 0否 1是 */
    @Excel(name = "是否调拨", readConverterExp = "0=否,1=是")
    private Integer isTransfer;

    /** 调出外协ID */
    private Long transferFrom;

    /** 调入外协ID */
    private Long transferTo;

    /** 工票ID列表，逗号分隔 */
    private String jobIds;
    private String batchNo;
    private Integer batchSeq;
    private Integer batchTotal;

    /** 总件数 */
    @Excel(name = "总件数")
    private Integer totalQty;

    /** 理论总重量 */
    @Excel(name = "理论总重量")
    private BigDecimal theoryWeight;

    /** 实际收回重量 */
    @Excel(name = "实际收回重量")
    private BigDecimal actualWeight;

    /** 确认收货数量 */
    @Excel(name = "确认收货数量")
    private Integer confirmQty;

    /** 次品数量 */
    @Excel(name = "次品数量")
    private Integer defectQty;

    /** 状态 0待发出 1已发出 2部分收回 3全部收回 */
    @Excel(name = "状态", readConverterExp = "0=待发出,1=已发出,2=部分收回,3=全部收回")
    private String status;

    /** 审批状态 DRAFT/SUBMITTED/APPROVED/REJECTED */
    private String auditStatus;

    /** 发出时间 */
    @Excel(name = "发出时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date outboundTime;

    /** 收回时间 */
    @Excel(name = "收回时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date receiveTime;

    /** 加工单价 */
    @Excel(name = "加工单价")
    private BigDecimal unitPrice;

    /** 加工总价 */
    @Excel(name = "加工总价")
    private BigDecimal totalPrice;

    /** 运费 */
    @Excel(name = "运费")
    private BigDecimal freight;

    /** 备注 */
    private String remark;

    /** 结算状态 UNSETTLED/PARTIAL/SETTLED */
    private String settleStatus;

    /** 已结算金额 */
    @Excel(name = "已结算金额")
    private BigDecimal settleAmount;

    /** 结算时间 */
    private Date settleTime;

    /** 外协成本金额（含运费） */
    @Excel(name = "外协成本")
    private BigDecimal costAmount;

    public String getSettleStatus() { return settleStatus; }
    public void setSettleStatus(String settleStatus) { this.settleStatus = settleStatus; }

    public String getAuditStatus() { return auditStatus; }
    public void setAuditStatus(String auditStatus) { this.auditStatus = auditStatus; }
    public BigDecimal getSettleAmount() { return settleAmount; }
    public void setSettleAmount(BigDecimal settleAmount) { this.settleAmount = settleAmount; }
    public Date getSettleTime() { return settleTime; }
    public void setSettleTime(Date settleTime) { this.settleTime = settleTime; }
    public BigDecimal getCostAmount() { return costAmount; }
    public void setCostAmount(BigDecimal costAmount) { this.costAmount = costAmount; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutsourceNo() {
        return outsourceNo;
    }

    public void setOutsourceNo(String outsourceNo) {
        this.outsourceNo = outsourceNo;
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

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getIsTransfer() {
        return isTransfer;
    }

    public void setIsTransfer(Integer isTransfer) {
        this.isTransfer = isTransfer;
    }

    public Long getTransferFrom() {
        return transferFrom;
    }

    public void setTransferFrom(Long transferFrom) {
        this.transferFrom = transferFrom;
    }

    public Long getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(Long transferTo) {
        this.transferTo = transferTo;
    }

    public String getJobIds() {
        return jobIds;
    }

    public void setJobIds(String jobIds) {
        this.jobIds = jobIds;
    }

    public String getBatchNo() { return batchNo; }
    public void setBatchNo(String v) { this.batchNo = v; }
    public Integer getBatchSeq() { return batchSeq; }
    public void setBatchSeq(Integer v) { this.batchSeq = v; }
    public Integer getBatchTotal() { return batchTotal; }
    public void setBatchTotal(Integer v) { this.batchTotal = v; }

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public BigDecimal getTheoryWeight() {
        return theoryWeight;
    }

    public void setTheoryWeight(BigDecimal theoryWeight) {
        this.theoryWeight = theoryWeight;
    }

    public BigDecimal getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(BigDecimal actualWeight) {
        this.actualWeight = actualWeight;
    }

    public Integer getConfirmQty() {
        return confirmQty;
    }

    public void setConfirmQty(Integer confirmQty) {
        this.confirmQty = confirmQty;
    }

    public Integer getDefectQty() {
        return defectQty;
    }

    public void setDefectQty(Integer defectQty) {
        this.defectQty = defectQty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(Date outboundTime) {
        this.outboundTime = outboundTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
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
            .append("outsourceNo", getOutsourceNo())
            .append("processId", getProcessId())
            .append("processName", getProcessName())
            .append("supplierId", getSupplierId())
            .append("supplierName", getSupplierName())
            .append("isTransfer", getIsTransfer())
            .append("transferFrom", getTransferFrom())
            .append("transferTo", getTransferTo())
            .append("jobIds", getJobIds())
            .append("totalQty", getTotalQty())
            .append("theoryWeight", getTheoryWeight())
            .append("actualWeight", getActualWeight())
            .append("confirmQty", getConfirmQty())
            .append("defectQty", getDefectQty())
            .append("status", getStatus())
            .append("auditStatus", getAuditStatus())
            .append("outboundTime", getOutboundTime())
            .append("receiveTime", getReceiveTime())
            .append("unitPrice", getUnitPrice())
            .append("totalPrice", getTotalPrice())
            .append("freight", getFreight())
            .append("settleStatus", getSettleStatus())
            .append("settleAmount", getSettleAmount())
            .append("settleTime", getSettleTime())
            .append("costAmount", getCostAmount())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
