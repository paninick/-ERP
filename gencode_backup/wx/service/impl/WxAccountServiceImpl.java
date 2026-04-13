package com.ruoyi.wx.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wx.mapper.WxAccountMapper;
import com.ruoyi.wx.domain.WxAccount;
import com.ruoyi.wx.service.IWxAccountService;

/**
 * 微信账户Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@Service
public class WxAccountServiceImpl implements IWxAccountService {
    @Autowired
    private WxAccountMapper wxAccountMapper;

    /**
     * 查询微信账户
     *
     * @param id 微信账户主键
     * @return 微信账户
     */
    @Override
    public WxAccount selectWxAccountById(Long id) {
        return wxAccountMapper.selectWxAccountById(id);
    }

    /**
     * 查询微信账户列表
     *
     * @param wxAccount 微信账户
     * @return 微信账户
     */
    @Override
    public List<WxAccount> selectWxAccountList(WxAccount wxAccount) {
        return wxAccountMapper.selectWxAccountList(wxAccount);
    }

    /**
     * 新增微信账户
     *
     * @param wxAccount 微信账户
     * @return 结果
     */
    @Override
    public int insertWxAccount(WxAccount wxAccount) {
        wxAccount.setCreateBy(SecurityUtils.getUserId().toString());
        wxAccount.setCreateTime(DateUtils.getNowDate());
        return wxAccountMapper.insertWxAccount(wxAccount);
    }

    /**
     * 修改微信账户
     *
     * @param wxAccount 微信账户
     * @return 结果
     */
    @Override
    public int updateWxAccount(WxAccount wxAccount) {
        wxAccount.setUpdateTime(DateUtils.getNowDate());
        return wxAccountMapper.updateWxAccount(wxAccount);
    }

    /**
     * 批量删除微信账户
     *
     * @param ids 需要删除的微信账户主键
     * @return 结果
     */
    @Override
    public int deleteWxAccountByIds(Long[] ids) {
        return wxAccountMapper.deleteWxAccountByIds(ids);
    }

    /**
     * 删除微信账户信息
     *
     * @param id 微信账户主键
     * @return 结果
     */
    @Override
    public int deleteWxAccountById(Long id) {
        return wxAccountMapper.deleteWxAccountById(id);
    }

    /**
     * 批量新增微信账户
     *
     * @param list 微信账户
     * @return 结果
     */
    @Override
    public int insertWxAccountBatch(List<WxAccount> list) {
        return wxAccountMapper.insertWxAccountBatch(list);
    }
}
