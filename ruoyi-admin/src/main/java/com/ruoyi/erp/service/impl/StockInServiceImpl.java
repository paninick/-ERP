package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.erp.mapper.StockInMapper;
import com.ruoyi.erp.domain.StockIn;
import com.ruoyi.erp.service.IStockInService;
import com.ruoyi.erp.service.IErpPushDownService;
import com.ruoyi.erp.service.IErpInventoryService;
import com.ruoyi.erp.utils.BillNoGenerator;

/**
 * 入库单Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@Service
public class StockInServiceImpl implements IStockInService {
    @Autowired
    private StockInMapper stockInMapper;

    @Autowired
    private BillNoGenerator billNoGenerator;

    @Autowired
    private IErpPushDownService erpPushDownService;

    @Autowired
    private IErpInventoryService erpInventoryService;

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
        if (stockIn.getSn() == null || stockIn.getSn().isEmpty()) {
            stockIn.setSn(billNoGenerator.generate("SI"));
        }
        stockIn.setCreateBy(SecurityUtils.getUserId().toString());
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

    /**
     * 确认入库单
     *
     * @param stockIn 入库单
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confirm(StockIn stockIn) {
        // 1. 更新确认状态
        stockIn.setConfirmStatus("1");
        stockIn.setConfirmBy(SecurityUtils.getUsername());
        stockIn.setConfirmTime(DateUtils.getNowDate());
        int result = stockInMapper.updateStockIn(stockIn);
        if (result <= 0) {
            return false;
        }

        // 2. 如果来源上游单据，更新上游已执行数量
        boolean pushDownResult = erpPushDownService.updatePurchaseExecuteQty(stockIn);
        if (!pushDownResult) {
            throw new RuntimeException("更新采购订单已执行数量失败");
        }

        // 3. 增加库存
        boolean inventoryResult = erpInventoryService.increaseStockByStockIn(stockIn);
        if (!inventoryResult) {
            throw new RuntimeException("增加库存失败");
        }

        return true;
    }

    /**
     * 取消确认入库单
     *
     * @param stockIn 入库单
     * @return 是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelConfirm(StockIn stockIn) {
        // 1. 更新确认状态
        stockIn.setConfirmStatus("0");
        stockIn.setConfirmBy(null);
        stockIn.setConfirmTime(null);
        int result = stockInMapper.updateStockIn(stockIn);
        if (result <= 0) {
            return false;
        }

        // 2. 如果来源上游单据，回滚上游已执行数量
        boolean rollbackResult = erpPushDownService.rollbackPurchaseExecuteQty(stockIn);
        if (!rollbackResult) {
            throw new RuntimeException("回滚采购订单已执行数量失败");
        }

        // 3. 回滚库存
        boolean inventoryResult = erpInventoryService.rollbackIncreaseStockByStockIn(stockIn);
        if (!inventoryResult) {
            throw new RuntimeException("回滚库存失败");
        }

        return true;
    }

    @Override
    public String importStockIn(List<StockIn> list, boolean updateSupport) {
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("导入入库单数据不能为空");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (StockIn stockIn : list) {
            try {
                insertStockIn(stockIn);
                successNum++;
                successMsg.append("\n").append(successNum).append("、入库单 ")
                        .append(stockIn.getSn()).append(" 导入成功");
            } catch (Exception e) {
                failureNum++;
                failureMsg.append("\n").append(failureNum).append("、入库单导入失败：")
                        .append(e.getMessage());
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new RuntimeException(failureMsg.toString());
        }
        successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条。");
        return successMsg.toString();
    }
}
