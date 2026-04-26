package com.ruoyi.erp.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.erp.domain.StockOutItem;
import com.ruoyi.erp.mapper.StockOutItemMapper;
import com.ruoyi.erp.service.IStockOutItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Stock-out item service implementation.
 */
@Service
public class StockOutItemServiceImpl implements IStockOutItemService {
    @Autowired
    private StockOutItemMapper stockOutItemMapper;

    @Override
    public StockOutItem selectStockOutItemById(Long id) {
        return stockOutItemMapper.selectStockOutItemById(id);
    }

    @Override
    public List<StockOutItem> selectStockOutItemList(StockOutItem stockOutItem) {
        return stockOutItemMapper.selectStockOutItemList(stockOutItem);
    }

    @Override
    public int insertStockOutItem(StockOutItem stockOutItem) {
        stockOutItem.setCreateBy(SecurityUtils.getUserId().toString());
        stockOutItem.setCreateTime(DateUtils.getNowDate());
        return stockOutItemMapper.insertStockOutItem(stockOutItem);
    }

    @Override
    public int updateStockOutItem(StockOutItem stockOutItem) {
        stockOutItem.setUpdateTime(DateUtils.getNowDate());
        return stockOutItemMapper.updateStockOutItem(stockOutItem);
    }

    @Override
    public int deleteStockOutItemByIds(Long[] ids) {
        return stockOutItemMapper.deleteStockOutItemByIds(ids);
    }

    @Override
    public int deleteStockOutItemById(Long id) {
        return stockOutItemMapper.deleteStockOutItemById(id);
    }

    @Override
    public int insertStockOutItemBatch(List<StockOutItem> list) {
        return stockOutItemMapper.insertStockOutItemBatch(list);
    }

    @Override
    public List<StockOutItem> selectStockOutItemByOutId(Long outId) {
        return stockOutItemMapper.selectStockOutItemByOutId(outId);
    }
}
