package com.ruoyi.erp.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

public class QcInspection extends BaseEntity {
    private Long id;
    private String batchNo;
    private String styleCode;
    private String orderNo;
    private String result;
    private BigDecimal passRate;
    private Integer sampleQty;
    private Integer defectQty;
    private Long inspectorId;
    private String inspectorName;
    private Long factoryId;
    private String status;
    private String rejectReason;
    private String qcType;
    private String sizeMeasure;
    private String needleDetect;
    private Integer recheckCount;
    private String defectSegment;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBatchNo() { return batchNo; }
    public void setBatchNo(String batchNo) { this.batchNo = batchNo; }
    public String getStyleCode() { return styleCode; }
    public void setStyleCode(String styleCode) { this.styleCode = styleCode; }
    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public BigDecimal getPassRate() { return passRate; }
    public void setPassRate(BigDecimal passRate) { this.passRate = passRate; }
    public Integer getSampleQty() { return sampleQty; }
    public void setSampleQty(Integer sampleQty) { this.sampleQty = sampleQty; }
    public Integer getDefectQty() { return defectQty; }
    public void setDefectQty(Integer defectQty) { this.defectQty = defectQty; }
    public Long getInspectorId() { return inspectorId; }
    public void setInspectorId(Long inspectorId) { this.inspectorId = inspectorId; }
    public String getInspectorName() { return inspectorName; }
    public void setInspectorName(String inspectorName) { this.inspectorName = inspectorName; }
    public Long getFactoryId() { return factoryId; }
    public void setFactoryId(Long factoryId) { this.factoryId = factoryId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getRejectReason() { return rejectReason; }
    public void setRejectReason(String rejectReason) { this.rejectReason = rejectReason; }
    public String getQcType() { return qcType; }
    public void setQcType(String qcType) { this.qcType = qcType; }
    public String getSizeMeasure() { return sizeMeasure; }
    public void setSizeMeasure(String sizeMeasure) { this.sizeMeasure = sizeMeasure; }
    public String getNeedleDetect() { return needleDetect; }
    public void setNeedleDetect(String needleDetect) { this.needleDetect = needleDetect; }
    public Integer getRecheckCount() { return recheckCount; }
    public void setRecheckCount(Integer recheckCount) { this.recheckCount = recheckCount; }
    public String getDefectSegment() { return defectSegment; }
    public void setDefectSegment(String defectSegment) { this.defectSegment = defectSegment; }
}
