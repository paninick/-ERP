package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.WarehouseAreaMapper;
import com.ruoyi.erp.domain.WarehouseArea;
import com.ruoyi.erp.service.IWarehouseAreaService;

/**
 * 仓库管理Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
@RequiredArgsConstructor
@Service
public class WarehouseAreaServiceImpl implements IWarehouseAreaService {
    private final WarehouseAreaMapper warehouseAreaMapper;

    /**
     * 查询仓库管理
     *
     * @param id 仓库管理主键
     * @return 仓库管理
     */
    @Override
    public WarehouseArea selectWarehouseAreaById(Long id) {
        return warehouseAreaMapper.selectWarehouseAreaById(id);
    }

    /**
     * 查询仓库管理列表
     *
     * @param warehouseArea 仓库管理
     * @return 仓库管理
     */
    @Override
    public List<WarehouseArea> selectWarehouseAreaList(WarehouseArea warehouseArea) {
        return warehouseAreaMapper.selectWarehouseAreaList(warehouseArea);
    }

    /**
     * 新增仓库管理
     *
     * @param warehouseArea 仓库管理
     * @return 结果
     */
    @Override
    public int insertWarehouseArea(WarehouseArea warehouseArea) {
        warehouseArea.setCreateBy(SecurityUtils.getUserIdStr());
        warehouseArea.setCreateTime(DateUtils.getNowDate());
        return warehouseAreaMapper.insertWarehouseArea(warehouseArea);
    }

    /**
     * 修改仓库管理
     *
     * @param warehouseArea 仓库管理
     * @return 结果
     */
    @Override
    public int updateWarehouseArea(WarehouseArea warehouseArea) {
        warehouseArea.setUpdateTime(DateUtils.getNowDate());
        return warehouseAreaMapper.updateWarehouseArea(warehouseArea);
    }

    /**
     * 批量删除仓库管理
     *
     * @param ids 需要删除的仓库管理主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseAreaByIds(Long[] ids) {
        return warehouseAreaMapper.deleteWarehouseAreaByIds(ids);
    }

    /**
     * 删除仓库管理信息
     *
     * @param id 仓库管理主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseAreaById(Long id) {
        return warehouseAreaMapper.deleteWarehouseAreaById(id);
    }

    /**
     * 批量新增仓库管理
     *
     * @param list 仓库管理
     * @return 结果
     */
    @Override
    public int insertWarehouseAreaBatch(List<WarehouseArea> list) {
        return warehouseAreaMapper.insertWarehouseAreaBatch(list);
    }
}
