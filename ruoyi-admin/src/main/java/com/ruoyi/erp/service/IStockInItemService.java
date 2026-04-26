package com.ruoyi.erp.service;

import com.ruoyi.erp.domain.StockInItem;

import java.util.List;

/**
 * Stock-in item service.
 */
public interface IStockInItemService {
    StockInItem selectStockInItemById(Long id);

    List<StockInItem> selectStockInItemList(StockInItem stockInItem);

    int insertStockInItem(StockInItem stockInItem);

    int updateStockInItem(StockInItem stockInItem);

    int deleteStockInItemByIds(Long[] ids);

    int deleteStockInItemById(Long id);

    int insertStockInItemBatch(List<StockInItem> list);

    List<StockInItem> selectStockInItemByInId(Long inId);
}
