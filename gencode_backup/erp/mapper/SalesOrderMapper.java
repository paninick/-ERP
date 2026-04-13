package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.SalesOrder;

/**
 * 销售订单Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface SalesOrderMapper {
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
     * 删除销售订单
     *
     * @param id 销售订单主键
     * @return 结果
     */
    int deleteSalesOrderById(Long id);

    /**
     * 批量删除销售订单
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteSalesOrderByIds(Long[] ids);

    /**
     * 批量新增销售订单批量
     *
     * @param list 销售订单
     * @return 结果
     */
    int insertSalesOrderBatch(List<SalesOrder> list);
}
