package com.ruoyi.erp.workcenter.domain;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class ErpWorkCenter extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "工作中心编码不能为空")
    @Excel(name = "编码")
    private String centerCode;

    @NotBlank(message = "工作中心名称不能为空")
    @Excel(name = "名称")
    private String centerName;

    @NotBlank(message = "类型不能为空")
    @Excel(name = "类型")
    private String centerType;

    private Long workshopId;

    @Excel(name = "日产能")
    private BigDecimal capacity;

    @Excel(name = "产能单位")
    private String capacityUnit;

    @Excel(name = "负责人")
    private String manager;

    @NotBlank(message = "状态不能为空")
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCenterCode() { return centerCode; }
    public void setCenterCode(String centerCode) { this.centerCode = centerCode; }
    public String getCenterName() { return centerName; }
    public void setCenterName(String centerName) { this.centerName = centerName; }
    public String getCenterType() { return centerType; }
    public void setCenterType(String centerType) { this.centerType = centerType; }
    public Long getWorkshopId() { return workshopId; }
    public void setWorkshopId(Long workshopId) { this.workshopId = workshopId; }
    public BigDecimal getCapacity() { return capacity; }
    public void setCapacity(BigDecimal capacity) { this.capacity = capacity; }
    public String getCapacityUnit() { return capacityUnit; }
    public void setCapacityUnit(String capacityUnit) { this.capacityUnit = capacityUnit; }
    public String getManager() { return manager; }
    public void setManager(String manager) { this.manager = manager; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", id).append("centerCode", centerCode)
            .append("centerName", centerName).append("centerType", centerType)
            .append("workshopId", workshopId).append("status", status).toString();
    }
}
