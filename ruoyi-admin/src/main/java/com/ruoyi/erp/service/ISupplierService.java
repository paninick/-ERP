package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.Supplier;

/**
 * 供应商Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface ISupplierService {
    /**
     * 查询供应商
     *
     * @param id 供应商主键
     * @return 供应商
     */
    Supplier selectSupplierById(Long id);

    /**
     * 查询供应商列表
     *
     * @param supplier 供应商
     * @return 供应商集合
     */
    List<Supplier> selectSupplierList(Supplier supplier);

    /**
     * 新增供应商
     *
     * @param supplier 供应商
     * @return 结果
     */
    int insertSupplier(Supplier supplier);

    /**
     * 修改供应商
     *
     * @param supplier 供应商
     * @return 结果
     */
    int updateSupplier(Supplier supplier);

    /**
     * 批量删除供应商
     *
     * @param ids 需要删除的供应商主键集合
     * @return 结果
     */
    int deleteSupplierByIds(Long[] ids);

    /**
     * 删除供应商信息
     *
     * @param id 供应商主键
     * @return 结果
     */
    int deleteSupplierById(Long id);

    /**
     * 新增供应商批量
     *
     * @param list 供应商
     * @return 结果
     */
    int insertSupplierBatch(List<Supplier> list);

    /**
     * 导入供应商数据
     *
     * @param list 供应商数据列表
     * @param updateSupport 是否更新支持，如果已存在则更新
     * @return 结果
     */
    String importSupplier(List<Supplier> list, boolean updateSupport);
}
