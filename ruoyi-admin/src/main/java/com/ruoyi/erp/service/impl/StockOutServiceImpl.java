package com.ruoyi.erp.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.erp.domain.StockOut;
import com.ruoyi.erp.mapper.StockOutMapper;
import com.ruoyi.erp.service.IErpInventoryService;
import com.ruoyi.erp.service.IErpPushDownService;
import com.ruoyi.erp.service.IProduceMaterialConsumeService;
import com.ruoyi.erp.service.IStockOutService;
import com.ruoyi.erp.utils.BillNoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 出库单 service.
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

    @Autowired
    private IProduceMaterialConsumeService produceMaterialConsumeService;

    @Override
    public StockOut selectStockOutById(Long id) {
        return stockOutMapper.selectStockOutById(id);
    }

    @Override
    public List<StockOut> selectStockOutList(StockOut stockOut) {
        return stockOutMapper.selectStockOutList(stockOut);
    }

    @Override
    public int insertStockOut(StockOut stockOut) {
        if (stockOut.getSn() == null || stockOut.getSn().isEmpty()) {
            stockOut.setSn(billNoGenerator.generate("SOUT"));
        }
        stockOut.setCreateBy(SecurityUtils.getUserId().toString());
        stockOut.setCreateTime(DateUtils.getNowDate());
        return stockOutMapper.insertStockOut(stockOut);
    }

    @Override
    public int updateStockOut(StockOut stockOut) {
        stockOut.setUpdateTime(DateUtils.getNowDate());
        return stockOutMapper.updateStockOut(stockOut);
    }

    @Override
    public int deleteStockOutByIds(Long[] ids) {
        return stockOutMapper.deleteStockOutByIds(ids);
    }

    @Override
    public int deleteStockOutById(Long id) {
        return stockOutMapper.deleteStockOutById(id);
    }

    @Override
    public int insertStockOutBatch(List<StockOut> list) {
        return stockOutMapper.insertStockOutBatch(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confirm(StockOut stockOut) {
        if (stockOut == null || stockOut.getId() == null) {
            throw new ServiceException("出库单不存在，无法确认");
        }
        if (produceMaterialConsumeService.hasLockedAbnormalByStockOut(stockOut.getId())) {
            throw new ServiceException("该出库单关联的用料记录存在锁定异常，禁止确认出库，请先处理异常池记录");
        }

        stockOut.setConfirmStatus("1");
        stockOut.setConfirmBy(SecurityUtils.getUsername());
        stockOut.setConfirmTime(DateUtils.getNowDate());
        int result = stockOutMapper.updateStockOut(stockOut);
        if (result <= 0) {
            return false;
        }

        boolean pushDownResult = erpPushDownService.updateSalesOrderExecuteQty(stockOut);
        if (!pushDownResult) {
            throw new RuntimeException("更新上游执行数量失败");
        }

        boolean inventoryResult = erpInventoryService.decreaseStockByStockOut(stockOut);
        if (!inventoryResult) {
            throw new RuntimeException("扣减库存失败");
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelConfirm(StockOut stockOut) {
        stockOut.setConfirmStatus("0");
        stockOut.setConfirmBy(null);
        stockOut.setConfirmTime(null);
        int result = stockOutMapper.updateStockOut(stockOut);
        if (result <= 0) {
            return false;
        }

        boolean rollbackResult = erpPushDownService.rollbackSalesOrderExecuteQty(stockOut);
        if (!rollbackResult) {
            throw new RuntimeException("回滚上游执行数量失败");
        }

        boolean inventoryResult = erpInventoryService.rollbackDecreaseStockByStockOut(stockOut);
        if (!inventoryResult) {
            throw new RuntimeException("回滚库存失败");
        }

        return true;
    }
}
