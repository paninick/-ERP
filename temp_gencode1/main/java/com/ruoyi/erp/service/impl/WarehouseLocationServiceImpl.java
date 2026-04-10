package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.WarehouseLocationMapper;
import com.ruoyi.erp.domain.WarehouseLocation;
import com.ruoyi.erp.service.IWarehouseLocationService;

/**
 * 仓位管理Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
@RequiredArgsConstructor
@Service
public class WarehouseLocationServiceImpl implements IWarehouseLocationService {
    private final WarehouseLocationMapper warehouseLocationMapper;

    /**
     * 查询仓位管理
     *
     * @param id 仓位管理主键
     * @return 仓位管理
     */
    @Override
    public WarehouseLocation selectWarehouseLocationById(Long id) {
        return warehouseLocationMapper.selectWarehouseLocationById(id);
    }

    /**
     * 查询仓位管理列表
     *
     * @param warehouseLocation 仓位管理
     * @return 仓位管理
     */
    @Override
    public List<WarehouseLocation> selectWarehouseLocationList(WarehouseLocation warehouseLocation) {
        return warehouseLocationMapper.selectWarehouseLocationList(warehouseLocation);
    }

    /**
     * 新增仓位管理
     *
     * @param warehouseLocation 仓位管理
     * @return 结果
     */
    @Override
    public int insertWarehouseLocation(WarehouseLocation warehouseLocation) {
        warehouseLocation.setCreateBy(SecurityUtils.getUserIdStr());
        warehouseLocation.setCreateTime(DateUtils.getNowDate());
        return warehouseLocationMapper.insertWarehouseLocation(warehouseLocation);
    }

    /**
     * 修改仓位管理
     *
     * @param warehouseLocation 仓位管理
     * @return 结果
     */
    @Override
    public int updateWarehouseLocation(WarehouseLocation warehouseLocation) {
        warehouseLocation.setUpdateTime(DateUtils.getNowDate());
        return warehouseLocationMapper.updateWarehouseLocation(warehouseLocation);
    }

    /**
     * 批量删除仓位管理
     *
     * @param ids 需要删除的仓位管理主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseLocationByIds(Long[] ids) {
        return warehouseLocationMapper.deleteWarehouseLocationByIds(ids);
    }

    /**
     * 删除仓位管理信息
     *
     * @param id 仓位管理主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseLocationById(Long id) {
        return warehouseLocationMapper.deleteWarehouseLocationById(id);
    }

    /**
     * 批量新增仓位管理
     *
     * @param list 仓位管理
     * @return 结果
     */
    @Override
    public int insertWarehouseLocationBatch(List<WarehouseLocation> list) {
        return warehouseLocationMapper.insertWarehouseLocationBatch(list);
    }
}
