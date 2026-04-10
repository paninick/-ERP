package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.SalesOrderItem;

/**
 * 销售订单明细Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
public interface ISalesOrderItemService {
    /**
     * 查询销售订单明细
     *
     * @param id 销售订单明细主键
     * @return 销售订单明细
     */
    SalesOrderItem selectSalesOrderItemById(Long id);

    /**
     * 查询销售订单明细列表
     *
     * @param salesOrderItem 销售订单明细
     * @return 销售订单明细集合
     */
    List<SalesOrderItem> selectSalesOrderItemList(SalesOrderItem salesOrderItem);

    /**
     * 新增销售订单明细
     *
     * @param salesOrderItem 销售订单明细
     * @return 结果
     */
    int insertSalesOrderItem(SalesOrderItem salesOrderItem);

    /**
     * 修改销售订单明细
     *
     * @param salesOrderItem 销售订单明细
     * @return 结果
     */
    int updateSalesOrderItem(SalesOrderItem salesOrderItem);

    /**
     * 批量删除销售订单明细
     *
     * @param ids 需要删除的销售订单明细主键集合
     * @return 结果
     */
    int deleteSalesOrderItemByIds(Long[] ids);

    /**
     * 删除销售订单明细信息
     *
     * @param id 销售订单明细主键
     * @return 结果
     */
    int deleteSalesOrderItemById(Long id);

    /**
     * 新增销售订单明细批量
     *
     * @param list 销售订单明细
     * @return 结果
     */
    int insertSalesOrderItemBatch(List<SalesOrderItem> list);
}
