package com.ruoyi.wx.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 模板消息对象 t_wx_template_message
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
public class TemplateMessage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 用户编号 */
    private Long wxUserId;

    /** 用户服务号openid */
    @Excel(name = "用户服务号openid")
    private String openId;

    /** 服务号消息模板id */
    @Excel(name = "服务号消息模板id")
    private String templateId;

    /** 链接地址 */
    private String url;

    /** 小程序appid */
    private String minaAppid;

    /** 小程序路径 */
    private String pagePath;

    /** 模板消息参数 */
    private String params;

    /** 发送状态1成功，2失败 */
    @Excel(name = "发送状态1成功，2失败")
    private String status;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setWxUserId(Long wxUserId) {
        this.wxUserId = wxUserId;
    }

    public Long getWxUserId() {
        return wxUserId;
    }
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOpenId() {
        return openId;
    }
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateId() {
        return templateId;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
    public void setMinaAppid(String minaAppid) {
        this.minaAppid = minaAppid;
    }

    public String getMinaAppid() {
        return minaAppid;
    }
    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public String getPagePath() {
        return pagePath;
    }
    public void setParams(String params) {
        this.params = params;
    }

    public String getParams() {
        return params;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("wxUserId", getWxUserId())
            .append("openId", getOpenId())
            .append("templateId", getTemplateId())
            .append("url", getUrl())
            .append("minaAppid", getMinaAppid())
            .append("pagePath", getPagePath())
            .append("params", getParams())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
