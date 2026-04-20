package com.ruoyi.erp.service;

import com.ruoyi.erp.domain.ErpInventory;
import com.ruoyi.erp.domain.StockIn;
import com.ruoyi.erp.domain.StockOut;

import java.math.BigDecimal;
import java.util.List;

/**
 * 库存服务接口
 *
 * @author zhangmingjian
 * @date 2026-04-20
 */
public interface IErpInventoryService {
    /**
     * 查询库存
     *
     * @param id 库存ID
     * @return 库存信息
     */
    public ErpInventory selectErpInventoryById(Long id);

    /**
     * 根据仓库和SKU查询库存
     *
     * @param warehouseId 仓库ID
     * @param skuId       SKU ID
     * @return 库存信息
     */
    public ErpInventory selectByWarehouseIdAndSkuId(Long warehouseId, Long skuId);

    /**
     * 查询库存列表
     *
     * @param erpInventory 查询条件
     * @return 库存集合
     */
    public List<ErpInventory> selectErpInventoryList(ErpInventory erpInventory);

    /**
     * 新增库存
     *
     * @param erpInventory 库存信息
     * @return 结果
     */
    public int insertErpInventory(ErpInventory erpInventory);

    /**
     * 修改库存
     *
     * @param erpInventory 库存信息
     * @return 结果
     */
    public int updateErpInventory(ErpInventory erpInventory);

    /**
     * 批量删除库存
     *
     * @param ids 需要删除的库存ID
     * @return 结果
     */
    public int deleteErpInventoryByIds(Long[] ids);

    /**
     * 删除库存
     *
     * @param id 库存ID
     * @return 结果
     */
    public int deleteErpInventoryById(Long id);

    /**
     * 入库增加库存
     *
     * @param warehouseId 仓库ID
     * @param skuId       SKU ID
     * @param materialId   物料ID
     * @param qty         入库数量
     * @return 是否成功
     */
    public boolean increaseStock(Long warehouseId, Long skuId, Long materialId, BigDecimal qty);

    /**
     * 出库扣减库存
     *
     * @param warehouseId 仓库ID
     * @param skuId       SKU ID
     * @param qty         出库数量
     * @return 是否成功（库存不足返回false）
     */
    public boolean decreaseStock(Long warehouseId, Long skuId, BigDecimal qty);

    /**
     * 采购入库单确认，批量增加库存
     *
     * @param stockIn 入库单
     * @return 是否全部成功
     */
    public boolean increaseStockByStockIn(StockIn stockIn);

    /**
     * 销售出库单确认，批量扣减库存
     *
     * @param stockOut 出库单
     * @return 是否全部成功
     */
    public boolean decreaseStockByStockOut(StockOut stockOut);

    /**
     * 入库单取消确认，回滚库存增加
     *
     * @param stockIn 入库单
     * @return 是否全部成功
     */
    public boolean rollbackIncreaseStockByStockIn(StockIn stockIn);

    /**
     * 出库单取消确认，回滚库存扣减
     *
     * @param stockOut 出库单
     * @return 是否全部成功
     */
    public boolean rollbackDecreaseStockByStockOut(StockOut stockOut);

    /**
     * 检查库存是否充足
     *
     * @param warehouseId 仓库ID
     * @param skuId       SKU ID
     * @param qty         需要数量
     * @return 是否充足
     */
    public boolean checkStockAvailable(Long warehouseId, Long skuId, BigDecimal qty);
}
