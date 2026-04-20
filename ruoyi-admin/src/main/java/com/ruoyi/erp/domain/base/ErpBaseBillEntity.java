package com.ruoyi.erp.domain.base;

import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * ERP业务单据基类
 * 所有业务单据（采购订单/销售订单/入库单/出库单）必须继承此类
 * 支持来源单据下推（销售订单 → 出库单），记录已执行数量
 *
 * 按照Gemini/金蝶工业级最佳实践设计
 *
 * @author ruoyi
 * @date 2026-04-16
 */
public class ErpBaseBillEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 来源单据类型
     * 比如："sal_order" 表示来自销售订单
     */
    private String srcBillType;

    /**
     * 来源单据ID
     */
    private Long srcBillId;

    /**
     * 来源单据号
     */
    private String srcBillNo;

    public String getSrcBillType() {
        return srcBillType;
    }

    public void setSrcBillType(String srcBillType) {
        this.srcBillType = srcBillType;
    }

    public Long getSrcBillId() {
        return srcBillId;
    }

    public void setSrcBillId(Long srcBillId) {
        this.srcBillId = srcBillId;
    }

    public String getSrcBillNo() {
        return srcBillNo;
    }

    public void setSrcBillNo(String srcBillNo) {
        this.srcBillNo = srcBillNo;
    }
}
