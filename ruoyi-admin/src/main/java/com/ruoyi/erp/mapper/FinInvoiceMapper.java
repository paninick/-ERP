package com.ruoyi.erp.mapper;

import java.util.List;
import java.util.Map;
import com.ruoyi.erp.domain.FinInvoice;
import org.apache.ibatis.annotations.Param;

public interface FinInvoiceMapper {
    FinInvoice selectById(Long id);
    FinInvoice selectByInvoiceNo(String invoiceNo);
    List<FinInvoice> selectList(FinInvoice query);
    int insert(FinInvoice invoice);
    int update(FinInvoice invoice);
    int deleteById(Long id);
    int updateSettledAmount(@Param("id") Long id, @Param("settledAmount") java.math.BigDecimal settledAmount, @Param("status") String status);
    Map<String, Object> selectBoardStats(@Param("factoryId") Long factoryId);
}
