package com.ruoyi.erp.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 单件流水号对象 t_erp_product_serial
 *
 * @author ruoyi
 */
public class ProductSerial extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 流水ID
     */
    private Long id;

    /**
     * 流水号（唯一）
     */
    @Excel(name = "流水号")
    private String serialNo;

    /**
     * 销售订单ID
     */
    @Excel(name = "销售订单ID")
    private Long orderId;

    /**
     * 工票ID
     */
    @Excel(name = "工票ID")
    private Long jobId;

    /**
     * 生产计划ID
     */
    @Excel(name = "生产计划ID")
    private Long producePlanId;

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
     * 当前工序ID
     */
    @Excel(name = "当前工序ID")
    private Long currentProcessId;

    /**
     * 当前工序名称
     */
    @Excel(name = "当前工序名称")
    private String currentProcessName;

    /**
     * 状态 0在制 1已完工 2已入库 3已出货
     */
    @Excel(name = "状态", readConverterExp = "0=在制,1=已完工,2=已入库,3=已出货")
    private String status;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 完工时间
     */
    @Excel(name = "完工时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    /**
     * 入库时间
     */
    @Excel(name = "入库时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date warehouseTime;

    /**
     * 出货时间
     */
    @Excel(name = "出货时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date shipTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getProducePlanId() {
        return producePlanId;
    }

    public void setProducePlanId(Long producePlanId) {
        this.producePlanId = producePlanId;
    }

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

    public Long getCurrentProcessId() {
        return currentProcessId;
    }

    public void setCurrentProcessId(Long currentProcessId) {
        this.currentProcessId = currentProcessId;
    }

    public String getCurrentProcessName() {
        return currentProcessName;
    }

    public void setCurrentProcessName(String currentProcessName) {
        this.currentProcessName = currentProcessName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getWarehouseTime() {
        return warehouseTime;
    }

    public void setWarehouseTime(Date warehouseTime) {
        this.warehouseTime = warehouseTime;
    }

    public Date getShipTime() {
        return shipTime;
    }

    public void setShipTime(Date shipTime) {
        this.shipTime = shipTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("serialNo", getSerialNo())
                .append("orderId", getOrderId())
                .append("jobId", getJobId())
                .append("producePlanId", getProducePlanId())
                .append("colorCode", getColorCode())
                .append("sizeCode", getSizeCode())
                .append("currentProcessId", getCurrentProcessId())
                .append("currentProcessName", getCurrentProcessName())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("finishTime", getFinishTime())
                .append("warehouseTime", getWarehouseTime())
                .append("shipTime", getShipTime())
                .append("remark", getRemark())
                .toString();
    }
}
