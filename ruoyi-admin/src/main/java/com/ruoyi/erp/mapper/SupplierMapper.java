package com.ruoyi.erp.mapper;

import java.util.List;
import java.util.Map;
import com.ruoyi.erp.domain.Supplier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 供应商Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface SupplierMapper {
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
     * 删除供应商
     *
     * @param id 供应商主键
     * @return 结果
     */
    int deleteSupplierById(Long id);

    /**
     * 批量删除供应商
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteSupplierByIds(Long[] ids);

    /**
     * 批量新增供应商批量
     *
     * @param list 供应商
     * @return 结果
     */
    int insertSupplierBatch(List<Supplier> list);

    /**
     * 根据供应商编号查询供应商
     *
     * @param supplierNo 供应商编号
     * @return 供应商信息
     */
    Supplier selectSupplierByNo(String supplierNo);

    /**
     * 调用存储过程自动计算指定供应商的评级
     */
    @Select("CALL sp_erp_rate_supplier(#{supplierId})")
    void rateSupplier(@Param("supplierId") Long supplierId);

    /**
     * 查询供应商评级概览视图（全部供应商）
     */
    @Select("SELECT * FROM v_erp_supplier_rating ORDER BY overall_rating DESC")
    List<Map<String, Object>> selectSupplierRatingView();
}
