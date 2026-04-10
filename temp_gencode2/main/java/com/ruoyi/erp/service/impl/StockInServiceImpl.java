package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.StockInMapper;
import com.ruoyi.erp.domain.StockIn;
import com.ruoyi.erp.service.IStockInService;

/**
 * 入库单Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
@RequiredArgsConstructor
@Service
public class StockInServiceImpl implements IStockInService {
    private final StockInMapper stockInMapper;

    /**
     * 查询入库单
     *
     * @param id 入库单主键
     * @return 入库单
     */
    @Override
    public StockIn selectStockInById(Long id) {
        return stockInMapper.selectStockInById(id);
    }

    /**
     * 查询入库单列表
     *
     * @param stockIn 入库单
     * @return 入库单
     */
    @Override
    public List<StockIn> selectStockInList(StockIn stockIn) {
        return stockInMapper.selectStockInList(stockIn);
    }

    /**
     * 新增入库单
     *
     * @param stockIn 入库单
     * @return 结果
     */
    @Override
    public int insertStockIn(StockIn stockIn) {
        stockIn.setCreateBy(SecurityUtils.getUserIdStr());
        stockIn.setCreateTime(DateUtils.getNowDate());
        return stockInMapper.insertStockIn(stockIn);
    }

    /**
     * 修改入库单
     *
     * @param stockIn 入库单
     * @return 结果
     */
    @Override
    public int updateStockIn(StockIn stockIn) {
        stockIn.setUpdateTime(DateUtils.getNowDate());
        return stockInMapper.updateStockIn(stockIn);
    }

    /**
     * 批量删除入库单
     *
     * @param ids 需要删除的入库单主键
     * @return 结果
     */
    @Override
    public int deleteStockInByIds(Long[] ids) {
        return stockInMapper.deleteStockInByIds(ids);
    }

    /**
     * 删除入库单信息
     *
     * @param id 入库单主键
     * @return 结果
     */
    @Override
    public int deleteStockInById(Long id) {
        return stockInMapper.deleteStockInById(id);
    }

    /**
     * 批量新增入库单
     *
     * @param list 入库单
     * @return 结果
     */
    @Override
    public int insertStockInBatch(List<StockIn> list) {
        return stockInMapper.insertStockInBatch(list);
    }
}
