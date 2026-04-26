package com.ruoyi.erp.service;

import com.ruoyi.erp.domain.StockOutItem;

import java.util.List;

/**
 * Stock-out item service.
 */
public interface IStockOutItemService {
    StockOutItem selectStockOutItemById(Long id);

    List<StockOutItem> selectStockOutItemList(StockOutItem stockOutItem);

    int insertStockOutItem(StockOutItem stockOutItem);

    int updateStockOutItem(StockOutItem stockOutItem);

    int deleteStockOutItemByIds(Long[] ids);

    int deleteStockOutItemById(Long id);

    int insertStockOutItemBatch(List<StockOutItem> list);

    List<StockOutItem> selectStockOutItemByOutId(Long outId);
}
