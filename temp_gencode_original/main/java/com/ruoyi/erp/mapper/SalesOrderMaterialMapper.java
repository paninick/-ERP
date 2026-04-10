package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.SalesOrderMaterial;

/**
 * 大货订单物料明细Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
public interface SalesOrderMaterialMapper {
    /**
     * 查询大货订单物料明细
     *
     * @param id 大货订单物料明细主键
     * @return 大货订单物料明细
     */
    SalesOrderMaterial selectSalesOrderMaterialById(Long id);

    /**
     * 查询大货订单物料明细列表
     *
     * @param salesOrderMaterial 大货订单物料明细
     * @return 大货订单物料明细集合
     */
    List<SalesOrderMaterial> selectSalesOrderMaterialList(SalesOrderMaterial salesOrderMaterial);

    /**
     * 新增大货订单物料明细
     *
     * @param salesOrderMaterial 大货订单物料明细
     * @return 结果
     */
    int insertSalesOrderMaterial(SalesOrderMaterial salesOrderMaterial);

    /**
     * 修改大货订单物料明细
     *
     * @param salesOrderMaterial 大货订单物料明细
     * @return 结果
     */
    int updateSalesOrderMaterial(SalesOrderMaterial salesOrderMaterial);

    /**
     * 删除大货订单物料明细
     *
     * @param id 大货订单物料明细主键
     * @return 结果
     */
    int deleteSalesOrderMaterialById(Long id);

    /**
     * 批量删除大货订单物料明细
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteSalesOrderMaterialByIds(Long[] ids);

    /**
     * 批量新增大货订单物料明细批量
     *
     * @param list 大货订单物料明细
     * @return 结果
     */
    int insertSalesOrderMaterialBatch(List<SalesOrderMaterial> list);
}
