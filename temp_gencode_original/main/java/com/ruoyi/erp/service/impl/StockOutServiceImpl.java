package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.StockOutMapper;
import com.ruoyi.erp.domain.StockOut;
import com.ruoyi.erp.service.IStockOutService;

/**
 * 出库单Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
@RequiredArgsConstructor
@Service
public class StockOutServiceImpl implements IStockOutService {
    private final StockOutMapper stockOutMapper;

    /**
     * 查询出库单
     *
     * @param id 出库单主键
     * @return 出库单
     */
    @Override
    public StockOut selectStockOutById(Long id) {
        return stockOutMapper.selectStockOutById(id);
    }

    /**
     * 查询出库单列表
     *
     * @param stockOut 出库单
     * @return 出库单
     */
    @Override
    public List<StockOut> selectStockOutList(StockOut stockOut) {
        return stockOutMapper.selectStockOutList(stockOut);
    }

    /**
     * 新增出库单
     *
     * @param stockOut 出库单
     * @return 结果
     */
    @Override
    public int insertStockOut(StockOut stockOut) {
        stockOut.setCreateBy(SecurityUtils.getUserIdStr());
        stockOut.setCreateTime(DateUtils.getNowDate());
        return stockOutMapper.insertStockOut(stockOut);
    }

    /**
     * 修改出库单
     *
     * @param stockOut 出库单
     * @return 结果
     */
    @Override
    public int updateStockOut(StockOut stockOut) {
        stockOut.setUpdateTime(DateUtils.getNowDate());
        return stockOutMapper.updateStockOut(stockOut);
    }

    /**
     * 批量删除出库单
     *
     * @param ids 需要删除的出库单主键
     * @return 结果
     */
    @Override
    public int deleteStockOutByIds(Long[] ids) {
        return stockOutMapper.deleteStockOutByIds(ids);
    }

    /**
     * 删除出库单信息
     *
     * @param id 出库单主键
     * @return 结果
     */
    @Override
    public int deleteStockOutById(Long id) {
        return stockOutMapper.deleteStockOutById(id);
    }

    /**
     * 批量新增出库单
     *
     * @param list 出库单
     * @return 结果
     */
    @Override
    public int insertStockOutBatch(List<StockOut> list) {
        return stockOutMapper.insertStockOutBatch(list);
    }
}
