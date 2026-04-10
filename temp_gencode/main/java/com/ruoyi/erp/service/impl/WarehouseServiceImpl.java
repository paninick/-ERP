package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.WarehouseMapper;
import com.ruoyi.erp.domain.Warehouse;
import com.ruoyi.erp.service.IWarehouseService;

/**
 * 库区管理Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
@RequiredArgsConstructor
@Service
public class WarehouseServiceImpl implements IWarehouseService {
    private final WarehouseMapper warehouseMapper;

    /**
     * 查询库区管理
     *
     * @param id 库区管理主键
     * @return 库区管理
     */
    @Override
    public Warehouse selectWarehouseById(Long id) {
        return warehouseMapper.selectWarehouseById(id);
    }

    /**
     * 查询库区管理列表
     *
     * @param warehouse 库区管理
     * @return 库区管理
     */
    @Override
    public List<Warehouse> selectWarehouseList(Warehouse warehouse) {
        return warehouseMapper.selectWarehouseList(warehouse);
    }

    /**
     * 新增库区管理
     *
     * @param warehouse 库区管理
     * @return 结果
     */
    @Override
    public int insertWarehouse(Warehouse warehouse) {
        warehouse.setCreateBy(SecurityUtils.getUserIdStr());
        warehouse.setCreateTime(DateUtils.getNowDate());
        return warehouseMapper.insertWarehouse(warehouse);
    }

    /**
     * 修改库区管理
     *
     * @param warehouse 库区管理
     * @return 结果
     */
    @Override
    public int updateWarehouse(Warehouse warehouse) {
        warehouse.setUpdateTime(DateUtils.getNowDate());
        return warehouseMapper.updateWarehouse(warehouse);
    }

    /**
     * 批量删除库区管理
     *
     * @param ids 需要删除的库区管理主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseByIds(Long[] ids) {
        return warehouseMapper.deleteWarehouseByIds(ids);
    }

    /**
     * 删除库区管理信息
     *
     * @param id 库区管理主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseById(Long id) {
        return warehouseMapper.deleteWarehouseById(id);
    }

    /**
     * 批量新增库区管理
     *
     * @param list 库区管理
     * @return 结果
     */
    @Override
    public int insertWarehouseBatch(List<Warehouse> list) {
        return warehouseMapper.insertWarehouseBatch(list);
    }
}
