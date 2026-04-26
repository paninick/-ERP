package com.ruoyi.erp.mapper;

import com.ruoyi.erp.domain.ProduceMaterialConsume;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Production material consume mapper.
 */
@Mapper
public interface ProduceMaterialConsumeMapper {
    ProduceMaterialConsume selectProduceMaterialConsumeById(Long id);

    List<ProduceMaterialConsume> selectProduceMaterialConsumeList(ProduceMaterialConsume produceMaterialConsume);

    int insertProduceMaterialConsume(ProduceMaterialConsume produceMaterialConsume);

    int updateProduceMaterialConsume(ProduceMaterialConsume produceMaterialConsume);

    int deleteProduceMaterialConsumeById(Long id);

    int deleteProduceMaterialConsumeByIds(Long[] ids);

    BigDecimal sumActualLossByProducePlan(Long producePlanId);

    List<ProduceMaterialConsume> selectByProducePlanId(Long producePlanId);

    int countPendingApproval();

    Map<String, Object> selectLossStats();
}
