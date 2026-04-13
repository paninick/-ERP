package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.WarehouseArea;

/**
 * 仓库管理Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface WarehouseAreaMapper {
    /**
     * 查询仓库管理
     *
     * @param id 仓库管理主键
     * @return 仓库管理
     */
    WarehouseArea selectWarehouseAreaById(Long id);

    /**
     * 查询仓库管理列表
     *
     * @param warehouseArea 仓库管理
     * @return 仓库管理集合
     */
    List<WarehouseArea> selectWarehouseAreaList(WarehouseArea warehouseArea);

    /**
     * 新增仓库管理
     *
     * @param warehouseArea 仓库管理
     * @return 结果
     */
    int insertWarehouseArea(WarehouseArea warehouseArea);

    /**
     * 修改仓库管理
     *
     * @param warehouseArea 仓库管理
     * @return 结果
     */
    int updateWarehouseArea(WarehouseArea warehouseArea);

    /**
     * 删除仓库管理
     *
     * @param id 仓库管理主键
     * @return 结果
     */
    int deleteWarehouseAreaById(Long id);

    /**
     * 批量删除仓库管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteWarehouseAreaByIds(Long[] ids);

    /**
     * 批量新增仓库管理批量
     *
     * @param list 仓库管理
     * @return 结果
     */
    int insertWarehouseAreaBatch(List<WarehouseArea> list);
}
