package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.SalesOrder;

/**
 * 销售订单Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
public interface ISalesOrderService {
    /**
     * 查询销售订单
     *
     * @param id 销售订单主键
     * @return 销售订单
     */
    SalesOrder selectSalesOrderById(Long id);

    /**
     * 查询销售订单列表
     *
     * @param salesOrder 销售订单
     * @return 销售订单集合
     */
    List<SalesOrder> selectSalesOrderList(SalesOrder salesOrder);

    /**
     * 新增销售订单
     *
     * @param salesOrder 销售订单
     * @return 结果
     */
    int insertSalesOrder(SalesOrder salesOrder);

    /**
     * 修改销售订单
     *
     * @param salesOrder 销售订单
     * @return 结果
     */
    int updateSalesOrder(SalesOrder salesOrder);

    /**
     * 批量删除销售订单
     *
     * @param ids 需要删除的销售订单主键集合
     * @return 结果
     */
    int deleteSalesOrderByIds(Long[] ids);

    /**
     * 删除销售订单信息
     *
     * @param id 销售订单主键
     * @return 结果
     */
    int deleteSalesOrderById(Long id);

    /**
     * 新增销售订单批量
     *
     * @param list 销售订单
     * @return 结果
     */
    int insertSalesOrderBatch(List<SalesOrder> list);
}
