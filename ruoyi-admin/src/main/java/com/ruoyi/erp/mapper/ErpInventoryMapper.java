package com.ruoyi.erp.mapper;

import com.ruoyi.erp.domain.ErpInventory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 库存总表Mapper接口
 *
 * @author zhangmingjian
 */
@Mapper
public interface ErpInventoryMapper {
    /**
     * 查询库存总表 by ID
     *
     * @param id 库存ID
     * @return 库存信息
     */
    public ErpInventory selectErpInventoryById(Long id);

    /**
     * 根据仓库ID和SKU ID查询
     *
     * @param warehouseId 仓库ID
     * @param skuId       SKU ID
     * @return 库存信息
     */
    public ErpInventory selectByWarehouseIdAndSkuId(@Param("warehouseId") Long warehouseId,
                                                @Param("skuId") Long skuId);

    /**
     * 查询库存总表列表
     *
     * @param erpInventory 查询条件
     * @return 库存集合
     */
    public List<ErpInventory> selectErpInventoryList(ErpInventory erpInventory);

    /**
     * 新增库存总表
     *
     * @param erpInventory 库存信息
     * @return 结果
     */
    public int insertErpInventory(ErpInventory erpInventory);

    /**
     * 修改库存总表（带乐观锁检查）
     *
     * @param erpInventory 库存信息
     * @return 结果
     */
    public int updateErpInventory(ErpInventory erpInventory);

    /**
     * 增加库存（原子操作，带乐观锁）
     *
     * @param id          ID
     * @param qty         增加数量
     * @param oldVersion  当前版本
     * @return 影响行数
     */
    public int increaseStock(@Param("id") Long id, @Param("qty") BigDecimal qty, @Param("oldVersion") Integer oldVersion);

    /**
     * 扣减库存（原子操作，带乐观锁和负库存检查）
     *
     * @param id          ID
     * @param qty         扣减数量
     * @param oldVersion  当前版本
     * @return 影响行数
     */
    public int decreaseStock(@Param("id") Long id, @Param("qty") BigDecimal qty, @Param("oldVersion") Integer oldVersion);

    /**
     * 删除库存总表 by ID
     *
     * @param id ID
     * @return 结果
     */
    public int deleteErpInventoryById(Long id);

    /**
     * 批量删除库存总表
     *
     * @param ids 需要删除的ID数组
     * @return 结果
     */
    public int deleteErpInventoryByIds(Long[] ids);
}
