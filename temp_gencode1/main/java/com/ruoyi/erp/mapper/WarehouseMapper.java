package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.Warehouse;

/**
 * 库区管理Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
public interface WarehouseMapper {
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
     * 删除库区管理
     *
     * @param id 库区管理主键
     * @return 结果
     */
    int deleteWarehouseById(Long id);

    /**
     * 批量删除库区管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteWarehouseByIds(Long[] ids);

    /**
     * 批量新增库区管理批量
     *
     * @param list 库区管理
     * @return 结果
     */
    int insertWarehouseBatch(List<Warehouse> list);
}
