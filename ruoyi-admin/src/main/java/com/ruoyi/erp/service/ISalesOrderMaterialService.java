package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.SalesOrderMaterial;

/**
 * 大货订单物料明细Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface ISalesOrderMaterialService {
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
     * 批量删除大货订单物料明细
     *
     * @param ids 需要删除的大货订单物料明细主键集合
     * @return 结果
     */
    int deleteSalesOrderMaterialByIds(Long[] ids);

    /**
     * 删除大货订单物料明细信息
     *
     * @param id 大货订单物料明细主键
     * @return 结果
     */
    int deleteSalesOrderMaterialById(Long id);

    /**
     * 新增大货订单物料明细批量
     *
     * @param list 大货订单物料明细
     * @return 结果
     */
    int insertSalesOrderMaterialBatch(List<SalesOrderMaterial> list);
}
