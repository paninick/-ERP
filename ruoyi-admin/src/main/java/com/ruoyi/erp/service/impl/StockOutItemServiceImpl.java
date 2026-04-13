package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.StockOutItemMapper;
import com.ruoyi.erp.domain.StockOutItem;
import com.ruoyi.erp.service.IStockOutItemService;

/**
 * 出库明细Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@Service
public class StockOutItemServiceImpl implements IStockOutItemService {
    @Autowired
    private StockOutItemMapper stockOutItemMapper;

    /**
     * 查询出库明细
     *
     * @param id 出库明细主键
     * @return 出库明细
     */
    @Override
    public StockOutItem selectStockOutItemById(Long id) {
        return stockOutItemMapper.selectStockOutItemById(id);
    }

    /**
     * 查询出库明细列表
     *
     * @param stockOutItem 出库明细
     * @return 出库明细
     */
    @Override
    public List<StockOutItem> selectStockOutItemList(StockOutItem stockOutItem) {
        return stockOutItemMapper.selectStockOutItemList(stockOutItem);
    }

    /**
     * 新增出库明细
     *
     * @param stockOutItem 出库明细
     * @return 结果
     */
    @Override
    public int insertStockOutItem(StockOutItem stockOutItem) {
        stockOutItem.setCreateBy(SecurityUtils.getUserId().toString());
        stockOutItem.setCreateTime(DateUtils.getNowDate());
        return stockOutItemMapper.insertStockOutItem(stockOutItem);
    }

    /**
     * 修改出库明细
     *
     * @param stockOutItem 出库明细
     * @return 结果
     */
    @Override
    public int updateStockOutItem(StockOutItem stockOutItem) {
        stockOutItem.setUpdateTime(DateUtils.getNowDate());
        return stockOutItemMapper.updateStockOutItem(stockOutItem);
    }

    /**
     * 批量删除出库明细
     *
     * @param ids 需要删除的出库明细主键
     * @return 结果
     */
    @Override
    public int deleteStockOutItemByIds(Long[] ids) {
        return stockOutItemMapper.deleteStockOutItemByIds(ids);
    }

    /**
     * 删除出库明细信息
     *
     * @param id 出库明细主键
     * @return 结果
     */
    @Override
    public int deleteStockOutItemById(Long id) {
        return stockOutItemMapper.deleteStockOutItemById(id);
    }

    /**
     * 批量新增出库明细
     *
     * @param list 出库明细
     * @return 结果
     */
    @Override
    public int insertStockOutItemBatch(List<StockOutItem> list) {
        return stockOutItemMapper.insertStockOutItemBatch(list);
    }
}
