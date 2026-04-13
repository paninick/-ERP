package com.ruoyi.erp.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工艺指示书对象 t_erp_sample_tech
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public class SampleTech extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 打样类型：1-服装 2-毛衣 */
    @Excel(name = "打样类型：1-服装 2-毛衣")
    private String sampleType;

    /** 打样id */
    @Excel(name = "打样id")
    private Long sampleId;

    /** 要求交期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "要求交期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDate;

    /** 款式图 */
    @Excel(name = "款式图")
    private String stylePic;

    /** 订标位置示意图 */
    @Excel(name = "订标位置示意图")
    private String tagPic;

    /** 订表位置说明 */
    @Excel(name = "订表位置说明")
    private String tagPicRemark;

    /** 制版人id */
    @Excel(name = "制版人id")
    private Long pattenMarker;

    /** 核版人id */
    @Excel(name = "核版人id")
    private Long pattenChecker;

    /** 裁剪要求 */
    @Excel(name = "裁剪要求")
    private String cuttingTip;

    /** 用衬要求 */
    @Excel(name = "用衬要求")
    private String liningTip;

    /** 用线要求 */
    @Excel(name = "用线要求")
    private String threadTip;

    /** 运针要求 */
    @Excel(name = "运针要求")
    private String needleTip;

    /** 缝制工艺说明 */
    @Excel(name = "缝制工艺说明")
    private String sewingTip;

    /** 后套工艺说明 */
    @Excel(name = "后套工艺说明")
    private String backGarmentTip;

    /** 吊牌挂法 */
    @Excel(name = "吊牌挂法")
    private String tagHangingTip;

    /** 整烫要求 */
    @Excel(name = "整烫要求")
    private String ironingTip;

    /** 织造要求 */
    @Excel(name = "织造要求")
    private String fabricTip;

    /** 套口要求 */
    @Excel(name = "套口要求")
    private String seamSealingTip;

    /** 手缝要求 */
    @Excel(name = "手缝要求")
    private String handStitchingTip;

    /** 套口手缝检验 */
    @Excel(name = "套口手缝检验")
    private String handStitchingInspection;

    /** 水洗要求 */
    @Excel(name = "水洗要求")
    private String washingTip;

    /** 审批状态 */
    @Excel(name = "审批状态")
    private String auditStatus;

    /** 审批人 */
    @Excel(name = "审批人")
    private String auditBy;

    /** 审批时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date auditTime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getSampleType() {
        return sampleType;
    }
    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

    public Long getSampleId() {
        return sampleId;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }
    public void setStylePic(String stylePic) {
        this.stylePic = stylePic;
    }

    public String getStylePic() {
        return stylePic;
    }
    public void setTagPic(String tagPic) {
        this.tagPic = tagPic;
    }

    public String getTagPic() {
        return tagPic;
    }
    public void setTagPicRemark(String tagPicRemark) {
        this.tagPicRemark = tagPicRemark;
    }

    public String getTagPicRemark() {
        return tagPicRemark;
    }
    public void setPattenMarker(Long pattenMarker) {
        this.pattenMarker = pattenMarker;
    }

    public Long getPattenMarker() {
        return pattenMarker;
    }
    public void setPattenChecker(Long pattenChecker) {
        this.pattenChecker = pattenChecker;
    }

    public Long getPattenChecker() {
        return pattenChecker;
    }
    public void setCuttingTip(String cuttingTip) {
        this.cuttingTip = cuttingTip;
    }

    public String getCuttingTip() {
        return cuttingTip;
    }
    public void setLiningTip(String liningTip) {
        this.liningTip = liningTip;
    }

    public String getLiningTip() {
        return liningTip;
    }
    public void setThreadTip(String threadTip) {
        this.threadTip = threadTip;
    }

    public String getThreadTip() {
        return threadTip;
    }
    public void setNeedleTip(String needleTip) {
        this.needleTip = needleTip;
    }

    public String getNeedleTip() {
        return needleTip;
    }
    public void setSewingTip(String sewingTip) {
        this.sewingTip = sewingTip;
    }

    public String getSewingTip() {
        return sewingTip;
    }
    public void setBackGarmentTip(String backGarmentTip) {
        this.backGarmentTip = backGarmentTip;
    }

    public String getBackGarmentTip() {
        return backGarmentTip;
    }
    public void setTagHangingTip(String tagHangingTip) {
        this.tagHangingTip = tagHangingTip;
    }

    public String getTagHangingTip() {
        return tagHangingTip;
    }
    public void setIroningTip(String ironingTip) {
        this.ironingTip = ironingTip;
    }

    public String getIroningTip() {
        return ironingTip;
    }
    public void setFabricTip(String fabricTip) {
        this.fabricTip = fabricTip;
    }

    public String getFabricTip() {
        return fabricTip;
    }
    public void setSeamSealingTip(String seamSealingTip) {
        this.seamSealingTip = seamSealingTip;
    }

    public String getSeamSealingTip() {
        return seamSealingTip;
    }
    public void setHandStitchingTip(String handStitchingTip) {
        this.handStitchingTip = handStitchingTip;
    }

    public String getHandStitchingTip() {
        return handStitchingTip;
    }
    public void setHandStitchingInspection(String handStitchingInspection) {
        this.handStitchingInspection = handStitchingInspection;
    }

    public String getHandStitchingInspection() {
        return handStitchingInspection;
    }
    public void setWashingTip(String washingTip) {
        this.washingTip = washingTip;
    }

    public String getWashingTip() {
        return washingTip;
    }
    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditStatus() {
        return auditStatus;
    }
    public void setAuditBy(String auditBy) {
        this.auditBy = auditBy;
    }

    public String getAuditBy() {
        return auditBy;
    }
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sampleType", getSampleType())
            .append("sampleId", getSampleId())
            .append("dueDate", getDueDate())
            .append("stylePic", getStylePic())
            .append("tagPic", getTagPic())
            .append("tagPicRemark", getTagPicRemark())
            .append("pattenMarker", getPattenMarker())
            .append("pattenChecker", getPattenChecker())
            .append("cuttingTip", getCuttingTip())
            .append("liningTip", getLiningTip())
            .append("threadTip", getThreadTip())
            .append("needleTip", getNeedleTip())
            .append("sewingTip", getSewingTip())
            .append("backGarmentTip", getBackGarmentTip())
            .append("tagHangingTip", getTagHangingTip())
            .append("ironingTip", getIroningTip())
            .append("fabricTip", getFabricTip())
            .append("seamSealingTip", getSeamSealingTip())
            .append("handStitchingTip", getHandStitchingTip())
            .append("handStitchingInspection", getHandStitchingInspection())
            .append("washingTip", getWashingTip())
            .append("auditStatus", getAuditStatus())
            .append("auditBy", getAuditBy())
            .append("auditTime", getAuditTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
