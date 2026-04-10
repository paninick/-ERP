package com.ruoyi.erp.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 打样通知-样衣要求对象 t_erp_sample_notice_detail
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
public class SampleNoticeDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 打样通知id */
    @Excel(name = "打样通知id")
    private Long noticeId;

    /** 颜色 */
    @Excel(name = "颜色")
    private String sampleColor;

    /** 规格 */
    @Excel(name = "规格")
    private String sampleSize;

    /** 数量 */
    @Excel(name = "数量")
    private Long sampleCount;

    /** 要求交期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "要求交期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDate;

    /** 客户意见 */
    @Excel(name = "客户意见")
    private String customerComment;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getNoticeId() {
        return noticeId;
    }
    public void setSampleColor(String sampleColor) {
        this.sampleColor = sampleColor;
    }

    public String getSampleColor() {
        return sampleColor;
    }
    public void setSampleSize(String sampleSize) {
        this.sampleSize = sampleSize;
    }

    public String getSampleSize() {
        return sampleSize;
    }
    public void setSampleCount(Long sampleCount) {
        this.sampleCount = sampleCount;
    }

    public Long getSampleCount() {
        return sampleCount;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }
    public void setCustomerComment(String customerComment) {
        this.customerComment = customerComment;
    }

    public String getCustomerComment() {
        return customerComment;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("noticeId", getNoticeId())
            .append("sampleColor", getSampleColor())
            .append("sampleSize", getSampleSize())
            .append("sampleCount", getSampleCount())
            .append("dueDate", getDueDate())
            .append("customerComment", getCustomerComment())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
