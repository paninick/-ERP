package com.ruoyi.erp.service.impl;

import com.ruoyi.erp.domain.*;
import com.ruoyi.erp.mapper.ProducePlanMapper;
import com.ruoyi.erp.mapper.PurchaseItemMapper;
import com.ruoyi.erp.mapper.SalesOrderItemMapper;
import com.ruoyi.erp.mapper.StockInItemMapper;
import com.ruoyi.erp.mapper.StockOutItemMapper;
import com.ruoyi.erp.service.IErpPushDownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 单据下推服务实现
 * 入库确认 → 采购明细 execute_qty 累加
 * 出库确认 → 销售订单明细 execute_qty 累加
 */
@Service
public class ErpPushDownServiceImpl implements IErpPushDownService {

    @Autowired
    private PurchaseItemMapper purchaseItemMapper;

    @Autowired
    private SalesOrderItemMapper salesOrderItemMapper;

    @Autowired
    private StockInItemMapper stockInItemMapper;

    @Autowired
    private StockOutItemMapper stockOutItemMapper;

    @Autowired
    private ProducePlanMapper producePlanMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePurchaseExecuteQty(StockIn stockIn) {
        if (stockIn.getPurchaseId() == null || stockIn.getId() == null) {
            return true;
        }
        List<StockInItem> inItems = stockInItemMapper.selectStockInItemByInId(stockIn.getId());
        if (inItems == null || inItems.isEmpty()) {
            return true;
        }
        List<PurchaseItem> purchaseItems = purchaseItemMapper.selectPurchaseItemByPurchaseId(stockIn.getPurchaseId());
        if (purchaseItems == null || purchaseItems.isEmpty()) {
            return true;
        }
        for (StockInItem inItem : inItems) {
            if (inItem.getMaterialId() == null || inItem.getCount() == null) {
                continue;
            }
            // 按 materialId + materialType 匹配采购明细，取第一条
            purchaseItems.stream()
                .filter(p -> inItem.getMaterialId().equals(p.getMaterialId())
                    && matchType(inItem.getMaterialType(), p.getMaterialType()))
                .findFirst()
                .ifPresent(p -> purchaseItemMapper.incrementExecuteQty(p.getId(), inItem.getCount()));
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSalesOrderExecuteQty(StockOut stockOut) {
        if (stockOut.getPlanId() == null || stockOut.getId() == null) {
            return true;
        }
        ProducePlan plan = producePlanMapper.selectProducePlanById(stockOut.getPlanId());
        if (plan == null || plan.getSalesOrderId() == null) {
            return true;
        }
        List<StockOutItem> outItems = stockOutItemMapper.selectStockOutItemByOutId(stockOut.getId());
        if (outItems == null || outItems.isEmpty()) {
            return true;
        }
        List<SalesOrderItem> salesItems = salesOrderItemMapper.selectSalesOrderItemBySalesOrderId(plan.getSalesOrderId());
        if (salesItems == null || salesItems.isEmpty()) {
            return true;
        }
        // 出库明细无 color/size，按总数量均摊到销售明细（按 planQuantity 比例）
        BigDecimal totalOut = outItems.stream()
            .map(o -> o.getCount() == null ? BigDecimal.ZERO : o.getCount())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalOut.compareTo(BigDecimal.ZERO) == 0) {
            return true;
        }
        BigDecimal totalPlan = salesItems.stream()
            .map(s -> s.getPlanQuantity() == null ? BigDecimal.ZERO : s.getPlanQuantity())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalPlan.compareTo(BigDecimal.ZERO) == 0) {
            // 无计划数量时平均分配
            BigDecimal avg = totalOut.divide(BigDecimal.valueOf(salesItems.size()), 0, java.math.RoundingMode.DOWN);
            for (SalesOrderItem item : salesItems) {
                salesOrderItemMapper.incrementExecuteQty(item.getId(), avg);
            }
        } else {
            for (SalesOrderItem item : salesItems) {
                BigDecimal planQty = item.getPlanQuantity() == null ? BigDecimal.ZERO : item.getPlanQuantity();
                BigDecimal delta = totalOut.multiply(planQty)
                    .divide(totalPlan, 0, java.math.RoundingMode.DOWN);
                if (delta.compareTo(BigDecimal.ZERO) > 0) {
                    salesOrderItemMapper.incrementExecuteQty(item.getId(), delta);
                }
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean rollbackPurchaseExecuteQty(StockIn stockIn) {
        if (stockIn.getPurchaseId() == null || stockIn.getId() == null) {
            return true;
        }
        List<StockInItem> inItems = stockInItemMapper.selectStockInItemByInId(stockIn.getId());
        if (inItems == null || inItems.isEmpty()) {
            return true;
        }
        List<PurchaseItem> purchaseItems = purchaseItemMapper.selectPurchaseItemByPurchaseId(stockIn.getPurchaseId());
        if (purchaseItems == null || purchaseItems.isEmpty()) {
            return true;
        }
        for (StockInItem inItem : inItems) {
            if (inItem.getMaterialId() == null || inItem.getCount() == null) {
                continue;
            }
            purchaseItems.stream()
                .filter(p -> inItem.getMaterialId().equals(p.getMaterialId())
                    && matchType(inItem.getMaterialType(), p.getMaterialType()))
                .findFirst()
                .ifPresent(p -> purchaseItemMapper.decrementExecuteQty(p.getId(), inItem.getCount()));
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean rollbackSalesOrderExecuteQty(StockOut stockOut) {
        if (stockOut.getPlanId() == null || stockOut.getId() == null) {
            return true;
        }
        ProducePlan plan = producePlanMapper.selectProducePlanById(stockOut.getPlanId());
        if (plan == null || plan.getSalesOrderId() == null) {
            return true;
        }
        List<StockOutItem> outItems = stockOutItemMapper.selectStockOutItemByOutId(stockOut.getId());
        if (outItems == null || outItems.isEmpty()) {
            return true;
        }
        List<SalesOrderItem> salesItems = salesOrderItemMapper.selectSalesOrderItemBySalesOrderId(plan.getSalesOrderId());
        if (salesItems == null || salesItems.isEmpty()) {
            return true;
        }
        BigDecimal totalOut = outItems.stream()
            .map(o -> o.getCount() == null ? BigDecimal.ZERO : o.getCount())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalOut.compareTo(BigDecimal.ZERO) == 0) {
            return true;
        }
        BigDecimal totalPlan = salesItems.stream()
            .map(s -> s.getPlanQuantity() == null ? BigDecimal.ZERO : s.getPlanQuantity())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalPlan.compareTo(BigDecimal.ZERO) == 0) {
            BigDecimal avg = totalOut.divide(BigDecimal.valueOf(salesItems.size()), 0, java.math.RoundingMode.DOWN);
            for (SalesOrderItem item : salesItems) {
                salesOrderItemMapper.decrementExecuteQty(item.getId(), avg);
            }
        } else {
            for (SalesOrderItem item : salesItems) {
                BigDecimal planQty = item.getPlanQuantity() == null ? BigDecimal.ZERO : item.getPlanQuantity();
                BigDecimal delta = totalOut.multiply(planQty)
                    .divide(totalPlan, 0, java.math.RoundingMode.DOWN);
                if (delta.compareTo(BigDecimal.ZERO) > 0) {
                    salesOrderItemMapper.decrementExecuteQty(item.getId(), delta);
                }
            }
        }
        return true;
    }

    private boolean matchType(String inType, String purchaseType) {
        if (inType == null && purchaseType == null) return true;
        if (inType == null || purchaseType == null) return false;
        return inType.equals(purchaseType);
    }
}
