package com.ruoyi.erp.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 业务异常池对象 t_erp_biz_abnormal_pool
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public class BizAbnormalPool extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 异常ID */
    private Long id;

    /** 关联业务类型 */
    @Excel(name = "关联业务类型")
    private String bizType;

    /** 关联业务ID */
    @Excel(name = "关联业务ID")
    private Long bizId;

    /** 异常编码 */
    @Excel(name = "异常编码")
    private String abnormalCode;

    /** 异常标题 */
    @Excel(name = "异常标题")
    private String abnormalTitle;

    /** 异常描述 */
    @Excel(name = "异常描述")
    private String abnormalDesc;

    /** 异常等级 1低 2中 3高 */
    @Excel(name = "异常等级", readConverterExp = "1=低,2=中,3=高")
    private Integer abnormalLevel;

    /** 状态 0待处理 1处理中 2已处理 3已关闭 */
    @Excel(name = "状态", readConverterExp = "0=待处理,1=处理中,2=已处理,3=已关闭")
    private String status;

    /** 处理人ID */
    private Long handleById;

    /** 处理人姓名 */
    private String handleByName;

    /** 处理时间 */
    @Excel(name = "处理时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date handleTime;

    /** 处理结果描述 */
    @Excel(name = "处理结果描述")
    private String handleResult;

    /** 是否锁定业务 0不锁定 1锁定 */
    @Excel(name = "是否锁定", readConverterExp = "0=不锁定,1=锁定")
    private String lockBiz;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public String getAbnormalCode() {
        return abnormalCode;
    }

    public void setAbnormalCode(String abnormalCode) {
        this.abnormalCode = abnormalCode;
    }

    public String getAbnormalTitle() {
        return abnormalTitle;
    }

    public void setAbnormalTitle(String abnormalTitle) {
        this.abnormalTitle = abnormalTitle;
    }

    public String getAbnormalDesc() {
        return abnormalDesc;
    }

    public void setAbnormalDesc(String abnormalDesc) {
        this.abnormalDesc = abnormalDesc;
    }

    public Integer getAbnormalLevel() {
        return abnormalLevel;
    }

    public void setAbnormalLevel(Integer abnormalLevel) {
        this.abnormalLevel = abnormalLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getHandleById() {
        return handleById;
    }

    public void setHandleById(Long handleById) {
        this.handleById = handleById;
    }

    public String getHandleByName() {
        return handleByName;
    }

    public void setHandleByName(String handleByName) {
        this.handleByName = handleByName;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    public String getLockBiz() {
        return lockBiz;
    }

    public void setLockBiz(String lockBiz) {
        this.lockBiz = lockBiz;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("bizType", getBizType())
            .append("bizId", getBizId())
            .append("abnormalCode", getAbnormalCode())
            .append("abnormalTitle", getAbnormalTitle())
            .append("abnormalDesc", getAbnormalDesc())
            .append("abnormalLevel", getAbnormalLevel())
            .append("status", getStatus())
            .append("handleById", getHandleById())
            .append("handleByName", getHandleByName())
            .append("handleTime", getHandleTime())
            .append("handleResult", getHandleResult())
            .append("lockBiz", getLockBiz())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
