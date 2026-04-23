package com.ruoyi.erp.service.impl;

import com.ruoyi.erp.domain.ErpEmployee;
import com.ruoyi.erp.domain.ProcessDef;
import com.ruoyi.erp.domain.ProduceJob;
import com.ruoyi.erp.domain.vo.ProduceBoardStatsVO;
import com.ruoyi.erp.domain.vo.ProcessWipStatsVO;
import com.ruoyi.erp.domain.vo.EmployeeRankVO;
import com.ruoyi.erp.mapper.ErpEmployeeMapper;
import com.ruoyi.erp.mapper.ProcessDefMapper;
import com.ruoyi.erp.mapper.ProduceJobMapper;
import com.ruoyi.erp.mapper.ProducePlanMapper;
import com.ruoyi.erp.mapper.BizAbnormalPoolMapper;
import com.ruoyi.erp.mapper.ProduceMaterialConsumeMapper;
import com.ruoyi.erp.service.IProduceBoardService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * 生产看板Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
@Service
public class ProduceBoardServiceImpl implements IProduceBoardService {

    @Autowired
    private ProducePlanMapper producePlanMapper;

    @Autowired
    private ProduceJobMapper produceJobMapper;

    @Autowired
    private ProcessDefMapper processDefMapper;

    @Autowired
    private ErpEmployeeMapper erpEmployeeMapper;

    @Autowired
    private BizAbnormalPoolMapper bizAbnormalPoolMapper;

    @Autowired
    private ProduceMaterialConsumeMapper produceMaterialConsumeMapper;

    /**
     * 瓶颈预警阈值：当WIP预计天数超过此值则标记为瓶颈
     */
    private static final int BOTTLENECK_THRESHOLD_DAYS = 3;

    @Override
    public ProduceBoardStatsVO calculateBoardStats() {
        ProduceBoardStatsVO stats = new ProduceBoardStatsVO();

        // 统计生产计划
        int totalPlans = producePlanMapper.countAll();
        int inProgressPlans = producePlanMapper.countByStatus("3"); // 生产中
        int completedPlans = producePlanMapper.countByStatusIn(List.of("5", "6", "7", "9")); // 已入库/完成
        int todayCompleted = producePlanMapper.countCompletedByDate(DateUtils.getNowDate());
        int weekCompleted = producePlanMapper.countCompletedBetween(getFirstDayOfWeek(), DateUtils.getNowDate());

        // 统计WIP工票
        int totalWipJobs = produceJobMapper.countByStatus("1"); // 生产中
        int todayNewJobs = produceJobMapper.countCreatedToday(DateUtils.getNowDate());

        // 统计待审批和未处理异常
        int pendingApproval = produceMaterialConsumeMapper.countPendingApproval();
        int unhandledAbnormal = bizAbnormalPoolMapper.countUnhandled();

        // 计算准时交付率
        BigDecimal onTimeRate = calculateOnTimeDeliveryRate();

        // 计算平均产能利用率
        BigDecimal capacity = calculateAverageCapacityUtilization();

        stats.setTotalPlans(totalPlans);
        stats.setInProgressPlans(inProgressPlans);
        stats.setCompletedPlans(completedPlans);
        stats.setTodayCompleted(todayCompleted);
        stats.setWeekCompleted(weekCompleted);
        stats.setTotalWipJobs(totalWipJobs);
        stats.setTodayNewJobs(todayNewJobs);
        stats.setPendingApprovalCount(pendingApproval);
        stats.setUnhandledAbnormalCount(unhandledAbnormal);
        stats.setOnTimeDeliveryRate(onTimeRate);
        stats.setCapacityUtilization(capacity);

        return stats;
    }

    @Override
    public List<ProcessWipStatsVO> calculateProcessWipStats() {
        List<ProcessDef> processes = processDefMapper.selectProcessDefList(new ProcessDef());
        List<ProcessWipStatsVO> result = new ArrayList<>();

        for (ProcessDef process : processes) {
            // ERP 基础数据里 0=启用，1=停用；看板只统计启用工序
            if ("0".equals(process.getStatus())) {
                ProcessWipStatsVO vo = new ProcessWipStatsVO();
                vo.setProcessId(process.getId());
                vo.setProcessName(process.getProcessName());

                // 统计该工序当前WIP
                int jobCount = produceJobMapper.countWipByProcess(process.getId());
                int totalQty = produceJobMapper.sumWipQtyByProcess(process.getId());

                // 估算日均产能（简单计算：根据历史数据估算）
                int dailyCapacity = estimateDailyCapacity(process.getId());

                vo.setWipJobCount(jobCount);
                vo.setWipQuantity(totalQty);
                vo.setDailyCapacity(dailyCapacity);

                // 计算预计完成天数
                if (dailyCapacity > 0) {
                    BigDecimal estimatedDays = BigDecimal.valueOf(totalQty)
                            .divide(BigDecimal.valueOf(dailyCapacity), 1, RoundingMode.HALF_UP);
                    vo.setEstimatedDays(estimatedDays);
                    vo.setIsBottleneck(estimatedDays.intValue() > BOTTLENECK_THRESHOLD_DAYS);
                } else {
                    vo.setEstimatedDays(BigDecimal.ZERO);
                    vo.setIsBottleneck(false);
                }

                // 计算利用率
                BigDecimal utilization = calculateProcessUtilization(process.getId());
                vo.setUtilizationRate(utilization);

                result.add(vo);
            }
        }

        // 按预计天数降序排序
        result.sort((a, b) -> b.getEstimatedDays().compareTo(a.getEstimatedDays()));

        return result;
    }

    @Override
    public List<EmployeeRankVO> getEmployeeProductivityRank() {
        // 获取当前月
        Calendar cal = Calendar.getInstance();
        Date startDate = getFirstDayOfMonth(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
        Date endDate = getLastDayOfMonth(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);

        List<EmployeeRankVO> list = produceJobMapper.selectEmployeeMonthlyRank(startDate, endDate);

        // 设置排名
        int rank = 1;
        for (EmployeeRankVO vo : list) {
            vo.setRank(rank++);
        }

        // 只返回前10名
        if (list.size() > 10) {
            return list.subList(0, 10);
        }
        return list;
    }

    @Override
    public List<ProcessWipStatsVO> getBottleneckWarnings() {
        List<ProcessWipStatsVO> allStats = calculateProcessWipStats();
        List<ProcessWipStatsVO> bottlenecks = new ArrayList<>();

        for (ProcessWipStatsVO vo : allStats) {
            if (Boolean.TRUE.equals(vo.getIsBottleneck())) {
                bottlenecks.add(vo);
            }
        }

        return bottlenecks;
    }

    /**
     * 计算准时交付率
     */
    private BigDecimal calculateOnTimeDeliveryRate() {
        int totalCompleted = producePlanMapper.countCompletedInPeriod(null, null);
        if (totalCompleted == 0) {
            return BigDecimal.ZERO;
        }
        int onTime = producePlanMapper.countOnTimeCompleted();
        return BigDecimal.valueOf(onTime)
                .divide(BigDecimal.valueOf(totalCompleted), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    /**
     * 计算平均产能利用率
     */
    private BigDecimal calculateAverageCapacityUtilization() {
        List<ProcessWipStatsVO> allStats = calculateProcessWipStats();
        if (allStats.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal total = BigDecimal.ZERO;
        for (ProcessWipStatsVO vo : allStats) {
            total = total.add(vo.getUtilizationRate());
        }
        return total.divide(BigDecimal.valueOf(allStats.size()), 2, RoundingMode.HALF_UP);
    }

    /**
     * 估算日均产能
     */
    private int estimateDailyCapacity(Long processId) {
        // 简化处理：基于过去7天平均产量
        Date startDate = DateUtils.addDays(DateUtils.getNowDate(), -7);
        Integer avg = produceJobMapper.selectDailyAvgByProcess(processId, startDate, DateUtils.getNowDate());
        return avg != null && avg > 0 ? avg : 50; // 默认50件/天
    }

    /**
     * 计算工序利用率
     */
    private BigDecimal calculateProcessUtilization(Long processId) {
        // 简化计算：实际WIP / (日均产能 * 工作日
        int wipQty = produceJobMapper.sumWipQtyByProcess(processId);
        int dailyCapacity = estimateDailyCapacity(processId);
        if (dailyCapacity == 0) {
            return BigDecimal.ZERO;
        }
        // 假设5天产能为基准，计算当前利用率
        int capacity5days = dailyCapacity * 5;
        if (capacity5days == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(wipQty)
                .divide(BigDecimal.valueOf(capacity5days), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    /**
     * 获取本周第一天
     */
    private Date getFirstDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取当月第一天
     */
    private Date getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取当月最后一天
     */
    private Date getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }
}
