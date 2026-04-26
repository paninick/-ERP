package com.ruoyi.erp.inspection.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

public class ErpInspectionBooking extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "预约单号")
    private String bookingNo;

    private Long salesOrderId;

    @Excel(name = "销售单号")
    private String salesNo;

    @Excel(name = "款号")
    private String styleCode;

    @NotNull(message = "检品公司不能为空")
    private Long inspectionCompanyId;

    @Excel(name = "预约日期")
    private Date bookingDate;

    @Excel(name = "检品日期")
    private Date inspectionDate;

    @Excel(name = "检品结果")
    private String inspectionResult;

    @Excel(name = "报告编号")
    private String reportNo;

    @Excel(name = "缺陷摘要")
    private String defectSummary;

    private String releaseBy;
    private Date releaseTime;

    @NotBlank(message = "状态不能为空")
    @Excel(name = "状态")
    private String status;

    private Long factoryId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBookingNo() { return bookingNo; }
    public void setBookingNo(String bookingNo) { this.bookingNo = bookingNo; }
    public Long getSalesOrderId() { return salesOrderId; }
    public void setSalesOrderId(Long salesOrderId) { this.salesOrderId = salesOrderId; }
    public String getSalesNo() { return salesNo; }
    public void setSalesNo(String salesNo) { this.salesNo = salesNo; }
    public String getStyleCode() { return styleCode; }
    public void setStyleCode(String styleCode) { this.styleCode = styleCode; }
    public Long getInspectionCompanyId() { return inspectionCompanyId; }
    public void setInspectionCompanyId(Long inspectionCompanyId) { this.inspectionCompanyId = inspectionCompanyId; }
    public Date getBookingDate() { return bookingDate; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }
    public Date getInspectionDate() { return inspectionDate; }
    public void setInspectionDate(Date inspectionDate) { this.inspectionDate = inspectionDate; }
    public String getInspectionResult() { return inspectionResult; }
    public void setInspectionResult(String inspectionResult) { this.inspectionResult = inspectionResult; }
    public String getReportNo() { return reportNo; }
    public void setReportNo(String reportNo) { this.reportNo = reportNo; }
    public String getDefectSummary() { return defectSummary; }
    public void setDefectSummary(String defectSummary) { this.defectSummary = defectSummary; }
    public String getReleaseBy() { return releaseBy; }
    public void setReleaseBy(String releaseBy) { this.releaseBy = releaseBy; }
    public Date getReleaseTime() { return releaseTime; }
    public void setReleaseTime(Date releaseTime) { this.releaseTime = releaseTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getFactoryId() { return factoryId; }
    public void setFactoryId(Long factoryId) { this.factoryId = factoryId; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", id).append("bookingNo", bookingNo)
            .append("styleCode", styleCode).append("status", status).toString();
    }
}
