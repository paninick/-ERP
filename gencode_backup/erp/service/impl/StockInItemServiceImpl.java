package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.StockInItemMapper;
import com.ruoyi.erp.domain.StockInItem;
import com.ruoyi.erp.service.IStockInItemService;

/**
 * 入库明细Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@Service
public class StockInItemServiceImpl implements IStockInItemService {
    @Autowired
    private StockInItemMapper stockInItemMapper;

    /**
     * 查询入库明细
     *
     * @param id 入库明细主键
     * @return 入库明细
     */
    @Override
    public StockInItem selectStockInItemById(Long id) {
        return stockInItemMapper.selectStockInItemById(id);
    }

    /**
     * 查询入库明细列表
     *
     * @param stockInItem 入库明细
     * @return 入库明细
     */
    @Override
    public List<StockInItem> selectStockInItemList(StockInItem stockInItem) {
        return stockInItemMapper.selectStockInItemList(stockInItem);
    }

    /**
     * 新增入库明细
     *
     * @param stockInItem 入库明细
     * @return 结果
     */
    @Override
    public int insertStockInItem(StockInItem stockInItem) {
        stockInItem.setCreateBy(SecurityUtils.getUserId().toString());
        stockInItem.setCreateTime(DateUtils.getNowDate());
        return stockInItemMapper.insertStockInItem(stockInItem);
    }

    /**
     * 修改入库明细
     *
     * @param stockInItem 入库明细
     * @return 结果
     */
    @Override
    public int updateStockInItem(StockInItem stockInItem) {
        stockInItem.setUpdateTime(DateUtils.getNowDate());
        return stockInItemMapper.updateStockInItem(stockInItem);
    }

    /**
     * 批量删除入库明细
     *
     * @param ids 需要删除的入库明细主键
     * @return 结果
     */
    @Override
    public int deleteStockInItemByIds(Long[] ids) {
        return stockInItemMapper.deleteStockInItemByIds(ids);
    }

    /**
     * 删除入库明细信息
     *
     * @param id 入库明细主键
     * @return 结果
     */
    @Override
    public int deleteStockInItemById(Long id) {
        return stockInItemMapper.deleteStockInItemById(id);
    }

    /**
     * 批量新增入库明细
     *
     * @param list 入库明细
     * @return 结果
     */
    @Override
    public int insertStockInItemBatch(List<StockInItem> list) {
        return stockInItemMapper.insertStockInItemBatch(list);
    }
}
