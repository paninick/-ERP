package com.ruoyi.erp.inspection.domain;

import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class ErpInspectionCompany extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "检品公司名称不能为空")
    @Excel(name = "公司名称")
    private String companyName;

    @Excel(name = "公司编码")
    private String companyCode;

    @Excel(name = "联系人")
    private String contact;

    @Excel(name = "联系电话")
    private String phone;

    @Excel(name = "邮箱")
    private String email;

    @Excel(name = "地址")
    private String address;

    @Excel(name = "AQL标准")
    private String aqlStandard;

    @NotBlank(message = "状态不能为空")
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getCompanyCode() { return companyCode; }
    public void setCompanyCode(String companyCode) { this.companyCode = companyCode; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getAqlStandard() { return aqlStandard; }
    public void setAqlStandard(String aqlStandard) { this.aqlStandard = aqlStandard; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", id).append("companyName", companyName)
            .append("companyCode", companyCode).append("status", status).toString();
    }
}
