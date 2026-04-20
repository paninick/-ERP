package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.SalesOrderItem;

/**
 * 销售订单明细Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface SalesOrderItemMapper {
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
     * 删除销售订单明细
     *
     * @param id 销售订单明细主键
     * @return 结果
     */
    int deleteSalesOrderItemById(Long id);

    /**
     * 批量删除销售订单明细
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteSalesOrderItemByIds(Long[] ids);

    /**
     * 批量新增销售订单明细批量
     *
     * @param list 销售订单明细
     * @return 结果
     */
    int insertSalesOrderItemBatch(List<SalesOrderItem> list);

    /**
     * 根据销售订单ID查询销售明细列表
     *
     * @param salesOrderId 销售订单ID
     * @return 销售明细集合
     */
    List<SalesOrderItem> selectSalesOrderItemBySalesOrderId(Long salesOrderId);
}
