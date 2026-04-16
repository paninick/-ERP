package com.ruoyi.erp.domain.vo;

import java.math.BigDecimal;

/**
 * 员工产量排名VO
 *
 * @author zhangmingjian
 */
public class EmployeeRankVO {

    /**
     * 员工ID
     */
    private Long employeeId;

    /**
     * 员工姓名
     */
    private String employeeName;

    /**
     * 本月产量（件）
     */
    private Integer monthlyQuantity;

    /**
     * 本月计件工资
     */
    private BigDecimal monthlyWage;

    /**
     * 日均产量
     */
    private BigDecimal dailyAverage;

    /**
     * 排名
     */
    private Integer rank;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getMonthlyQuantity() {
        return monthlyQuantity;
    }

    public void setMonthlyQuantity(Integer monthlyQuantity) {
        this.monthlyQuantity = monthlyQuantity;
    }

    public BigDecimal getMonthlyWage() {
        return monthlyWage;
    }

    public void setMonthlyWage(BigDecimal monthlyWage) {
        this.monthlyWage = monthlyWage;
    }

    public BigDecimal getDailyAverage() {
        return dailyAverage;
    }

    public void setDailyAverage(BigDecimal dailyAverage) {
        this.dailyAverage = dailyAverage;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
