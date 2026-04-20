package com.ruoyi.erp.service;

import com.ruoyi.erp.domain.StockIn;
import com.ruoyi.erp.domain.StockOut;

/**
 * 单据下推服务接口
 *
 * @author zhangmingjian
 * @date 2026-04-20
 */
public interface IErpPushDownService {
    /**
     * 采购入库单确认后，更新采购订单明细已执行数量
     *
     * @param stockIn 入库单
     * @return 是否更新成功
     */
    boolean updatePurchaseExecuteQty(StockIn stockIn);

    /**
     * 销售出库单确认后，更新销售订单明细已执行数量
     *
     * @param stockOut 出库单
     * @return 是否更新成功
     */
    boolean updateSalesOrderExecuteQty(StockOut stockOut);

    /**
     * 入库单取消确认，回滚采购订单明细已执行数量
     *
     * @param stockIn 入库单
     * @return 是否回滚成功
     */
    boolean rollbackPurchaseExecuteQty(StockIn stockIn);

    /**
     * 出库单取消确认，回滚销售订单明细已执行数量
     *
     * @param stockOut 出库单
     * @return 是否回滚成功
     */
    boolean rollbackSalesOrderExecuteQty(StockOut stockOut);
}
