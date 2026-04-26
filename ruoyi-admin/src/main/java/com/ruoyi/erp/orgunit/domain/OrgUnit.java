package com.ruoyi.erp.orgunit.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 组织层级对象 t_erp_org_unit
 * 组织层级：工厂 → 分厂 → 车间 → 班组 → 工位
 *
 * @author erp
 * @date 2026-04-25
 */
public class OrgUnit extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 父级ID */
    private Long parentId;

    /** 组织名称 */
    @NotBlank(message = "组织名称不能为空")
    @Excel(name = "组织名称")
    private String orgName;

    /** 组织编码 */
    @Excel(name = "组织编码")
    private String orgCode;

    /** 组织类型 */
    @NotBlank(message = "组织类型不能为空")
    @Excel(name = "组织类型", readConverterExp = "FACTORY=工厂,BRANCH_FACTORY=分厂,WORKSHOP=车间,TEAM=班组,STATION=工位,OUTSOURCE_VENDOR=外协厂,INSPECTION_COMPANY=检品公司")
    private String orgType;

    /** 所属工厂ID */
    private Long factoryId;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Integer orderNum;

    /** 负责人 */
    @Excel(name = "负责人")
    private String leader;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phone;

    /** 状态（0正常 1停用） */
    @NotBlank(message = "状态不能为空")
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setFactoryId(Long factoryId) {
        this.factoryId = factoryId;
    }

    public Long getFactoryId() {
        return factoryId;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getLeader() {
        return leader;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("parentId", getParentId())
            .append("orgName", getOrgName())
            .append("orgCode", getOrgCode())
            .append("orgType", getOrgType())
            .append("factoryId", getFactoryId())
            .append("orderNum", getOrderNum())
            .append("leader", getLeader())
            .append("phone", getPhone())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
