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

    /** 审批状态 */
    @Excel(name = "审批状态")
    private String auditStatus;

    /** 进行状态 */
    @Excel(name = "进行状态")
    private String progressStatus;

    /** 客户名称 */
    @Excel(name = "客户")
    private String customerName;

    /** 打样类型 */
    @Excel(name = "打样类型")
    private String sampleTypeDisplay;

    /** 样品款式 */
    @Excel(name = "样品款式")
    private String styleType;

    /** 样品类型 */
    @Excel(name = "样品类型")
    private String sampleCategoryType;

    /** 款号 */
    @Excel(name = "款号")
    private String styleCode;

    /** 大货款号 */
    @Excel(name = "大货款号")
    private String bulkOrderNo;

    /** 业务员 */
    @Excel(name = "业务员")
    private String salesName;

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

    /** 审批人 */
    @Excel(name = "审批人")
    private String auditBy;

    /** 审批时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date auditTime;

    /** 染整：缩水率上限（%，超出触发预缩工序） */
    @Excel(name = "缩水率上限(%)")
    private java.math.BigDecimal shrinkageRateLimit;

    /** 染整：色差等级下限（GB/T 250，低于此值不合格） */
    @Excel(name = "色差等级下限")
    private java.math.BigDecimal colorDifferenceGradeMin;

    /** 染整：定型温度上限（℃，超出损伤面料） */
    @Excel(name = "定型温度上限(℃)")
    private Integer settingTempMax;

    /** 染整：定型温度下限（℃） */
    @Excel(name = "定型温度下限(℃)")
    private Integer settingTempMin;

    /** 染整：水洗色牢度要求（JIS L 0844，4级以上） */
    @Excel(name = "水洗色牢度要求")
    private String washFastnessRequirement;

    /** 染整：摩擦色牢度要求（JIS L 0849） */
    @Excel(name = "摩擦色牢度要求")
    private String rubFastnessRequirement;

    /** 染整：pH值范围（对日要求 4.0-7.5） */
    @Excel(name = "pH值范围")
    private String phRange;

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

    public java.math.BigDecimal getShrinkageRateLimit() { return shrinkageRateLimit; }
    public void setShrinkageRateLimit(java.math.BigDecimal shrinkageRateLimit) { this.shrinkageRateLimit = shrinkageRateLimit; }

    public java.math.BigDecimal getColorDifferenceGradeMin() { return colorDifferenceGradeMin; }
    public void setColorDifferenceGradeMin(java.math.BigDecimal colorDifferenceGradeMin) { this.colorDifferenceGradeMin = colorDifferenceGradeMin; }

    public Integer getSettingTempMax() { return settingTempMax; }
    public void setSettingTempMax(Integer settingTempMax) { this.settingTempMax = settingTempMax; }

    public Integer getSettingTempMin() { return settingTempMin; }
    public void setSettingTempMin(Integer settingTempMin) { this.settingTempMin = settingTempMin; }

    public String getWashFastnessRequirement() { return washFastnessRequirement; }
    public void setWashFastnessRequirement(String washFastnessRequirement) { this.washFastnessRequirement = washFastnessRequirement; }

    public String getRubFastnessRequirement() { return rubFastnessRequirement; }
    public void setRubFastnessRequirement(String rubFastnessRequirement) { this.rubFastnessRequirement = rubFastnessRequirement; }

    public String getPhRange() { return phRange; }
    public void setPhRange(String phRange) { this.phRange = phRange; }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setSampleTypeDisplay(String sampleTypeDisplay) {
        this.sampleTypeDisplay = sampleTypeDisplay;
    }

    public String getSampleTypeDisplay() {
        return sampleTypeDisplay;
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

    public void setStyleCode(String styleCode) {
        this.styleCode = styleCode;
    }

    public String getStyleCode() {
        return styleCode;
    }

    public void setBulkOrderNo(String bulkOrderNo) {
        this.bulkOrderNo = bulkOrderNo;
    }

    public String getBulkOrderNo() {
        return bulkOrderNo;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public String getSalesName() {
        return salesName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("auditStatus", getAuditStatus())
            .append("progressStatus", getProgressStatus())
            .append("customerName", getCustomerName())
            .append("sampleTypeDisplay", getSampleTypeDisplay())
            .append("styleType", getStyleType())
            .append("sampleCategoryType", getSampleCategoryType())
            .append("styleCode", getStyleCode())
            .append("bulkOrderNo", getBulkOrderNo())
            .append("salesName", getSalesName())
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
            .append("shrinkageRateLimit", getShrinkageRateLimit())
            .append("colorDifferenceGradeMin", getColorDifferenceGradeMin())
            .append("settingTempMax", getSettingTempMax())
            .append("settingTempMin", getSettingTempMin())
            .append("washFastnessRequirement", getWashFastnessRequirement())
            .append("rubFastnessRequirement", getRubFastnessRequirement())
            .append("phRange", getPhRange())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
