package com.ruoyi.erp.domain.vo;

import java.math.BigDecimal;

/**
 * 生产看板总体统计VO
 *
 * @author zhangmingjian
 */
public class ProduceBoardStatsVO {

    /**
     * 总生产计划数
     */
    private Integer totalPlans;

    /**
     * 进行中计划数
     */
    private Integer inProgressPlans;

    /**
     * 已完成计划数
     */
    private Integer completedPlans;

    /**
     * 今日完工数
     */
    private Integer todayCompleted;

    /**
     * 本周完工数
     */
    private Integer weekCompleted;

    /**
     * 总WIP扎数
     */
    private Integer totalWipJobs;

    /**
     * 今日新增工票数
     */
    private Integer todayNewJobs;

    /**
     * 待审批超领单数
     */
    private Integer pendingApprovalCount;

    /**
     * 未处理异常数
     */
    private Integer unhandledAbnormalCount;

    /**
     * 准时交付率
     */
    private BigDecimal onTimeDeliveryRate;

    /**
     * 总体产能利用率 %
     */
    private BigDecimal capacityUtilization;

    public Integer getTotalPlans() {
        return totalPlans;
    }

    public void setTotalPlans(Integer totalPlans) {
        this.totalPlans = totalPlans;
    }

    public Integer getInProgressPlans() {
        return inProgressPlans;
    }

    public void setInProgressPlans(Integer inProgressPlans) {
        this.inProgressPlans = inProgressPlans;
    }

    public Integer getCompletedPlans() {
        return completedPlans;
    }

    public void setCompletedPlans(Integer completedPlans) {
        this.completedPlans = completedPlans;
    }

    public Integer getTodayCompleted() {
        return todayCompleted;
    }

    public void setTodayCompleted(Integer todayCompleted) {
        this.todayCompleted = todayCompleted;
    }

    public Integer getWeekCompleted() {
        return weekCompleted;
    }

    public void setWeekCompleted(Integer weekCompleted) {
        this.weekCompleted = weekCompleted;
    }

    public Integer getTotalWipJobs() {
        return totalWipJobs;
    }

    public void setTotalWipJobs(Integer totalWipJobs) {
        this.totalWipJobs = totalWipJobs;
    }

    public Integer getTodayNewJobs() {
        return todayNewJobs;
    }

    public void setTodayNewJobs(Integer todayNewJobs) {
        this.todayNewJobs = todayNewJobs;
    }

    public Integer getPendingApprovalCount() {
        return pendingApprovalCount;
    }

    public void setPendingApprovalCount(Integer pendingApprovalCount) {
        this.pendingApprovalCount = pendingApprovalCount;
    }

    public Integer getUnhandledAbnormalCount() {
        return unhandledAbnormalCount;
    }

    public void setUnhandledAbnormalCount(Integer unhandledAbnormalCount) {
        this.unhandledAbnormalCount = unhandledAbnormalCount;
    }

    public BigDecimal getOnTimeDeliveryRate() {
        return onTimeDeliveryRate;
    }

    public void setOnTimeDeliveryRate(BigDecimal onTimeDeliveryRate) {
        this.onTimeDeliveryRate = onTimeDeliveryRate;
    }

    public BigDecimal getCapacityUtilization() {
        return capacityUtilization;
    }

    public void setCapacityUtilization(BigDecimal capacityUtilization) {
        this.capacityUtilization = capacityUtilization;
    }
}
