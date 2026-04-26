package com.ruoyi.erp.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.erp.domain.ProduceJob;
import com.ruoyi.erp.domain.ProduceMaterialConsume;
import com.ruoyi.erp.domain.StockOut;
import com.ruoyi.erp.domain.StockOutItem;
import com.ruoyi.erp.domain.BizAbnormalPool;
import com.ruoyi.erp.domain.ProduceJobProcess;
import com.ruoyi.erp.mapper.ProduceJobMapper;
import com.ruoyi.erp.mapper.ProduceJobProcessMapper;
import com.ruoyi.erp.mapper.ProduceMaterialConsumeMapper;
import com.ruoyi.erp.mapper.StockOutItemMapper;
import com.ruoyi.erp.mapper.StockOutMapper;
import com.ruoyi.erp.service.IBizAbnormalPoolService;
import com.ruoyi.erp.service.IProduceMaterialConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProduceMaterialConsumeServiceImpl implements IProduceMaterialConsumeService {

    private static final BigDecimal DEFAULT_LOSS_RATE = new BigDecimal("5");
    private static final String BIZ_TYPE_MATERIAL_CONSUME = "MATERIAL_CONSUME";
    private static final String ABNORMAL_CODE_OVER_LIMIT = "MATERIAL_OVER_LIMIT";

    @Autowired
    private ProduceMaterialConsumeMapper produceMaterialConsumeMapper;

    @Autowired
    private StockOutMapper stockOutMapper;

    @Autowired
    private StockOutItemMapper stockOutItemMapper;

    @Autowired
    private ProduceJobMapper produceJobMapper;

    @Autowired
    private ProduceJobProcessMapper produceJobProcessMapper;

    @Autowired
    private IBizAbnormalPoolService bizAbnormalPoolService;

    @Override
    public ProduceMaterialConsume selectProduceMaterialConsumeById(Long id) {
        return produceMaterialConsumeMapper.selectProduceMaterialConsumeById(id);
    }

    @Override
    public List<ProduceMaterialConsume> selectProduceMaterialConsumeList(ProduceMaterialConsume produceMaterialConsume) {
        return produceMaterialConsumeMapper.selectProduceMaterialConsumeList(produceMaterialConsume);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertProduceMaterialConsume(ProduceMaterialConsume consume) {
        fillDerivedFields(consume);
        consume.setCreateBy(SecurityUtils.getUsername());
        consume.setCreateTime(DateUtils.getNowDate());
        consume.setUpdateBy(SecurityUtils.getUsername());
        consume.setUpdateTime(DateUtils.getNowDate());
        int rows = produceMaterialConsumeMapper.insertProduceMaterialConsume(consume);
        syncOverLimitAbnormal(consume);
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateProduceMaterialConsume(ProduceMaterialConsume consume) {
        fillDerivedFields(consume);
        consume.setUpdateBy(SecurityUtils.getUsername());
        consume.setUpdateTime(DateUtils.getNowDate());
        int rows = produceMaterialConsumeMapper.updateProduceMaterialConsume(consume);
        syncOverLimitAbnormal(consume);
        return rows;
    }

    @Override
    public int deleteProduceMaterialConsumeByIds(Long[] ids) {
        return produceMaterialConsumeMapper.deleteProduceMaterialConsumeByIds(ids);
    }

    @Override
    public int deleteProduceMaterialConsumeById(Long id) {
        return produceMaterialConsumeMapper.deleteProduceMaterialConsumeById(id);
    }

    @Override
    public BigDecimal sumActualLossByProducePlan(Long producePlanId) {
        return produceMaterialConsumeMapper.sumActualLossByProducePlan(producePlanId);
    }

    @Override
    public List<ProduceMaterialConsume> selectByProducePlanId(Long producePlanId) {
        return produceMaterialConsumeMapper.selectByProducePlanId(producePlanId);
    }

    @Override
    public BigDecimal calculateLimitQty(BigDecimal bomQty, BigDecimal standardLossRate) {
        BigDecimal safeBomQty = defaultDecimal(bomQty);
        BigDecimal safeLossRate = defaultDecimal(standardLossRate);
        BigDecimal rate = safeLossRate.divide(new BigDecimal("100"), 6, RoundingMode.HALF_UP);
        return safeBomQty.multiply(BigDecimal.ONE.add(rate)).setScale(4, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateActualLoss(BigDecimal actualQty, BigDecimal bomQty) {
        return defaultDecimal(actualQty).subtract(defaultDecimal(bomQty)).setScale(4, RoundingMode.HALF_UP);
    }

    @Override
    public Map<String, Object> selectLossStats() {
        Map<String, Object> stats = produceMaterialConsumeMapper.selectLossStats();
        if (stats == null) {
            stats = new HashMap<>();
        }
        long total = stats.get("totalRecords") != null ? ((Number) stats.get("totalRecords")).longValue() : 0L;
        long overLimit = stats.get("overLimitCount") != null ? ((Number) stats.get("overLimitCount")).longValue() : 0L;
        String rate = total > 0
            ? new BigDecimal(overLimit)
                .divide(new BigDecimal(total), 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"))
                .setScale(2, RoundingMode.HALF_UP)
                .toPlainString()
            : "0.00";
        stats.put("overLimitRate", rate);
        return stats;
    }

    @Override
    public int approveLoss(Long id, boolean approved, String remark) {
        ProduceMaterialConsume consume = produceMaterialConsumeMapper.selectProduceMaterialConsumeById(id);
        if (consume == null) {
            return 0;
        }
        consume.setApprovalStatus(approved ? "2" : "3");
        consume.setApprovalRemark(remark);
        consume.setApprovalByName(SecurityUtils.getUsername());
        consume.setApprovalTime(DateUtils.getNowDate());
        consume.setUpdateBy(SecurityUtils.getUsername());
        consume.setUpdateTime(DateUtils.getNowDate());
        return produceMaterialConsumeMapper.updateProduceMaterialConsume(consume);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> syncByStockOut(Long stockOutId) {
        if (stockOutId == null || stockOutId <= 0) {
            throw new ServiceException("出库单ID不能为空");
        }

        StockOut stockOut = stockOutMapper.selectStockOutById(stockOutId);
        if (stockOut == null) {
            throw new ServiceException("出库单不存在");
        }

        List<StockOutItem> items = stockOutItemMapper.selectStockOutItemByOutId(stockOutId);
        if (items == null || items.isEmpty()) {
            throw new ServiceException("出库单没有可同步的明细");
        }

        ProduceJob job = selectPrimaryJob(stockOut.getPlanId());

        ProduceMaterialConsume query = new ProduceMaterialConsume();
        query.setStockOutId(stockOutId);
        List<ProduceMaterialConsume> existingList = produceMaterialConsumeMapper.selectProduceMaterialConsumeList(query);
        Map<Long, ProduceMaterialConsume> existingMap = existingList.stream()
            .collect(Collectors.toMap(ProduceMaterialConsume::getStockOutItemId, Function.identity()));

        int inserted = 0;
        int updated = 0;

        for (StockOutItem item : items) {
            ProduceMaterialConsume existing = existingMap.get(item.getId());
            ProduceMaterialConsume target = existing != null ? existing : new ProduceMaterialConsume();
            fillByStockOut(target, stockOut, item, job);
            if (existing == null) {
                insertProduceMaterialConsume(target);
                inserted++;
            } else {
                updateProduceMaterialConsume(target);
                updated++;
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("stockOutId", stockOutId);
        result.put("itemCount", items.size());
        result.put("insertedCount", inserted);
        result.put("updatedCount", updated);
        result.put("producePlanId", stockOut.getPlanId());
        result.put("jobId", job != null ? job.getId() : null);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> bindToJobProcess(Long consumeId, Long jobProcessId, Long reportLogId) {
        if (consumeId == null || consumeId <= 0) {
            throw new ServiceException("用料记录ID不能为空");
        }
        if (jobProcessId == null || jobProcessId <= 0) {
            throw new ServiceException("工序快照ID不能为空");
        }

        ProduceMaterialConsume consume = produceMaterialConsumeMapper.selectProduceMaterialConsumeById(consumeId);
        if (consume == null) {
            throw new ServiceException("用料记录不存在");
        }
        if (bizAbnormalPoolService.hasLockedUnhandledAbnormal(BIZ_TYPE_MATERIAL_CONSUME, consumeId)) {
            throw new ServiceException("该用料记录存在锁定中的异常，禁止继续绑定，请先处理异常池记录");
        }

        ProduceJobProcess jobProcess = produceJobProcessMapper.selectProduceJobProcessById(jobProcessId);
        if (jobProcess == null) {
            throw new ServiceException("工序快照不存在");
        }

        if (consume.getJobId() != null && jobProcess.getJobId() != null && !consume.getJobId().equals(jobProcess.getJobId())) {
            throw new ServiceException("用料记录与工序快照不属于同一生产工单");
        }

        consume.setJobId(jobProcess.getJobId());
        consume.setJobProcessId(jobProcess.getId());
        consume.setProcessId(jobProcess.getProcessId());
        consume.setProcessName(jobProcess.getProcessName());
        consume.setReportLogId(reportLogId);
        updateProduceMaterialConsume(consume);

        Map<String, Object> result = new HashMap<>();
        result.put("consumeId", consume.getId());
        result.put("jobId", consume.getJobId());
        result.put("jobProcessId", consume.getJobProcessId());
        result.put("processId", consume.getProcessId());
        result.put("reportLogId", consume.getReportLogId());
        return result;
    }

    private void fillByStockOut(ProduceMaterialConsume consume, StockOut stockOut, StockOutItem item, ProduceJob job) {
        consume.setStockOutId(stockOut.getId());
        consume.setStockOutItemId(item.getId());
        consume.setProducePlanId(stockOut.getPlanId());
        consume.setJobId(job != null ? job.getId() : null);
        consume.setOrderId(job != null ? job.getOrderId() : null);
        consume.setMaterialId(item.getMaterialId());
        consume.setMaterialCode(item.getMaterialNo());
        consume.setMaterialName(item.getName());
        consume.setMaterialType(item.getMaterialType());
        consume.setBatchNo(stockOut.getSn());
        consume.setActualQty(defaultDecimal(item.getCount()));
        if (consume.getBomQty() == null || consume.getBomQty().compareTo(BigDecimal.ZERO) <= 0) {
            consume.setBomQty(defaultDecimal(item.getCount()));
        }
        if (consume.getStandardLossRate() == null) {
            consume.setStandardLossRate(DEFAULT_LOSS_RATE);
        }
        if (consume.getApprovalStatus() == null || consume.getApprovalStatus().isEmpty()) {
            consume.setApprovalStatus("0");
        }
        if (consume.getUnit() == null || consume.getUnit().isEmpty()) {
            consume.setUnit("PCS");
        }
        if (consume.getRemark() == null || consume.getRemark().isEmpty()) {
            consume.setRemark("AUTO_SYNC_FROM_STOCK_OUT");
        }
    }

    private ProduceJob selectPrimaryJob(Long producePlanId) {
        if (producePlanId == null) {
            return null;
        }
        List<ProduceJob> jobs = produceJobMapper.selectProduceJobByProducePlanId(producePlanId);
        if (jobs == null || jobs.isEmpty()) {
            return null;
        }
        return jobs.get(0);
    }

    private ProduceMaterialConsume findByStockOutItem(Long stockOutItemId) {
        if (stockOutItemId == null) {
            return null;
        }
        ProduceMaterialConsume query = new ProduceMaterialConsume();
        query.setStockOutItemId(stockOutItemId);
        List<ProduceMaterialConsume> list = produceMaterialConsumeMapper.selectProduceMaterialConsumeList(query);
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

    private void syncOverLimitAbnormal(ProduceMaterialConsume consume) {
        if (consume.getId() == null) {
            return;
        }
        if ("1".equals(consume.getIsOverLimit())) {
            openOverLimitAbnormal(consume);
        } else {
            closeOverLimitAbnormal(consume);
        }
    }

    private void openOverLimitAbnormal(ProduceMaterialConsume consume) {
        if (bizAbnormalPoolService.hasUnhandledAbnormal(BIZ_TYPE_MATERIAL_CONSUME, consume.getId())) {
            return;
        }
        BizAbnormalPool abnormal = new BizAbnormalPool();
        abnormal.setBizType(BIZ_TYPE_MATERIAL_CONSUME);
        abnormal.setBizId(consume.getId());
        abnormal.setAbnormalCode(ABNORMAL_CODE_OVER_LIMIT);
        abnormal.setAbnormalTitle(buildAbnormalTitle(consume));
        abnormal.setAbnormalDesc(buildAbnormalDesc(consume));
        abnormal.setAbnormalLevel(resolveAbnormalLevel(consume));
        abnormal.setStatus("0");
        abnormal.setLockBiz("1");
        abnormal.setRemark(buildAbnormalRemark(consume));
        bizAbnormalPoolService.insertBizAbnormalPool(abnormal);
    }

    private void closeOverLimitAbnormal(ProduceMaterialConsume consume) {
        BizAbnormalPool query = new BizAbnormalPool();
        query.setBizType(BIZ_TYPE_MATERIAL_CONSUME);
        query.setBizId(consume.getId());
        List<BizAbnormalPool> list = bizAbnormalPoolService.selectBizAbnormalPoolList(query);
        if (list == null || list.isEmpty()) {
            return;
        }
        for (BizAbnormalPool abnormal : list) {
            if (!ABNORMAL_CODE_OVER_LIMIT.equals(abnormal.getAbnormalCode())) {
                continue;
            }
            if (!"0".equals(abnormal.getStatus()) && !"1".equals(abnormal.getStatus())) {
                continue;
            }
            BizAbnormalPool closing = new BizAbnormalPool();
            closing.setId(abnormal.getId());
            closing.setStatus("3");
            closing.setLockBiz("0");
            closing.setHandleResult("Material consume returned within limit");
            bizAbnormalPoolService.updateBizAbnormalPool(closing);
        }
    }

    private String buildAbnormalTitle(ProduceMaterialConsume consume) {
        String material = consume.getMaterialName();
        if (material == null || material.isEmpty()) {
            material = consume.getMaterialCode();
        }
        if (material == null || material.isEmpty()) {
            material = "UNKNOWN_MATERIAL";
        }
        return "Material consume over limit: " + material;
    }

    private String buildAbnormalDesc(ProduceMaterialConsume consume) {
        return "actualQty=" + defaultDecimal(consume.getActualQty()).toPlainString()
            + ", bomQty=" + defaultDecimal(consume.getBomQty()).toPlainString()
            + ", actualLoss=" + defaultDecimal(consume.getActualLossQty()).toPlainString()
            + ", limitQty=" + defaultDecimal(consume.getLimitLossQty()).toPlainString()
            + ", costDiff=" + defaultDecimal(consume.getCostDiff()).toPlainString();
    }

    private String buildAbnormalRemark(ProduceMaterialConsume consume) {
        return "source=material_consume"
            + ", stockOutId=" + consume.getStockOutId()
            + ", stockOutItemId=" + consume.getStockOutItemId()
            + ", jobId=" + consume.getJobId()
            + ", processId=" + consume.getProcessId();
    }

    private Integer resolveAbnormalLevel(ProduceMaterialConsume consume) {
        BigDecimal bomQty = defaultDecimal(consume.getBomQty());
        BigDecimal actualLoss = defaultDecimal(consume.getActualLossQty());
        if (bomQty.compareTo(BigDecimal.ZERO) <= 0) {
            return 2;
        }
        BigDecimal ratio = actualLoss.divide(bomQty, 4, RoundingMode.HALF_UP);
        return ratio.compareTo(new BigDecimal("0.10")) >= 0 ? 3 : 2;
    }

    private void fillDerivedFields(ProduceMaterialConsume consume) {
        BigDecimal limitQty = calculateLimitQty(consume.getBomQty(), consume.getStandardLossRate());
        BigDecimal actualLoss = calculateActualLoss(consume.getActualQty(), consume.getBomQty());
        BigDecimal allowedLossQty = limitQty.subtract(defaultDecimal(consume.getBomQty())).setScale(4, RoundingMode.HALF_UP);
        consume.setLimitLossQty(limitQty);
        consume.setActualLossQty(actualLoss);

        if (actualLoss.compareTo(allowedLossQty) > 0) {
            consume.setIsOverLimit("1");
            if (!"2".equals(consume.getApprovalStatus()) && !"3".equals(consume.getApprovalStatus())) {
                consume.setApprovalStatus("1");
            }
        } else {
            consume.setIsOverLimit("0");
            if (consume.getApprovalStatus() == null || "1".equals(consume.getApprovalStatus())) {
                consume.setApprovalStatus("0");
            }
        }

        BigDecimal unitPrice = defaultDecimal(consume.getUnitPrice());
        BigDecimal theoreticalCost = defaultDecimal(consume.getBomQty()).multiply(unitPrice).setScale(4, RoundingMode.HALF_UP);
        BigDecimal actualCost = defaultDecimal(consume.getActualQty()).multiply(unitPrice).setScale(4, RoundingMode.HALF_UP);
        consume.setTheoreticalCost(theoreticalCost);
        consume.setActualCost(actualCost);
        consume.setCostDiff(actualCost.subtract(theoreticalCost).setScale(4, RoundingMode.HALF_UP));
    }

    private BigDecimal defaultDecimal(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    @Override
    public boolean hasLockedAbnormalByStockOut(Long stockOutId) {
        if (stockOutId == null) {
            return false;
        }
        ProduceMaterialConsume query = new ProduceMaterialConsume();
        query.setStockOutId(stockOutId);
        List<ProduceMaterialConsume> rows = produceMaterialConsumeMapper.selectProduceMaterialConsumeList(query);
        if (rows == null || rows.isEmpty()) {
            return false;
        }
        for (ProduceMaterialConsume row : rows) {
            if (row == null || row.getId() == null) {
                continue;
            }
            if (bizAbnormalPoolService.hasLockedUnhandledAbnormal(BIZ_TYPE_MATERIAL_CONSUME, row.getId())) {
                return true;
            }
        }
        return false;
    }
}
