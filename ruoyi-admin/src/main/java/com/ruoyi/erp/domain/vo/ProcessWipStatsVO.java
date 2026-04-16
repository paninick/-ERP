package com.ruoyi.erp.domain.vo;

import java.math.BigDecimal;

/**
 * 工序WIP统计VO
 *
 * @author zhangmingjian
 */
public class ProcessWipStatsVO {

    /**
     * 工序ID
     */
    private Long processId;

    /**
     * 工序名称
     */
    private String processName;

    /**
     * WIP扎数
     */
    private Integer wipJobCount;

    /**
     * WIP总件数
     */
    private Integer wipQuantity;

    /**
     * 日均产能
     */
    private Integer dailyCapacity;

    /**
     * 预计天数（当前WIP需要多少天完成）
     */
    private BigDecimal estimatedDays;

    /**
     * 是否瓶颈（true表示超过预警阈值）
     */
    private Boolean isBottleneck;

    /**
     * 产能利用率 %
     */
    private BigDecimal utilizationRate;

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Integer getWipJobCount() {
        return wipJobCount;
    }

    public void setWipJobCount(Integer wipJobCount) {
        this.wipJobCount = wipJobCount;
    }

    public Integer getWipQuantity() {
        return wipQuantity;
    }

    public void setWipQuantity(Integer wipQuantity) {
        this.wipQuantity = wipQuantity;
    }

    public Integer getDailyCapacity() {
        return dailyCapacity;
    }

    public void setDailyCapacity(Integer dailyCapacity) {
        this.dailyCapacity = dailyCapacity;
    }

    public BigDecimal getEstimatedDays() {
        return estimatedDays;
    }

    public void setEstimatedDays(BigDecimal estimatedDays) {
        this.estimatedDays = estimatedDays;
    }

    public Boolean getIsBottleneck() {
        return isBottleneck;
    }

    public void setIsBottleneck(Boolean isBottleneck) {
        this.isBottleneck = isBottleneck;
    }

    public BigDecimal getUtilizationRate() {
        return utilizationRate;
    }

    public void setUtilizationRate(BigDecimal utilizationRate) {
        this.utilizationRate = utilizationRate;
    }
}
