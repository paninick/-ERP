package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.SalesOrderMaterialMapper;
import com.ruoyi.erp.domain.SalesOrderMaterial;
import com.ruoyi.erp.service.ISalesOrderMaterialService;

/**
 * 大货订单物料明细Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
@RequiredArgsConstructor
@Service
public class SalesOrderMaterialServiceImpl implements ISalesOrderMaterialService {
    private final SalesOrderMaterialMapper salesOrderMaterialMapper;

    /**
     * 查询大货订单物料明细
     *
     * @param id 大货订单物料明细主键
     * @return 大货订单物料明细
     */
    @Override
    public SalesOrderMaterial selectSalesOrderMaterialById(Long id) {
        return salesOrderMaterialMapper.selectSalesOrderMaterialById(id);
    }

    /**
     * 查询大货订单物料明细列表
     *
     * @param salesOrderMaterial 大货订单物料明细
     * @return 大货订单物料明细
     */
    @Override
    public List<SalesOrderMaterial> selectSalesOrderMaterialList(SalesOrderMaterial salesOrderMaterial) {
        return salesOrderMaterialMapper.selectSalesOrderMaterialList(salesOrderMaterial);
    }

    /**
     * 新增大货订单物料明细
     *
     * @param salesOrderMaterial 大货订单物料明细
     * @return 结果
     */
    @Override
    public int insertSalesOrderMaterial(SalesOrderMaterial salesOrderMaterial) {
        salesOrderMaterial.setCreateBy(SecurityUtils.getUserIdStr());
        salesOrderMaterial.setCreateTime(DateUtils.getNowDate());
        return salesOrderMaterialMapper.insertSalesOrderMaterial(salesOrderMaterial);
    }

    /**
     * 修改大货订单物料明细
     *
     * @param salesOrderMaterial 大货订单物料明细
     * @return 结果
     */
    @Override
    public int updateSalesOrderMaterial(SalesOrderMaterial salesOrderMaterial) {
        salesOrderMaterial.setUpdateTime(DateUtils.getNowDate());
        return salesOrderMaterialMapper.updateSalesOrderMaterial(salesOrderMaterial);
    }

    /**
     * 批量删除大货订单物料明细
     *
     * @param ids 需要删除的大货订单物料明细主键
     * @return 结果
     */
    @Override
    public int deleteSalesOrderMaterialByIds(Long[] ids) {
        return salesOrderMaterialMapper.deleteSalesOrderMaterialByIds(ids);
    }

    /**
     * 删除大货订单物料明细信息
     *
     * @param id 大货订单物料明细主键
     * @return 结果
     */
    @Override
    public int deleteSalesOrderMaterialById(Long id) {
        return salesOrderMaterialMapper.deleteSalesOrderMaterialById(id);
    }

    /**
     * 批量新增大货订单物料明细
     *
     * @param list 大货订单物料明细
     * @return 结果
     */
    @Override
    public int insertSalesOrderMaterialBatch(List<SalesOrderMaterial> list) {
        return salesOrderMaterialMapper.insertSalesOrderMaterialBatch(list);
    }
}
