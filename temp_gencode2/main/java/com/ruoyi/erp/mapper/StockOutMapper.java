package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.StockOut;

/**
 * 出库单Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
public interface StockOutMapper {
    /**
     * 查询出库单
     *
     * @param id 出库单主键
     * @return 出库单
     */
    StockOut selectStockOutById(Long id);

    /**
     * 查询出库单列表
     *
     * @param stockOut 出库单
     * @return 出库单集合
     */
    List<StockOut> selectStockOutList(StockOut stockOut);

    /**
     * 新增出库单
     *
     * @param stockOut 出库单
     * @return 结果
     */
    int insertStockOut(StockOut stockOut);

    /**
     * 修改出库单
     *
     * @param stockOut 出库单
     * @return 结果
     */
    int updateStockOut(StockOut stockOut);

    /**
     * 删除出库单
     *
     * @param id 出库单主键
     * @return 结果
     */
    int deleteStockOutById(Long id);

    /**
     * 批量删除出库单
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteStockOutByIds(Long[] ids);

    /**
     * 批量新增出库单批量
     *
     * @param list 出库单
     * @return 结果
     */
    int insertStockOutBatch(List<StockOut> list);
}
