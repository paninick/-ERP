package com.ruoyi.system.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.ruoyi.system.domain.FinInvoice;

/**
 * 财务发票 Mapper 接口
 *
 * @author ruoyi
 */
public interface FinInvoiceMapper
{
    /**
     * 查询发票看板卡片流列表（含工厂权限）
     */
    List<FinInvoice> selectInvoiceBoardList(FinInvoice finInvoice);

    /**
     * 按 ID 查询（用于状态机计算）
     */
    FinInvoice selectInvoiceById(Long id);

    /**
     * 新增发票
     */
    int insertFinInvoice(FinInvoice finInvoice);

    /**
     * 更新发票（核销状态/金额累计）
     */
    int updateFinInvoice(FinInvoice finInvoice);

    /**
     * 看板汇总统计
     * 返回 {invoicedAmount, pendingAmount, reconcileRate}
     */
    Map<String, Object> selectBoardStats(Long factoryId);
}
