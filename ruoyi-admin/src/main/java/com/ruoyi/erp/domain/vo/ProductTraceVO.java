package com.ruoyi.erp.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 产品追溯视图 VO
 *
 * @author ruoyi
 */
@Data
public class ProductTraceVO {

    /**
     * 销售订单ID
     */
    private Long salesOrderId;

    /**
     * 销售单号
     */
    private String salesNo;

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
     * 生产计划ID
     */
    private Long producePlanId;

    /**
     * 计划编号
     */
    private String planNo;

    /**
     * 工票ID
     */
    private Long produceJobId;

    /**
     * 工票编号
     */
    private String jobNo;

    /**
     * 颜色
     */
    private String colorCode;

    /**
     * 尺码
     */
    private String sizeCode;

    /**
     * 计划数量
     */
    private Integer planQty;

    /**
     * 实际完成数量
     */
    private Integer actualQty;

    /**
     * 成品流水ID
     */
    private Long serialId;

    /**
     * 成品流水号
     */
    private String serialNo;

    /**
     * 成品状态
     */
    private String serialStatus;

    /**
     * 成品状态名称
     */
    private String serialStatusName;

    /**
     * 当前工序名称
     */
    private String currentProcessName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date serialCreateTime;

    /**
     * 完工时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date finishTime;

    /**
     * 入库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date warehouseTime;

    /**
     * 出货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date shipTime;
}
