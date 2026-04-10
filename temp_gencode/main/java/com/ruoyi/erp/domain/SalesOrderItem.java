package com.ruoyi.erp.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 销售订单明细对象 t_erp_sales_order_item
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
public class SalesOrderItem extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 销售订单id */
    @Excel(name = "销售订单id")
    private Long salesOrderId;

    /** 打样通知id */
    @Excel(name = "打样通知id")
    private Long noticeId;

    /** 工艺书id */
    @Excel(name = "工艺书id")
    private Long techId;

    /** 颜色 */
    @Excel(name = "颜色")
    private String color;

    /** 尺码 */
    @Excel(name = "尺码")
    private String size;

    /** 订单数量 */
    @Excel(name = "订单数量")
    private BigDecimal orderQuantity;

    /** 排产数量 */
    @Excel(name = "排产数量")
    private BigDecimal planQuantity;

    /** 入库数量 */
    @Excel(name = "入库数量")
    private BigDecimal inboundAmount;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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
    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
    public void setSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }
    public void setOrderQuantity(BigDecimal orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public BigDecimal getOrderQuantity() {
        return orderQuantity;
    }
    public void setPlanQuantity(BigDecimal planQuantity) {
        this.planQuantity = planQuantity;
    }

    public BigDecimal getPlanQuantity() {
        return planQuantity;
    }
    public void setInboundAmount(BigDecimal inboundAmount) {
        this.inboundAmount = inboundAmount;
    }

    public BigDecimal getInboundAmount() {
        return inboundAmount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("salesOrderId", getSalesOrderId())
            .append("noticeId", getNoticeId())
            .append("techId", getTechId())
            .append("color", getColor())
            .append("size", getSize())
            .append("orderQuantity", getOrderQuantity())
            .append("planQuantity", getPlanQuantity())
            .append("inboundAmount", getInboundAmount())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
