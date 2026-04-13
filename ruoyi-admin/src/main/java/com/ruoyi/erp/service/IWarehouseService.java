package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.Warehouse;

/**
 * 库区管理Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface IWarehouseService {
    /**
     * 查询库区管理
     *
     * @param id 库区管理主键
     * @return 库区管理
     */
    Warehouse selectWarehouseById(Long id);

    /**
     * 查询库区管理列表
     *
     * @param warehouse 库区管理
     * @return 库区管理集合
     */
    List<Warehouse> selectWarehouseList(Warehouse warehouse);

    /**
     * 新增库区管理
     *
     * @param warehouse 库区管理
     * @return 结果
     */
    int insertWarehouse(Warehouse warehouse);

    /**
     * 修改库区管理
     *
     * @param warehouse 库区管理
     * @return 结果
     */
    int updateWarehouse(Warehouse warehouse);

    /**
     * 批量删除库区管理
     *
     * @param ids 需要删除的库区管理主键集合
     * @return 结果
     */
    int deleteWarehouseByIds(Long[] ids);

    /**
     * 删除库区管理信息
     *
     * @param id 库区管理主键
     * @return 结果
     */
    int deleteWarehouseById(Long id);

    /**
     * 新增库区管理批量
     *
     * @param list 库区管理
     * @return 结果
     */
    int insertWarehouseBatch(List<Warehouse> list);

    /**
     * 导入库区数据
     *
     * @param list 库区数据列表
     * @param updateSupport 是否更新支持，如果已存在则更新
     * @return 结果
     */
    String importWarehouse(List<Warehouse> list, boolean updateSupport);

    /**
     * 快速批量导入库区数据
     *
     * @param list 库区数据列表
     * @return 结果
     */
    String importWarehouseFast(List<Warehouse> list);

    /**
     * 安全插入库区
     *
     * @param warehouse 库区
     * @return 结果
     */
    int insertWarehouseSafe(Warehouse warehouse);

    /**
     * 批量安全插入库区
     *
     * @param list 库区列表
     * @return 结果
     */
    int insertWarehouseBatchSafe(List<Warehouse> list);
}
