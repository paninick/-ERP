package com.ruoyi.wx.mapper;

import java.util.List;
import com.ruoyi.wx.domain.WxAccount;

/**
 * 微信账户Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface WxAccountMapper {
    /**
     * 查询微信账户
     *
     * @param id 微信账户主键
     * @return 微信账户
     */
    WxAccount selectWxAccountById(Long id);

    /**
     * 查询微信账户列表
     *
     * @param wxAccount 微信账户
     * @return 微信账户集合
     */
    List<WxAccount> selectWxAccountList(WxAccount wxAccount);

    /**
     * 新增微信账户
     *
     * @param wxAccount 微信账户
     * @return 结果
     */
    int insertWxAccount(WxAccount wxAccount);

    /**
     * 修改微信账户
     *
     * @param wxAccount 微信账户
     * @return 结果
     */
    int updateWxAccount(WxAccount wxAccount);

    /**
     * 删除微信账户
     *
     * @param id 微信账户主键
     * @return 结果
     */
    int deleteWxAccountById(Long id);

    /**
     * 批量删除微信账户
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteWxAccountByIds(Long[] ids);

    /**
     * 批量新增微信账户批量
     *
     * @param list 微信账户
     * @return 结果
     */
    int insertWxAccountBatch(List<WxAccount> list);
}
