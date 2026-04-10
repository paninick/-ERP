package com.ruoyi.erp.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 生产计划衣服明细对象 t_erp_produce_plan_clothes
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
public class ProducePlanClothes extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 销售订单id */
    @Excel(name = "销售订单id")
    private Long planId;

    /** 销售明细id */
    @Excel(name = "销售明细id")
    private Long salesItemId;

    /** 颜色 */
    @Excel(name = "颜色")
    private String color;

    /** 尺码 */
    @Excel(name = "尺码")
    private String size;

    /** 订单数量 */
    @Excel(name = "订单数量")
    private BigDecimal orderQuantity;

    /** 额外数量 */
    @Excel(name = "额外数量")
    private BigDecimal extraQuantity;

    /** 排产数量 */
    @Excel(name = "排产数量")
    private BigDecimal planQuantity;

    /** 入库数量 */
    @Excel(name = "入库数量")
    private BigDecimal inboundQuantity;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getPlanId() {
        return planId;
    }
    public void setSalesItemId(Long salesItemId) {
        this.salesItemId = salesItemId;
    }

    public Long getSalesItemId() {
        return salesItemId;
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
    public void setExtraQuantity(BigDecimal extraQuantity) {
        this.extraQuantity = extraQuantity;
    }

    public BigDecimal getExtraQuantity() {
        return extraQuantity;
    }
    public void setPlanQuantity(BigDecimal planQuantity) {
        this.planQuantity = planQuantity;
    }

    public BigDecimal getPlanQuantity() {
        return planQuantity;
    }
    public void setInboundQuantity(BigDecimal inboundQuantity) {
        this.inboundQuantity = inboundQuantity;
    }

    public BigDecimal getInboundQuantity() {
        return inboundQuantity;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("planId", getPlanId())
            .append("salesItemId", getSalesItemId())
            .append("color", getColor())
            .append("size", getSize())
            .append("orderQuantity", getOrderQuantity())
            .append("extraQuantity", getExtraQuantity())
            .append("planQuantity", getPlanQuantity())
            .append("inboundQuantity", getInboundQuantity())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
