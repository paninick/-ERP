package com.ruoyi.wx.service;

import java.util.List;
import com.ruoyi.wx.domain.WxUser;

/**
 * 微信用户Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
public interface IWxUserService {
    /**
     * 查询微信用户
     *
     * @param id 微信用户主键
     * @return 微信用户
     */
    WxUser selectWxUserById(Long id);

    /**
     * 查询微信用户列表
     *
     * @param wxUser 微信用户
     * @return 微信用户集合
     */
    List<WxUser> selectWxUserList(WxUser wxUser);

    /**
     * 新增微信用户
     *
     * @param wxUser 微信用户
     * @return 结果
     */
    int insertWxUser(WxUser wxUser);

    /**
     * 修改微信用户
     *
     * @param wxUser 微信用户
     * @return 结果
     */
    int updateWxUser(WxUser wxUser);

    /**
     * 批量删除微信用户
     *
     * @param ids 需要删除的微信用户主键集合
     * @return 结果
     */
    int deleteWxUserByIds(Long[] ids);

    /**
     * 删除微信用户信息
     *
     * @param id 微信用户主键
     * @return 结果
     */
    int deleteWxUserById(Long id);

    /**
     * 新增微信用户批量
     *
     * @param list 微信用户
     * @return 结果
     */
    int insertWxUserBatch(List<WxUser> list);
}
