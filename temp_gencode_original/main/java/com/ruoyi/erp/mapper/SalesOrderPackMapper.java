package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.SalesOrderPack;

/**
 * 大货订单包装纸箱明细Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
public interface SalesOrderPackMapper {
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
     * 删除大货订单包装纸箱明细
     *
     * @param id 大货订单包装纸箱明细主键
     * @return 结果
     */
    int deleteSalesOrderPackById(Long id);

    /**
     * 批量删除大货订单包装纸箱明细
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteSalesOrderPackByIds(Long[] ids);

    /**
     * 批量新增大货订单包装纸箱明细批量
     *
     * @param list 大货订单包装纸箱明细
     * @return 结果
     */
    int insertSalesOrderPackBatch(List<SalesOrderPack> list);
}
