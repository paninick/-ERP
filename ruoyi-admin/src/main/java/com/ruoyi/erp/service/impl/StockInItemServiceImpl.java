package com.ruoyi.erp.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.erp.domain.StockInItem;
import com.ruoyi.erp.mapper.StockInItemMapper;
import com.ruoyi.erp.service.IStockInItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Stock-in item service implementation.
 */
@Service
public class StockInItemServiceImpl implements IStockInItemService {
    @Autowired
    private StockInItemMapper stockInItemMapper;

    @Override
    public StockInItem selectStockInItemById(Long id) {
        return stockInItemMapper.selectStockInItemById(id);
    }

    @Override
    public List<StockInItem> selectStockInItemList(StockInItem stockInItem) {
        return stockInItemMapper.selectStockInItemList(stockInItem);
    }

    @Override
    public int insertStockInItem(StockInItem stockInItem) {
        stockInItem.setCreateBy(SecurityUtils.getUserId().toString());
        stockInItem.setCreateTime(DateUtils.getNowDate());
        return stockInItemMapper.insertStockInItem(stockInItem);
    }

    @Override
    public int updateStockInItem(StockInItem stockInItem) {
        stockInItem.setUpdateTime(DateUtils.getNowDate());
        return stockInItemMapper.updateStockInItem(stockInItem);
    }

    @Override
    public int deleteStockInItemByIds(Long[] ids) {
        return stockInItemMapper.deleteStockInItemByIds(ids);
    }

    @Override
    public int deleteStockInItemById(Long id) {
        return stockInItemMapper.deleteStockInItemById(id);
    }

    @Override
    public int insertStockInItemBatch(List<StockInItem> list) {
        return stockInItemMapper.insertStockInItemBatch(list);
    }

    @Override
    public List<StockInItem> selectStockInItemByInId(Long inId) {
        return stockInItemMapper.selectStockInItemByInId(inId);
    }
}
