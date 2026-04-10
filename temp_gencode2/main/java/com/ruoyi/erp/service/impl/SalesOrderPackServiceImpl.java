package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.SalesOrderPackMapper;
import com.ruoyi.erp.domain.SalesOrderPack;
import com.ruoyi.erp.service.ISalesOrderPackService;

/**
 * 大货订单包装纸箱明细Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
@RequiredArgsConstructor
@Service
public class SalesOrderPackServiceImpl implements ISalesOrderPackService {
    private final SalesOrderPackMapper salesOrderPackMapper;

    /**
     * 查询大货订单包装纸箱明细
     *
     * @param id 大货订单包装纸箱明细主键
     * @return 大货订单包装纸箱明细
     */
    @Override
    public SalesOrderPack selectSalesOrderPackById(Long id) {
        return salesOrderPackMapper.selectSalesOrderPackById(id);
    }

    /**
     * 查询大货订单包装纸箱明细列表
     *
     * @param salesOrderPack 大货订单包装纸箱明细
     * @return 大货订单包装纸箱明细
     */
    @Override
    public List<SalesOrderPack> selectSalesOrderPackList(SalesOrderPack salesOrderPack) {
        return salesOrderPackMapper.selectSalesOrderPackList(salesOrderPack);
    }

    /**
     * 新增大货订单包装纸箱明细
     *
     * @param salesOrderPack 大货订单包装纸箱明细
     * @return 结果
     */
    @Override
    public int insertSalesOrderPack(SalesOrderPack salesOrderPack) {
        salesOrderPack.setCreateBy(SecurityUtils.getUserIdStr());
        salesOrderPack.setCreateTime(DateUtils.getNowDate());
        return salesOrderPackMapper.insertSalesOrderPack(salesOrderPack);
    }

    /**
     * 修改大货订单包装纸箱明细
     *
     * @param salesOrderPack 大货订单包装纸箱明细
     * @return 结果
     */
    @Override
    public int updateSalesOrderPack(SalesOrderPack salesOrderPack) {
        salesOrderPack.setUpdateTime(DateUtils.getNowDate());
        return salesOrderPackMapper.updateSalesOrderPack(salesOrderPack);
    }

    /**
     * 批量删除大货订单包装纸箱明细
     *
     * @param ids 需要删除的大货订单包装纸箱明细主键
     * @return 结果
     */
    @Override
    public int deleteSalesOrderPackByIds(Long[] ids) {
        return salesOrderPackMapper.deleteSalesOrderPackByIds(ids);
    }

    /**
     * 删除大货订单包装纸箱明细信息
     *
     * @param id 大货订单包装纸箱明细主键
     * @return 结果
     */
    @Override
    public int deleteSalesOrderPackById(Long id) {
        return salesOrderPackMapper.deleteSalesOrderPackById(id);
    }

    /**
     * 批量新增大货订单包装纸箱明细
     *
     * @param list 大货订单包装纸箱明细
     * @return 结果
     */
    @Override
    public int insertSalesOrderPackBatch(List<SalesOrderPack> list) {
        return salesOrderPackMapper.insertSalesOrderPackBatch(list);
    }
}
