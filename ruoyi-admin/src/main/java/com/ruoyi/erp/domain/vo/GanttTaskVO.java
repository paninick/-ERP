package com.ruoyi.erp.domain.vo;

import java.util.Date;

/**
 * 甘特图任务VO
 *
 * @author zhangmingjian
 */
public class GanttTaskVO {

    /**
     * 任务ID
     */
    private Long id;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 开始日期
     */
    private Date start;

    /**
     * 结束日期
     */
    private Date end;

    /**
     * 进度 0-100
     */
    private Integer progress;

    /**
     * 负责人
     */
    private String owner;

    /**
     * 生产状态
     */
    private String status;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 款式品类
     */
    private String styleCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStyleCategory() {
        return styleCategory;
    }

    public void setStyleCategory(String styleCategory) {
        this.styleCategory = styleCategory;
    }
}
