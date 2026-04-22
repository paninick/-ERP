package com.ruoyi.erp.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 生产工票/扎单对象 t_erp_produce_job
 *
 * @author ruoyi
 */
public class ProduceJob extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 工票ID
     */
    private Long id;

    /**
     * 工票编号
     */
    @Excel(name = "工票编号")
    private String jobNo;

    /**
     * 生产计划ID
     */
    @Excel(name = "生产计划ID")
    private Long producePlanId;

    /**
     * 销售订单ID
     */
    @Excel(name = "销售订单ID")
    private Long orderId;

    /**
     * 工艺路线ID（创建工单时按此路线自动初始化 ProduceJobProcess 工序队列）
     */
    @Excel(name = "工艺路线ID")
    private Long processRouteId;

    /**
     * 颜色编码
     */
    @Excel(name = "颜色编码")
    private String colorCode;

    /**
     * 尺码编码
     */
    @Excel(name = "尺码编码")
    private String sizeCode;

    /**
     * 计划数量
     */
    @Excel(name = "计划数量")
    private Integer planQty;

    /**
     * 实际完成数量
     */
    @Excel(name = "实际完成数量")
    private Integer actualQty;

    /**
     * 次品数量
     */
    @Excel(name = "次品数量")
    private Integer defectQty;

    /**
     * 当前工序ID
     */
    @Excel(name = "当前工序ID")
    private Long currentProcessId;

    /**
     * 当前工序状态 0待开工 1进行中 2已完成
     */
    @Excel(name = "当前工序状态", readConverterExp = "0=待开工,1=进行中,2=已完成")
    private String currentProcessStatus;

    /**
     * 开工时间
     */
    @Excel(name = "开工时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 完成时间
     */
    @Excel(name = "完成时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    /**
     * 外协批次条码
     */
    @Excel(name = "外协批次条码")
    private String outsourceBatchBarcode;

    /**
     * 外协发出时间
     */
    @Excel(name = "外协发出时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date outboundTime;

    /**
     * 外协收回时间
     */
    @Excel(name = "外协收回时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date receiveTime;

    /**
     * 外协确认收货数量
     */
    @Excel(name = "外协确认收货数量")
    private Integer confirmQty;

    /**
     * 当前外协ID
     */
    @Excel(name = "当前外协ID")
    private Long outsourceId;

    /**
     * 工票状态 0待生产 1生产中 2已完成
     */
    @Excel(name = "工票状态", readConverterExp = "0=待生产,1=生产中,2=已完成")
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public Long getProducePlanId() {
        return producePlanId;
    }

    public void setProducePlanId(Long producePlanId) {
        this.producePlanId = producePlanId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProcessRouteId() { return processRouteId; }
    public void setProcessRouteId(Long processRouteId) { this.processRouteId = processRouteId; }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getSizeCode() {
        return sizeCode;
    }

    public void setSizeCode(String sizeCode) {
        this.sizeCode = sizeCode;
    }

    public Integer getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Integer planQty) {
        this.planQty = planQty;
    }

    public Integer getActualQty() {
        return actualQty;
    }

    public void setActualQty(Integer actualQty) {
        this.actualQty = actualQty;
    }

    public Integer getDefectQty() {
        return defectQty;
    }

    public void setDefectQty(Integer defectQty) {
        this.defectQty = defectQty;
    }

    public Long getCurrentProcessId() {
        return currentProcessId;
    }

    public void setCurrentProcessId(Long currentProcessId) {
        this.currentProcessId = currentProcessId;
    }

    public String getCurrentProcessStatus() {
        return currentProcessStatus;
    }

    public void setCurrentProcessStatus(String currentProcessStatus) {
        this.currentProcessStatus = currentProcessStatus;
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

    public String getOutsourceBatchBarcode() {
        return outsourceBatchBarcode;
    }

    public void setOutsourceBatchBarcode(String outsourceBatchBarcode) {
        this.outsourceBatchBarcode = outsourceBatchBarcode;
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

    public Integer getConfirmQty() {
        return confirmQty;
    }

    public void setConfirmQty(Integer confirmQty) {
        this.confirmQty = confirmQty;
    }

    public Long getOutsourceId() {
        return outsourceId;
    }

    public void setOutsourceId(Long outsourceId) {
        this.outsourceId = outsourceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("jobNo", getJobNo())
                .append("producePlanId", getProducePlanId())
                .append("orderId", getOrderId())
                .append("colorCode", getColorCode())
                .append("sizeCode", getSizeCode())
                .append("planQty", getPlanQty())
                .append("actualQty", getActualQty())
                .append("defectQty", getDefectQty())
                .append("currentProcessId", getCurrentProcessId())
                .append("currentProcessStatus", getCurrentProcessStatus())
                .append("startTime", getStartTime())
                .append("finishTime", getFinishTime())
                .append("outsourceBatchBarcode", getOutsourceBatchBarcode())
                .append("outboundTime", getOutboundTime())
                .append("receiveTime", getReceiveTime())
                .append("confirmQty", getConfirmQty())
                .append("outsourceId", getOutsourceId())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
