package com.ruoyi.erp.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.erp.domain.ErpInventory;
import com.ruoyi.erp.domain.StockIn;
import com.ruoyi.erp.domain.StockOut;
import com.ruoyi.erp.mapper.ErpInventoryMapper;
import com.ruoyi.erp.service.IErpInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 库存服务实现（核心：乐观锁+禁止负库存）
 *
 * @author zhangmingjian
 * @date 2026-04-20
 */
@Service
public class ErpInventoryServiceImpl implements IErpInventoryService {

    private static final int MAX_RETRY_TIMES = 3;

    @Autowired
    private ErpInventoryMapper erpInventoryMapper;

    @Override
    public ErpInventory selectErpInventoryById(Long id) {
        return erpInventoryMapper.selectErpInventoryById(id);
    }

    @Override
    public ErpInventory selectByWarehouseIdAndSkuId(Long warehouseId, Long skuId) {
        return erpInventoryMapper.selectByWarehouseIdAndSkuId(warehouseId, skuId);
    }

    @Override
    public List<ErpInventory> selectErpInventoryList(ErpInventory erpInventory) {
        return erpInventoryMapper.selectErpInventoryList(erpInventory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertErpInventory(ErpInventory erpInventory) {
        erpInventory.setVersion(0);
        erpInventory.setCreateTime(DateUtils.getNowDate());
        return erpInventoryMapper.insertErpInventory(erpInventory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateErpInventory(ErpInventory erpInventory) {
        erpInventory.setUpdateTime(DateUtils.getNowDate());
        return erpInventoryMapper.updateErpInventory(erpInventory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteErpInventoryById(Long id) {
        return erpInventoryMapper.deleteErpInventoryById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteErpInventoryByIds(Long[] ids) {
        return erpInventoryMapper.deleteErpInventoryByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean increaseStock(Long warehouseId, Long skuId, Long materialId, BigDecimal qty) {
        // 查询现有库存
        ErpInventory inv = selectByWarehouseIdAndSkuId(warehouseId, skuId);
        if (inv == null) {
            // 不存在，插入新记录
            ErpInventory newInv = new ErpInventory();
            newInv.setWarehouseId(warehouseId);
            newInv.setSkuId(skuId);
            newInv.setMaterialId(materialId);
            newInv.setInvQty(qty);
            newInv.setLockQty(BigDecimal.ZERO);
            insertErpInventory(newInv);
            return true;
        }

        // 存在，增加库存，使用乐观锁重试
        int retry = 0;
        while (retry < MAX_RETRY_TIMES) {
            int affected = erpInventoryMapper.increaseStock(inv.getId(), qty, inv.getVersion());
            if (affected > 0) {
                return true;
            }
            // 乐观锁失败，重新查询再重试
            inv = selectByWarehouseIdAndSkuId(warehouseId, skuId);
            retry++;
        }
        // 重试次数用完仍失败，返回false
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean decreaseStock(Long warehouseId, Long skuId, BigDecimal qty) {
        // 查询现有库存
        ErpInventory inv = selectByWarehouseIdAndSkuId(warehouseId, skuId);
        if (inv == null) {
            // 库存记录不存在，无法扣减
            return false;
        }

        // 前置检查：库存是否足够
        BigDecimal available = inv.getInvQty().subtract(inv.getLockQty());
        if (available.compareTo(qty) < 0) {
            // 库存不足
            return false;
        }

        // 扣减库存，使用乐观锁重试
        int retry = 0;
        while (retry < MAX_RETRY_TIMES) {
            int affected = erpInventoryMapper.decreaseStock(inv.getId(), qty, inv.getVersion());
            if (affected > 0) {
                return true;
            }
            // 乐观锁失败或库存检查不通过，重新查询再重试
            inv = selectByWarehouseIdAndSkuId(warehouseId, skuId);
            if (inv == null) {
                return false;
            }
            available = inv.getInvQty().subtract(inv.getLockQty());
            if (available.compareTo(qty) < 0) {
                return false;
            }
            retry++;
        }
        // 重试次数用完仍失败，返回false
        return false;
    }

    @Override
    public boolean increaseStockByStockIn(StockIn stockIn) {
        // 需要遍历入库单明细，逐个增加库存
        // 框架占位，具体整合到StockInServiceImpl时完成
        // 这里保证服务接口结构正确，后续整合
        return true;
    }

    @Override
    public boolean decreaseStockByStockOut(StockOut stockOut) {
        // 需要遍历出库单明细，逐个扣减库存
        // 框架占位，具体整合到StockOutServiceImpl时完成
        return true;
    }

    @Override
    public boolean rollbackIncreaseStockByStockIn(StockIn stockIn) {
        // 反向回滚，遍历入库单明细，逐个扣减库存
        // 框架占位
        return true;
    }

    @Override
    public boolean rollbackDecreaseStockByStockOut(StockOut stockOut) {
        // 反向回滚，遍历出库单明细，逐个增加库存
        // 框架占位
        return true;
    }

    @Override
    public boolean checkStockAvailable(Long warehouseId, Long skuId, BigDecimal qty) {
        ErpInventory inv = selectByWarehouseIdAndSkuId(warehouseId, skuId);
        if (inv == null) {
            return false;
        }
        BigDecimal available = inv.getInvQty().subtract(inv.getLockQty());
        return available.compareTo(qty) >= 0;
    }
}
