package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.FinReconciliation;
import org.apache.ibatis.annotations.Param;

public interface FinReconciliationMapper {
    FinReconciliation selectById(Long id);
    List<FinReconciliation> selectByInvoiceId(Long invoiceId);
    List<FinReconciliation> selectActiveByInvoiceId(Long invoiceId);
    int insert(FinReconciliation rec);
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
