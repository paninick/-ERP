package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.SalesOrderItemMapper;
import com.ruoyi.erp.domain.SalesOrderItem;
import com.ruoyi.erp.service.ISalesOrderItemService;

/**
 * 销售订单明细Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
@RequiredArgsConstructor
@Service
public class SalesOrderItemServiceImpl implements ISalesOrderItemService {
    private final SalesOrderItemMapper salesOrderItemMapper;

    /**
     * 查询销售订单明细
     *
     * @param id 销售订单明细主键
     * @return 销售订单明细
     */
    @Override
    public SalesOrderItem selectSalesOrderItemById(Long id) {
        return salesOrderItemMapper.selectSalesOrderItemById(id);
    }

    /**
     * 查询销售订单明细列表
     *
     * @param salesOrderItem 销售订单明细
     * @return 销售订单明细
     */
    @Override
    public List<SalesOrderItem> selectSalesOrderItemList(SalesOrderItem salesOrderItem) {
        return salesOrderItemMapper.selectSalesOrderItemList(salesOrderItem);
    }

    /**
     * 新增销售订单明细
     *
     * @param salesOrderItem 销售订单明细
     * @return 结果
     */
    @Override
    public int insertSalesOrderItem(SalesOrderItem salesOrderItem) {
        salesOrderItem.setCreateBy(SecurityUtils.getUserIdStr());
        salesOrderItem.setCreateTime(DateUtils.getNowDate());
        return salesOrderItemMapper.insertSalesOrderItem(salesOrderItem);
    }

    /**
     * 修改销售订单明细
     *
     * @param salesOrderItem 销售订单明细
     * @return 结果
     */
    @Override
    public int updateSalesOrderItem(SalesOrderItem salesOrderItem) {
        salesOrderItem.setUpdateTime(DateUtils.getNowDate());
        return salesOrderItemMapper.updateSalesOrderItem(salesOrderItem);
    }

    /**
     * 批量删除销售订单明细
     *
     * @param ids 需要删除的销售订单明细主键
     * @return 结果
     */
    @Override
    public int deleteSalesOrderItemByIds(Long[] ids) {
        return salesOrderItemMapper.deleteSalesOrderItemByIds(ids);
    }

    /**
     * 删除销售订单明细信息
     *
     * @param id 销售订单明细主键
     * @return 结果
     */
    @Override
    public int deleteSalesOrderItemById(Long id) {
        return salesOrderItemMapper.deleteSalesOrderItemById(id);
    }

    /**
     * 批量新增销售订单明细
     *
     * @param list 销售订单明细
     * @return 结果
     */
    @Override
    public int insertSalesOrderItemBatch(List<SalesOrderItem> list) {
        return salesOrderItemMapper.insertSalesOrderItemBatch(list);
    }
}
