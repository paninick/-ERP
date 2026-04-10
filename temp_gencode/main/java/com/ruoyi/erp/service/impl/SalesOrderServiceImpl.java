package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.SalesOrderMapper;
import com.ruoyi.erp.domain.SalesOrder;
import com.ruoyi.erp.service.ISalesOrderService;

/**
 * 销售订单Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
@RequiredArgsConstructor
@Service
public class SalesOrderServiceImpl implements ISalesOrderService {
    private final SalesOrderMapper salesOrderMapper;

    /**
     * 查询销售订单
     *
     * @param id 销售订单主键
     * @return 销售订单
     */
    @Override
    public SalesOrder selectSalesOrderById(Long id) {
        return salesOrderMapper.selectSalesOrderById(id);
    }

    /**
     * 查询销售订单列表
     *
     * @param salesOrder 销售订单
     * @return 销售订单
     */
    @Override
    public List<SalesOrder> selectSalesOrderList(SalesOrder salesOrder) {
        return salesOrderMapper.selectSalesOrderList(salesOrder);
    }

    /**
     * 新增销售订单
     *
     * @param salesOrder 销售订单
     * @return 结果
     */
    @Override
    public int insertSalesOrder(SalesOrder salesOrder) {
        salesOrder.setCreateBy(SecurityUtils.getUserIdStr());
        salesOrder.setCreateTime(DateUtils.getNowDate());
        return salesOrderMapper.insertSalesOrder(salesOrder);
    }

    /**
     * 修改销售订单
     *
     * @param salesOrder 销售订单
     * @return 结果
     */
    @Override
    public int updateSalesOrder(SalesOrder salesOrder) {
        salesOrder.setUpdateTime(DateUtils.getNowDate());
        return salesOrderMapper.updateSalesOrder(salesOrder);
    }

    /**
     * 批量删除销售订单
     *
     * @param ids 需要删除的销售订单主键
     * @return 结果
     */
    @Override
    public int deleteSalesOrderByIds(Long[] ids) {
        return salesOrderMapper.deleteSalesOrderByIds(ids);
    }

    /**
     * 删除销售订单信息
     *
     * @param id 销售订单主键
     * @return 结果
     */
    @Override
    public int deleteSalesOrderById(Long id) {
        return salesOrderMapper.deleteSalesOrderById(id);
    }

    /**
     * 批量新增销售订单
     *
     * @param list 销售订单
     * @return 结果
     */
    @Override
    public int insertSalesOrderBatch(List<SalesOrder> list) {
        return salesOrderMapper.insertSalesOrderBatch(list);
    }
}
