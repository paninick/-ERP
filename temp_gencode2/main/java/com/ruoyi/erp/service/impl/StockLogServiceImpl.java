package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.StockLogMapper;
import com.ruoyi.erp.domain.StockLog;
import com.ruoyi.erp.service.IStockLogService;

/**
 * 出入库流水Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
@RequiredArgsConstructor
@Service
public class StockLogServiceImpl implements IStockLogService {
    private final StockLogMapper stockLogMapper;

    /**
     * 查询出入库流水
     *
     * @param id 出入库流水主键
     * @return 出入库流水
     */
    @Override
    public StockLog selectStockLogById(Long id) {
        return stockLogMapper.selectStockLogById(id);
    }

    /**
     * 查询出入库流水列表
     *
     * @param stockLog 出入库流水
     * @return 出入库流水
     */
    @Override
    public List<StockLog> selectStockLogList(StockLog stockLog) {
        return stockLogMapper.selectStockLogList(stockLog);
    }

    /**
     * 新增出入库流水
     *
     * @param stockLog 出入库流水
     * @return 结果
     */
    @Override
    public int insertStockLog(StockLog stockLog) {
        stockLog.setCreateBy(SecurityUtils.getUserIdStr());
        stockLog.setCreateTime(DateUtils.getNowDate());
        return stockLogMapper.insertStockLog(stockLog);
    }

    /**
     * 修改出入库流水
     *
     * @param stockLog 出入库流水
     * @return 结果
     */
    @Override
    public int updateStockLog(StockLog stockLog) {
        stockLog.setUpdateTime(DateUtils.getNowDate());
        return stockLogMapper.updateStockLog(stockLog);
    }

    /**
     * 批量删除出入库流水
     *
     * @param ids 需要删除的出入库流水主键
     * @return 结果
     */
    @Override
    public int deleteStockLogByIds(Long[] ids) {
        return stockLogMapper.deleteStockLogByIds(ids);
    }

    /**
     * 删除出入库流水信息
     *
     * @param id 出入库流水主键
     * @return 结果
     */
    @Override
    public int deleteStockLogById(Long id) {
        return stockLogMapper.deleteStockLogById(id);
    }

    /**
     * 批量新增出入库流水
     *
     * @param list 出入库流水
     * @return 结果
     */
    @Override
    public int insertStockLogBatch(List<StockLog> list) {
        return stockLogMapper.insertStockLogBatch(list);
    }
}
