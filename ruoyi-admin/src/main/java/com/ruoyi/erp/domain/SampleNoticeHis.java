package com.ruoyi.erp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 打样通知历史对象 t_erp_sample_notice_his
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public class SampleNoticeHis extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 当前打样通知id */
    private Long currentNoticeId;

    /** 打样通知id */
    @Excel(name = "打样通知id")
    private Long noticeId;

    /** 打样编号 */
    @Excel(name = "打样编号")
    private String sampleNo;

    /** 款式 */
    @Excel(name = "款式")
    private String styleType;

    /** 样品种类：关联字典erp_sample_category */
    @Excel(name = "样品种类：关联字典erp_sample_category")
    private String sampleCategoryType;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setCurrentNoticeId(Long currentNoticeId) {
        this.currentNoticeId = currentNoticeId;
    }

    public Long getCurrentNoticeId() {
        return currentNoticeId;
    }
    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getNoticeId() {
        return noticeId;
    }
    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getSampleNo() {
        return sampleNo;
    }
    public void setStyleType(String styleType) {
        this.styleType = styleType;
    }

    public String getStyleType() {
        return styleType;
    }
    public void setSampleCategoryType(String sampleCategoryType) {
        this.sampleCategoryType = sampleCategoryType;
    }

    public String getSampleCategoryType() {
        return sampleCategoryType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("currentNoticeId", getCurrentNoticeId())
            .append("noticeId", getNoticeId())
            .append("sampleNo", getSampleNo())
            .append("styleType", getStyleType())
            .append("sampleCategoryType", getSampleCategoryType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
