package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.WarehouseArea;

/**
 * 仓库管理Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
public interface IWarehouseAreaService {
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
     * 批量删除仓库管理
     *
     * @param ids 需要删除的仓库管理主键集合
     * @return 结果
     */
    int deleteWarehouseAreaByIds(Long[] ids);

    /**
     * 删除仓库管理信息
     *
     * @param id 仓库管理主键
     * @return 结果
     */
    int deleteWarehouseAreaById(Long id);

    /**
     * 新增仓库管理批量
     *
     * @param list 仓库管理
     * @return 结果
     */
    int insertWarehouseAreaBatch(List<WarehouseArea> list);
}
