package com.ruoyi.wx.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 微信账户对象 t_wx_account
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
public class WxAccount extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** appid */
    @Excel(name = "appid")
    private String appid;

    /** 公众号密钥 */
    @Excel(name = "公众号密钥")
    private String appSecret;

    /** token */
    @Excel(name = "token")
    private String token;

    /** aes_key */
    @Excel(name = "aes_key")
    private String aesKey;

    /** 类型 1小程序 2 公众号 3 开放平台 */
    private Long type;

    /** 加入开放平台 */
    private Long open;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppid() {
        return appid;
    }
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAppSecret() {
        return appSecret;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getAesKey() {
        return aesKey;
    }
    public void setType(Long type) {
        this.type = type;
    }

    public Long getType() {
        return type;
    }
    public void setOpen(Long open) {
        this.open = open;
    }

    public Long getOpen() {
        return open;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("appid", getAppid())
            .append("appSecret", getAppSecret())
            .append("token", getToken())
            .append("aesKey", getAesKey())
            .append("type", getType())
            .append("open", getOpen())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
