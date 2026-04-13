package com.ruoyi.wx.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 微信用户对象 t_wx_user
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public class WxUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 微信union_id */
    @Excel(name = "微信union_id")
    private String unionId;

    /** 微信小程序openid */
    @Excel(name = "微信小程序openid")
    private String minaOpenid;

    /** 公众号openid */
    @Excel(name = "公众号openid")
    private String mpOpenid;

    /** 头像 */
    @Excel(name = "头像")
    private String headImage;

    /** 昵称 */
    @Excel(name = "昵称")
    private String nickname;

    /** 性别 */
    @Excel(name = "性别")
    private String gender;

    /** 手机 */
    @Excel(name = "手机")
    private String mobile;

    /** 1已关注，0未关注 */
    @Excel(name = "1已关注，0未关注")
    private String isSubscribe;

    /** 关注时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "关注时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date subscribeTime;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getUnionId() {
        return unionId;
    }
    public void setMinaOpenid(String minaOpenid) {
        this.minaOpenid = minaOpenid;
    }

    public String getMinaOpenid() {
        return minaOpenid;
    }
    public void setMpOpenid(String mpOpenid) {
        this.mpOpenid = mpOpenid;
    }

    public String getMpOpenid() {
        return mpOpenid;
    }
    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getHeadImage() {
        return headImage;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }
    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }
    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public Date getSubscribeTime() {
        return subscribeTime;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("unionId", getUnionId())
            .append("minaOpenid", getMinaOpenid())
            .append("mpOpenid", getMpOpenid())
            .append("headImage", getHeadImage())
            .append("nickname", getNickname())
            .append("gender", getGender())
            .append("mobile", getMobile())
            .append("isSubscribe", getIsSubscribe())
            .append("subscribeTime", getSubscribeTime())
            .append("name", getName())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
