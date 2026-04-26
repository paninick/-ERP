package com.ruoyi.erp.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class ErpShipment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "出货单号")
    private String shipmentNo;

    @Excel(name = "销售订单ID")
    private Long salesOrderId;

    @Excel(name = "销售单号")
    private String salesNo;

    @Excel(name = "款号")
    private String styleCode;

    private Long customerId;

    @Excel(name = "客户名称")
    private String customerName;

    @Excel(name = "出货日期", dateFormat = "yyyy-MM-dd")
    private Date shipmentDate;

    @Excel(name = "出货总数量")
    private BigDecimal totalQty;

    @Excel(name = "总箱数")
    private Integer totalCarton;

    @Excel(name = "总毛重")
    private BigDecimal totalWeight;

    @Excel(name = "总体积")
    private BigDecimal totalVolume;

    private String vesselName;
    private Date etd;
    private Date eta;
    private String portOfLoading;
    private String portOfDischarge;
    private String containerNo;
    private String blNo;
    private String invoiceNo;

    @Excel(name = "放行状态")
    private String releaseStatus;

    private String releaseBy;
    private Date releaseTime;

    @Excel(name = "状态")
    private String status;

    private Long factoryId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getShipmentNo() { return shipmentNo; }
    public void setShipmentNo(String v) { this.shipmentNo = v; }
    public Long getSalesOrderId() { return salesOrderId; }
    public void setSalesOrderId(Long v) { this.salesOrderId = v; }
    public String getSalesNo() { return salesNo; }
    public void setSalesNo(String v) { this.salesNo = v; }
    public String getStyleCode() { return styleCode; }
    public void setStyleCode(String v) { this.styleCode = v; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long v) { this.customerId = v; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String v) { this.customerName = v; }
    public Date getShipmentDate() { return shipmentDate; }
    public void setShipmentDate(Date v) { this.shipmentDate = v; }
    public BigDecimal getTotalQty() { return totalQty; }
    public void setTotalQty(BigDecimal v) { this.totalQty = v; }
    public Integer getTotalCarton() { return totalCarton; }
    public void setTotalCarton(Integer v) { this.totalCarton = v; }
    public BigDecimal getTotalWeight() { return totalWeight; }
    public void setTotalWeight(BigDecimal v) { this.totalWeight = v; }
    public BigDecimal getTotalVolume() { return totalVolume; }
    public void setTotalVolume(BigDecimal v) { this.totalVolume = v; }
    public String getVesselName() { return vesselName; }
    public Date getEtd() { return etd; }
    public void setEtd(Date v) { this.etd = v; }
    public Date getEta() { return eta; }
    public void setEta(Date v) { this.eta = v; }
    public String getPortOfLoading() { return portOfLoading; }
    public void setPortOfLoading(String v) { this.portOfLoading = v; }
    public String getPortOfDischarge() { return portOfDischarge; }
    public void setPortOfDischarge(String v) { this.portOfDischarge = v; }
    public void setVesselName(String v) { this.vesselName = v; }
    public String getContainerNo() { return containerNo; }
    public void setContainerNo(String v) { this.containerNo = v; }
    public String getBlNo() { return blNo; }
    public void setBlNo(String v) { this.blNo = v; }
    public String getInvoiceNo() { return invoiceNo; }
    public void setInvoiceNo(String v) { this.invoiceNo = v; }
    public String getReleaseStatus() { return releaseStatus; }
    public void setReleaseStatus(String v) { this.releaseStatus = v; }
    public String getReleaseBy() { return releaseBy; }
    public void setReleaseBy(String v) { this.releaseBy = v; }
    public Date getReleaseTime() { return releaseTime; }
    public void setReleaseTime(Date v) { this.releaseTime = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
    public Long getFactoryId() { return factoryId; }
    public void setFactoryId(Long v) { this.factoryId = v; }

    @Override
    public String toString() {
        return "ErpShipment{id=" + id + ", shipmentNo='" + shipmentNo + "', salesNo='" + salesNo + "', styleCode='" + styleCode + "', customerName='" + customerName + "', totalQty=" + totalQty + ", status='" + status + "', releaseStatus='" + releaseStatus + "'}";
    }
}
