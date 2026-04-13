package com.ruoyi.erp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 公司开票信息对象 t_erp_corp_invoice
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public class CorpInvoice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 类型 1-供应商 2-物流公司 */
    @Excel(name = "类型 1-供应商 2-物流公司")
    private String corpType;

    /** 公司id */
    private Long corpId;

    /** 纳税人识别号 */
    @Excel(name = "纳税人识别号")
    private String socialNumber;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String corpName;

    /** 单位地址 */
    @Excel(name = "单位地址")
    private String corpAddress;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String corpPhone;

    /** 开户银行 */
    @Excel(name = "开户银行")
    private String corpBank;

    /** 银行账号 */
    @Excel(name = "银行账号")
    private String corpBankNo;

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
    public void setSocialNumber(String socialNumber) {
        this.socialNumber = socialNumber;
    }

    public String getSocialNumber() {
        return socialNumber;
    }
    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getCorpName() {
        return corpName;
    }
    public void setCorpAddress(String corpAddress) {
        this.corpAddress = corpAddress;
    }

    public String getCorpAddress() {
        return corpAddress;
    }
    public void setCorpPhone(String corpPhone) {
        this.corpPhone = corpPhone;
    }

    public String getCorpPhone() {
        return corpPhone;
    }
    public void setCorpBank(String corpBank) {
        this.corpBank = corpBank;
    }

    public String getCorpBank() {
        return corpBank;
    }
    public void setCorpBankNo(String corpBankNo) {
        this.corpBankNo = corpBankNo;
    }

    public String getCorpBankNo() {
        return corpBankNo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("corpType", getCorpType())
            .append("corpId", getCorpId())
            .append("socialNumber", getSocialNumber())
            .append("corpName", getCorpName())
            .append("corpAddress", getCorpAddress())
            .append("corpPhone", getCorpPhone())
            .append("corpBank", getCorpBank())
            .append("corpBankNo", getCorpBankNo())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
