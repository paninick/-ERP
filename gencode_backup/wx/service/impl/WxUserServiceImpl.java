package com.ruoyi.wx.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wx.mapper.WxUserMapper;
import com.ruoyi.wx.domain.WxUser;
import com.ruoyi.wx.service.IWxUserService;

/**
 * 微信用户Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@Service
public class WxUserServiceImpl implements IWxUserService {
    @Autowired
    private WxUserMapper wxUserMapper;

    /**
     * 查询微信用户
     *
     * @param id 微信用户主键
     * @return 微信用户
     */
    @Override
    public WxUser selectWxUserById(Long id) {
        return wxUserMapper.selectWxUserById(id);
    }

    /**
     * 查询微信用户列表
     *
     * @param wxUser 微信用户
     * @return 微信用户
     */
    @Override
    public List<WxUser> selectWxUserList(WxUser wxUser) {
        return wxUserMapper.selectWxUserList(wxUser);
    }

    /**
     * 新增微信用户
     *
     * @param wxUser 微信用户
     * @return 结果
     */
    @Override
    public int insertWxUser(WxUser wxUser) {
        wxUser.setCreateBy(SecurityUtils.getUserId().toString());
        wxUser.setCreateTime(DateUtils.getNowDate());
        return wxUserMapper.insertWxUser(wxUser);
    }

    /**
     * 修改微信用户
     *
     * @param wxUser 微信用户
     * @return 结果
     */
    @Override
    public int updateWxUser(WxUser wxUser) {
        wxUser.setUpdateTime(DateUtils.getNowDate());
        return wxUserMapper.updateWxUser(wxUser);
    }

    /**
     * 批量删除微信用户
     *
     * @param ids 需要删除的微信用户主键
     * @return 结果
     */
    @Override
    public int deleteWxUserByIds(Long[] ids) {
        return wxUserMapper.deleteWxUserByIds(ids);
    }

    /**
     * 删除微信用户信息
     *
     * @param id 微信用户主键
     * @return 结果
     */
    @Override
    public int deleteWxUserById(Long id) {
        return wxUserMapper.deleteWxUserById(id);
    }

    /**
     * 批量新增微信用户
     *
     * @param list 微信用户
     * @return 结果
     */
    @Override
    public int insertWxUserBatch(List<WxUser> list) {
        return wxUserMapper.insertWxUserBatch(list);
    }
}
