package com.ruoyi.erp.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.erp.domain.base.ErpBaseBillEntity;

/**
 * 生产计划对象 t_erp_produce_plan
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public class ProducePlan extends ErpBaseBillEntity {

    /** id */
    private Long id;

    /** 生产状态 */
    @Excel(name = "生产状态")
    private String produceStatus;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 大货款号 */
    @Excel(name = "大货款号")
    private String bulkOrderNo;

    /** 打样款号 */
    @Excel(name = "打样款号")
    private String sampleStyleNo;

    /** 工厂内部款号（KN-YY-SS-NNN） */
    @Excel(name = "款号")
    private String styleCode;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String customerName;

    /** 款式品类 */
    @Excel(name = "款式品类")
    private String styleCategory;

    /** 业务员 */
    @Excel(name = "业务员")
    private String salesName;

    /** 销售订单id */
    @Excel(name = "销售订单id")
    private Long salesOrderId;

    /** 打样通知id */
    @Excel(name = "打样通知id")
    private Long noticeId;

    /** 工艺书id */
    @Excel(name = "工艺书id")
    private Long techId;

    /** 计划单编号 */
    @Excel(name = "计划单编号")
    private String planNo;

    /** 跟单人id */
    @Excel(name = "跟单人id")
    private String salesId;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 领料完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "领料完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date pickedDate;

    /** 完成日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "完成日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date completeDate;

    /** 原料到货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "原料到货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date materialArrivalDate;

    /** 上线日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上线日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date upDate;

    /** 下线日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "下线日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date downDate;

    /** 生产方式 1-本厂 2-委外 */
    @Excel(name = "生产方式 1-本厂 2-委外")
    private String produceType;

    /** 工厂 */
    @Excel(name = "工厂")
    private String inFactory;

    /** 委外工厂 */
    @Excel(name = "委外工厂")
    private String outFactory;

    /** 委外时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "委外时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date outDate;

    /** 前道日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "前道日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date preProcessDate;

    /** 送检日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "送检日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inspectionDate;

    /** 送检公司 */
    @Excel(name = "送检公司")
    private String inspectionCorp;

    /** 进仓日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "进仓日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inBoundDate;

    /** 船期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "船期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date shippingDate;

    /** 后道日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "后道日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date postProcessDate;

    /** 交期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "交期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDate;

    /** 订单状态 1-新建 2-已安排生产计划 3-生产中 4-部分入库 5-已全部入库 6-部分出库 7-已完成出库 8-交付中 9-完成 10-取消 */
    @Excel(name = "订单状态 1-新建 2-已安排生产计划 3-生产中 4-部分入库 5-已全部入库 6-部分出库 7-已完成出库 8-交付中 9-完成 10-取消")
    private String planStatus;

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

    /** 前道生产方式 */
    @Excel(name = "前道生产方式")
    private String preProduceType;

    /** 前道工厂 */
    @Excel(name = "前道工厂")
    private String preInFactory;

    /** 前道委外工厂 */
    @Excel(name = "前道委外工厂")
    private String preOutFactory;

    /** 前道委外日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "前道委外日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date preOutDate;

    /** 后道生产方式 1-本厂 2-委外 */
    @Excel(name = "后道生产方式 1-本厂 2-委外")
    private String postProduceType;

    /** 后道本厂 */
    @Excel(name = "后道本厂")
    private String postInFactory;

    /** 后道委外工厂 */
    @Excel(name = "后道委外工厂")
    private String postOutFactory;

    /** 后道委外时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "后道委外时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date postOutDate;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getProduceStatus() {
        return produceStatus;
    }

    public void setProduceStatus(String produceStatus) {
        this.produceStatus = produceStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBulkOrderNo() {
        return bulkOrderNo;
    }

    public void setBulkOrderNo(String bulkOrderNo) {
        this.bulkOrderNo = bulkOrderNo;
    }

    public String getSampleStyleNo() {
        return sampleStyleNo;
    }

    public void setSampleStyleNo(String sampleStyleNo) {
        this.sampleStyleNo = sampleStyleNo;
    }

    public String getStyleCode() {
        return styleCode;
    }

    public void setStyleCode(String styleCode) {
        this.styleCode = styleCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStyleCategory() {
        return styleCategory;
    }

    public void setStyleCategory(String styleCategory) {
        this.styleCategory = styleCategory;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }
    public void setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public Long getSalesOrderId() {
        return salesOrderId;
    }
    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getNoticeId() {
        return noticeId;
    }
    public void setTechId(Long techId) {
        this.techId = techId;
    }

    public Long getTechId() {
        return techId;
    }
    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getPlanNo() {
        return planNo;
    }
    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public String getSalesId() {
        return salesId;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }
    public void setPickedDate(Date pickedDate) {
        this.pickedDate = pickedDate;
    }

    public Date getPickedDate() {
        return pickedDate;
    }
    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }
    public void setMaterialArrivalDate(Date materialArrivalDate) {
        this.materialArrivalDate = materialArrivalDate;
    }

    public Date getMaterialArrivalDate() {
        return materialArrivalDate;
    }
    public void setUpDate(Date upDate) {
        this.upDate = upDate;
    }

    public Date getUpDate() {
        return upDate;
    }
    public void setDownDate(Date downDate) {
        this.downDate = downDate;
    }

    public Date getDownDate() {
        return downDate;
    }
    public void setProduceType(String produceType) {
        this.produceType = produceType;
    }

    public String getProduceType() {
        return produceType;
    }
    public void setInFactory(String inFactory) {
        this.inFactory = inFactory;
    }

    public String getInFactory() {
        return inFactory;
    }
    public void setOutFactory(String outFactory) {
        this.outFactory = outFactory;
    }

    public String getOutFactory() {
        return outFactory;
    }
    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public Date getOutDate() {
        return outDate;
    }
    public void setPreProcessDate(Date preProcessDate) {
        this.preProcessDate = preProcessDate;
    }

    public Date getPreProcessDate() {
        return preProcessDate;
    }
    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }
    public void setInspectionCorp(String inspectionCorp) {
        this.inspectionCorp = inspectionCorp;
    }

    public String getInspectionCorp() {
        return inspectionCorp;
    }
    public void setInBoundDate(Date inBoundDate) {
        this.inBoundDate = inBoundDate;
    }

    public Date getInBoundDate() {
        return inBoundDate;
    }
    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public Date getShippingDate() {
        return shippingDate;
    }
    public void setPostProcessDate(Date postProcessDate) {
        this.postProcessDate = postProcessDate;
    }

    public Date getPostProcessDate() {
        return postProcessDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }
    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public String getPlanStatus() {
        return planStatus;
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
    public void setPreProduceType(String preProduceType) {
        this.preProduceType = preProduceType;
    }

    public String getPreProduceType() {
        return preProduceType;
    }
    public void setPreInFactory(String preInFactory) {
        this.preInFactory = preInFactory;
    }

    public String getPreInFactory() {
        return preInFactory;
    }
    public void setPreOutFactory(String preOutFactory) {
        this.preOutFactory = preOutFactory;
    }

    public String getPreOutFactory() {
        return preOutFactory;
    }
    public void setPreOutDate(Date preOutDate) {
        this.preOutDate = preOutDate;
    }

    public Date getPreOutDate() {
        return preOutDate;
    }
    public void setPostProduceType(String postProduceType) {
        this.postProduceType = postProduceType;
    }

    public String getPostProduceType() {
        return postProduceType;
    }
    public void setPostInFactory(String postInFactory) {
        this.postInFactory = postInFactory;
    }

    public String getPostInFactory() {
        return postInFactory;
    }
    public void setPostOutFactory(String postOutFactory) {
        this.postOutFactory = postOutFactory;
    }

    public String getPostOutFactory() {
        return postOutFactory;
    }
    public void setPostOutDate(Date postOutDate) {
        this.postOutDate = postOutDate;
    }

    public Date getPostOutDate() {
        return postOutDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("produceStatus", getProduceStatus())
            .append("type", getType())
            .append("planNo", getPlanNo())
            .append("bulkOrderNo", getBulkOrderNo())
            .append("sampleStyleNo", getSampleStyleNo())
            .append("styleCode", getStyleCode())
            .append("customerName", getCustomerName())
            .append("styleCategory", getStyleCategory())
            .append("salesName", getSalesName())
            .append("materialArrivalDate", getMaterialArrivalDate())
            .append("preProcessDate", getPreProcessDate())
            .append("inspectionDate", getInspectionDate())
            .append("inBoundDate", getInBoundDate())
            .append("salesOrderId", getSalesOrderId())
            .append("noticeId", getNoticeId())
            .append("techId", getTechId())
            .append("planNo", getPlanNo())
            .append("salesId", getSalesId())
            .append("startDate", getStartDate())
            .append("pickedDate", getPickedDate())
            .append("completeDate", getCompleteDate())
            .append("materialArrivalDate", getMaterialArrivalDate())
            .append("upDate", getUpDate())
            .append("downDate", getDownDate())
            .append("produceType", getProduceType())
            .append("inFactory", getInFactory())
            .append("outFactory", getOutFactory())
            .append("outDate", getOutDate())
            .append("preProcessDate", getPreProcessDate())
            .append("inspectionDate", getInspectionDate())
            .append("inspectionCorp", getInspectionCorp())
            .append("inBoundDate", getInBoundDate())
            .append("shippingDate", getShippingDate())
            .append("postProcessDate", getPostProcessDate())
            .append("dueDate", getDueDate())
            .append("planStatus", getPlanStatus())
            .append("auditStatus", getAuditStatus())
            .append("auditBy", getAuditBy())
            .append("auditTime", getAuditTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("preProduceType", getPreProduceType())
            .append("preInFactory", getPreInFactory())
            .append("preOutFactory", getPreOutFactory())
            .append("preOutDate", getPreOutDate())
            .append("postProduceType", getPostProduceType())
            .append("postInFactory", getPostInFactory())
            .append("postOutFactory", getPostOutFactory())
            .append("postOutDate", getPostOutDate())
            .toString();
    }
}
