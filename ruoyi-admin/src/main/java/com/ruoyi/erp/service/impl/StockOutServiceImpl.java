package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.erp.mapper.StockOutMapper;
import com.ruoyi.erp.domain.StockOut;
import com.ruoyi.erp.service.IStockOutService;
import com.ruoyi.erp.service.IErpPushDownService;
import com.ruoyi.erp.service.IErpInventoryService;
import com.ruoyi.erp.utils.BillNoGenerator;

/**
 * 出库单Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@Service
public class StockOutServiceImpl implements IStockOutService {
    @Autowired
    private StockOutMapper stockOutMapper;

    @Autowired
    private BillNoGenerator billNoGenerator;

    @Autowired
    private IErpPushDownService erpPushDownService;

    @Autowired
    private IErpInventoryService erpInventoryService;

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
        if (stockOut.getSn() == null || stockOut.getSn().isEmpty()) {
            stockOut.setSn(billNoGenerator.generate("SOUT"));
        }
        stockOut.setCreateBy(SecurityUtils.getUserId().toString());
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

    /**
     * 确认出库单
     *
     * @param stockOut 出库单
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confirm(StockOut stockOut) {
        // 1. 更新确认状态
        stockOut.setConfirmStatus("1");
        stockOut.setConfirmBy(SecurityUtils.getUsername());
        stockOut.setConfirmTime(DateUtils.getNowDate());
        int result = stockOutMapper.updateStockOut(stockOut);
        if (result <= 0) {
            return false;
        }

        // 2. 如果来源上游单据，更新上游已执行数量
        boolean pushDownResult = erpPushDownService.updateSalesOrderExecuteQty(stockOut);
        if (!pushDownResult) {
            throw new RuntimeException("更新销售订单已执行数量失败");
        }

        // 3. 扣减库存
        boolean inventoryResult = erpInventoryService.decreaseStockByStockOut(stockOut);
        if (!inventoryResult) {
            throw new RuntimeException("扣减库存失败");
        }

        return true;
    }

    /**
     * 取消确认出库单
     *
     * @param stockOut 出库单
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelConfirm(StockOut stockOut) {
        // 1. 更新确认状态
        stockOut.setConfirmStatus("0");
        stockOut.setConfirmBy(null);
        stockOut.setConfirmTime(null);
        int result = stockOutMapper.updateStockOut(stockOut);
        if (result <= 0) {
            return false;
        }

        // 2. 如果来源上游单据，回滚上游已执行数量
        boolean rollbackResult = erpPushDownService.rollbackSalesOrderExecuteQty(stockOut);
        if (!rollbackResult) {
            throw new RuntimeException("回滚销售订单已执行数量失败");
        }

        // 3. 回滚库存
        boolean inventoryResult = erpInventoryService.rollbackDecreaseStockByStockOut(stockOut);
        if (!inventoryResult) {
            throw new RuntimeException("回滚库存失败");
        }

        return true;
    }
}
