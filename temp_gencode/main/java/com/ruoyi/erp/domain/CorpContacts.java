package com.ruoyi.erp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 公司联系人对象 t_erp_corp_contacts
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
public class CorpContacts extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 联系人id */
    private Long id;

    /** 公司类型 */
    @Excel(name = "公司类型")
    private String corpType;

    /** 公司id */
    @Excel(name = "公司id")
    private Long corpId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 部门 */
    @Excel(name = "部门")
    private String department;

    /** 性别-字典 */
    @Excel(name = "性别-字典")
    private String sex;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String mail;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setCorpType(String corpType) {
        this.corpType = corpType;
    }

    public String getCorpType() {
        return corpType;
    }
    public void setCorpId(Long corpId) {
        this.corpId = corpId;
    }

    public Long getCorpId() {
        return corpId;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("corpType", getCorpType())
            .append("corpId", getCorpId())
            .append("name", getName())
            .append("department", getDepartment())
            .append("sex", getSex())
            .append("phone", getPhone())
            .append("mail", getMail())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
