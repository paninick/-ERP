package com.ruoyi.erp.style.domain;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class ErpStyle extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "款号不能为空")
    @Excel(name = "内部款号")
    private String styleCode;

    @Excel(name = "客户款号")
    private String customerStyleCode;

    @Excel(name = "款号名称")
    private String styleName;

    @Excel(name = "产品族")
    private String productFamily;

    private Long customerId;

    @Excel(name = "季节")
    private String season;

    @Excel(name = "纱线类型")
    private String yarnType;

    @Excel(name = "机号")
    private String gauge;

    @Excel(name = "克重")
    private BigDecimal weight;

    @Excel(name = "BOM版本")
    private String bomVersion;

    @Excel(name = "工艺路线版本")
    private String routeVersion;

    @NotBlank(message = "状态不能为空")
    @Excel(name = "状态")
    private String status;

    private Long factoryId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStyleCode() { return styleCode; }
    public void setStyleCode(String styleCode) { this.styleCode = styleCode; }
    public String getCustomerStyleCode() { return customerStyleCode; }
    public void setCustomerStyleCode(String customerStyleCode) { this.customerStyleCode = customerStyleCode; }
    public String getStyleName() { return styleName; }
    public void setStyleName(String styleName) { this.styleName = styleName; }
    public String getProductFamily() { return productFamily; }
    public void setProductFamily(String productFamily) { this.productFamily = productFamily; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public String getSeason() { return season; }
    public void setSeason(String season) { this.season = season; }
    public String getYarnType() { return yarnType; }
    public void setYarnType(String yarnType) { this.yarnType = yarnType; }
    public String getGauge() { return gauge; }
    public void setGauge(String gauge) { this.gauge = gauge; }
    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }
    public String getBomVersion() { return bomVersion; }
    public void setBomVersion(String bomVersion) { this.bomVersion = bomVersion; }
    public String getRouteVersion() { return routeVersion; }
    public void setRouteVersion(String routeVersion) { this.routeVersion = routeVersion; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getFactoryId() { return factoryId; }
    public void setFactoryId(Long factoryId) { this.factoryId = factoryId; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", id).append("styleCode", styleCode)
            .append("customerStyleCode", customerStyleCode)
            .append("productFamily", productFamily).append("status", status).toString();
    }
}
