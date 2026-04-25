package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.StockOutItem;

/**
 * 出库明细Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface StockOutItemMapper {
    /**
     * 查询出库明细
     *
     * @param id 出库明细主键
     * @return 出库明细
     */
    StockOutItem selectStockOutItemById(Long id);

    /**
     * 查询出库明细列表
     *
     * @param stockOutItem 出库明细
     * @return 出库明细集合
     */
    List<StockOutItem> selectStockOutItemList(StockOutItem stockOutItem);

    /**
     * 新增出库明细
     *
     * @param stockOutItem 出库明细
     * @return 结果
     */
    int insertStockOutItem(StockOutItem stockOutItem);

    /**
     * 修改出库明细
     *
     * @param stockOutItem 出库明细
     * @return 结果
     */
    int updateStockOutItem(StockOutItem stockOutItem);

    /**
     * 删除出库明细
     *
     * @param id 出库明细主键
     * @return 结果
     */
    int deleteStockOutItemById(Long id);

    /**
     * 批量删除出库明细
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteStockOutItemByIds(Long[] ids);

    /**
     * 批量新增出库明细批量
     *
     * @param list 出库明细
     * @return 结果
     */
    int insertStockOutItemBatch(List<StockOutItem> list);

    List<StockOutItem> selectStockOutItemByOutId(Long outId);
}
