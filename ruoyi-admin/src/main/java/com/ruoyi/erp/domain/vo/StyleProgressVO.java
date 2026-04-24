package com.ruoyi.erp.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 款号进度视图 VO
 *
 * @author ruoyi
 */
@Data
public class StyleProgressVO {

    /**
     * 款号
     */
    private String styleCode;

    /**
     * 整单编号
     */
    private String bulkOrderNo;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 销售单号
     */
    private String salesNo;

    /**
     * 总工单数
     */
    private Integer totalJobs;

    /**
     * 总计划数量
     */
    private Integer totalPlanQty;

    /**
     * 总完成数量
     */
    private Integer totalActualQty;

    /**
     * 完成率百分比
     */
    private BigDecimal completeRatePct;

    /**
     * 已发货数量
     */
    private Integer shippedQty;

    /**
     * 交货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;
}
