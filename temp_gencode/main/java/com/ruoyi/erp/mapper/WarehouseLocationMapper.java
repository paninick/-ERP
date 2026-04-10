package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.WarehouseLocation;

/**
 * 仓位管理Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
public interface WarehouseLocationMapper {
    /**
     * 查询仓位管理
     *
     * @param id 仓位管理主键
     * @return 仓位管理
     */
    WarehouseLocation selectWarehouseLocationById(Long id);

    /**
     * 查询仓位管理列表
     *
     * @param warehouseLocation 仓位管理
     * @return 仓位管理集合
     */
    List<WarehouseLocation> selectWarehouseLocationList(WarehouseLocation warehouseLocation);

    /**
     * 新增仓位管理
     *
     * @param warehouseLocation 仓位管理
     * @return 结果
     */
    int insertWarehouseLocation(WarehouseLocation warehouseLocation);

    /**
     * 修改仓位管理
     *
     * @param warehouseLocation 仓位管理
     * @return 结果
     */
    int updateWarehouseLocation(WarehouseLocation warehouseLocation);

    /**
     * 删除仓位管理
     *
     * @param id 仓位管理主键
     * @return 结果
     */
    int deleteWarehouseLocationById(Long id);

    /**
     * 批量删除仓位管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteWarehouseLocationByIds(Long[] ids);

    /**
     * 批量新增仓位管理批量
     *
     * @param list 仓位管理
     * @return 结果
     */
    int insertWarehouseLocationBatch(List<WarehouseLocation> list);
}
