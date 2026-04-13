package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.SalesOrderPack;

/**
 * 大货订单包装纸箱明细Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface ISalesOrderPackService {
    /**
     * 查询大货订单包装纸箱明细
     *
     * @param id 大货订单包装纸箱明细主键
     * @return 大货订单包装纸箱明细
     */
    SalesOrderPack selectSalesOrderPackById(Long id);

    /**
     * 查询大货订单包装纸箱明细列表
     *
     * @param salesOrderPack 大货订单包装纸箱明细
     * @return 大货订单包装纸箱明细集合
     */
    List<SalesOrderPack> selectSalesOrderPackList(SalesOrderPack salesOrderPack);

    /**
     * 新增大货订单包装纸箱明细
     *
     * @param salesOrderPack 大货订单包装纸箱明细
     * @return 结果
     */
    int insertSalesOrderPack(SalesOrderPack salesOrderPack);

    /**
     * 修改大货订单包装纸箱明细
     *
     * @param salesOrderPack 大货订单包装纸箱明细
     * @return 结果
     */
    int updateSalesOrderPack(SalesOrderPack salesOrderPack);

    /**
     * 批量删除大货订单包装纸箱明细
     *
     * @param ids 需要删除的大货订单包装纸箱明细主键集合
     * @return 结果
     */
    int deleteSalesOrderPackByIds(Long[] ids);

    /**
     * 删除大货订单包装纸箱明细信息
     *
     * @param id 大货订单包装纸箱明细主键
     * @return 结果
     */
    int deleteSalesOrderPackById(Long id);

    /**
     * 新增大货订单包装纸箱明细批量
     *
     * @param list 大货订单包装纸箱明细
     * @return 结果
     */
    int insertSalesOrderPackBatch(List<SalesOrderPack> list);
}
