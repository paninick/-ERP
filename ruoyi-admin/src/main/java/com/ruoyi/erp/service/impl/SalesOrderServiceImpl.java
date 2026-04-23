package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.SalesOrderMapper;
import com.ruoyi.erp.domain.SalesOrder;
import com.ruoyi.erp.service.ISalesOrderService;
import com.ruoyi.erp.utils.BillNoGenerator;

/**
 * 销售订单Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@Service
public class SalesOrderServiceImpl implements ISalesOrderService {
    @Autowired
    private SalesOrderMapper salesOrderMapper;

    @Autowired
    private BillNoGenerator billNoGenerator;

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
        if (salesOrder.getSalesNo() == null || salesOrder.getSalesNo().isEmpty()) {
            salesOrder.setSalesNo(billNoGenerator.generate("SO"));
        }
        if (salesOrder.getStyleNo() == null || salesOrder.getStyleNo().isEmpty()) {
            salesOrder.setStyleNo(billNoGenerator.generateStyleNo(null));
        }
        salesOrder.setCreateBy(SecurityUtils.getUserId().toString());
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

    /**
     * 导入销售订单数据
     *
     * @param salesOrderList 销售订单数据列表
     * @param updateSupport 是否更新支持，如果已存在，则进行更新
     * @return 结果
     */
    @Override
    public String importSalesOrder(List<SalesOrder> salesOrderList, boolean updateSupport)
    {
        if (salesOrderList == null || salesOrderList.isEmpty())
        {
            throw new IllegalArgumentException("导入销售订单数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        int skipNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SalesOrder salesOrder : salesOrderList)
        {
            try
            {
                // 只跳过 null 对象（Excel 空行），空 salesNo 视为待自动生成
                if (salesOrder == null)
                {
                    skipNum++;
                    continue;
                }

                SalesOrder existingSalesOrder = null;
                if (salesOrder.getSalesNo() != null && !salesOrder.getSalesNo().trim().isEmpty()) {
                    existingSalesOrder = salesOrderMapper.selectSalesOrderBySalesNo(salesOrder.getSalesNo());
                }

                if (existingSalesOrder != null)
                {
                    if (updateSupport)
                    {
                        salesOrder.setId(existingSalesOrder.getId());
                        salesOrderMapper.updateSalesOrder(salesOrder);
                        successNum++;
                        successMsg.append("\n").append(successNum).append("、销售单号 " + salesOrder.getSalesNo() + " 更新成功");
                    }
                    else
                    {
                        failureNum++;
                        failureMsg.append("\n").append(failureNum).append("、销售单号 " + salesOrder.getSalesNo() + " 已存在");
                    }
                }
                else
                {
                    // 统一经由 insertSalesOrder()，空单号自动生成，保证所有单号出自同一入口
                    this.insertSalesOrder(salesOrder);
                    successNum++;
                    successMsg.append("\n").append(successNum).append("、销售单号 " + salesOrder.getSalesNo() + " 导入成功");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String salesNo = (salesOrder != null && salesOrder.getSalesNo() != null) ? salesOrder.getSalesNo() : "空";
                String msg = "\n" + failureNum + "、销售单号 " + salesNo + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new IllegalArgumentException(failureMsg.toString());
        }
        else
        {
            String skipInfo = skipNum > 0 ? "（跳过空行 " + skipNum + " 条）" : "";
            successMsg.insert(0, "导入成功！共 " + successNum + " 条数据" + skipInfo + "，如下：");
        }
        return successMsg.toString();
    }
}
