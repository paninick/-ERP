package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.SupplierMapper;
import com.ruoyi.erp.domain.Supplier;
import com.ruoyi.erp.service.ISupplierService;

/**
 * 供应商Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
@RequiredArgsConstructor
@Service
public class SupplierServiceImpl implements ISupplierService {
    private final SupplierMapper supplierMapper;

    /**
     * 查询供应商
     *
     * @param id 供应商主键
     * @return 供应商
     */
    @Override
    public Supplier selectSupplierById(Long id) {
        return supplierMapper.selectSupplierById(id);
    }

    /**
     * 查询供应商列表
     *
     * @param supplier 供应商
     * @return 供应商
     */
    @Override
    public List<Supplier> selectSupplierList(Supplier supplier) {
        return supplierMapper.selectSupplierList(supplier);
    }

    /**
     * 新增供应商
     *
     * @param supplier 供应商
     * @return 结果
     */
    @Override
    public int insertSupplier(Supplier supplier) {
        supplier.setCreateBy(SecurityUtils.getUserIdStr());
        supplier.setCreateTime(DateUtils.getNowDate());
        return supplierMapper.insertSupplier(supplier);
    }

    /**
     * 修改供应商
     *
     * @param supplier 供应商
     * @return 结果
     */
    @Override
    public int updateSupplier(Supplier supplier) {
        supplier.setUpdateTime(DateUtils.getNowDate());
        return supplierMapper.updateSupplier(supplier);
    }

    /**
     * 批量删除供应商
     *
     * @param ids 需要删除的供应商主键
     * @return 结果
     */
    @Override
    public int deleteSupplierByIds(Long[] ids) {
        return supplierMapper.deleteSupplierByIds(ids);
    }

    /**
     * 删除供应商信息
     *
     * @param id 供应商主键
     * @return 结果
     */
    @Override
    public int deleteSupplierById(Long id) {
        return supplierMapper.deleteSupplierById(id);
    }

    /**
     * 批量新增供应商
     *
     * @param list 供应商
     * @return 结果
     */
    @Override
    public int insertSupplierBatch(List<Supplier> list) {
        return supplierMapper.insertSupplierBatch(list);
    }
}
