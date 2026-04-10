package com.ruoyi.wx.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wx.mapper.TemplateMessageMapper;
import com.ruoyi.wx.domain.TemplateMessage;
import com.ruoyi.wx.service.ITemplateMessageService;

/**
 * 模板消息Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
@RequiredArgsConstructor
@Service
public class TemplateMessageServiceImpl implements ITemplateMessageService {
    private final TemplateMessageMapper templateMessageMapper;

    /**
     * 查询模板消息
     *
     * @param id 模板消息主键
     * @return 模板消息
     */
    @Override
    public TemplateMessage selectTemplateMessageById(Long id) {
        return templateMessageMapper.selectTemplateMessageById(id);
    }

    /**
     * 查询模板消息列表
     *
     * @param templateMessage 模板消息
     * @return 模板消息
     */
    @Override
    public List<TemplateMessage> selectTemplateMessageList(TemplateMessage templateMessage) {
        return templateMessageMapper.selectTemplateMessageList(templateMessage);
    }

    /**
     * 新增模板消息
     *
     * @param templateMessage 模板消息
     * @return 结果
     */
    @Override
    public int insertTemplateMessage(TemplateMessage templateMessage) {
        templateMessage.setCreateBy(SecurityUtils.getUserIdStr());
        templateMessage.setCreateTime(DateUtils.getNowDate());
        return templateMessageMapper.insertTemplateMessage(templateMessage);
    }

    /**
     * 修改模板消息
     *
     * @param templateMessage 模板消息
     * @return 结果
     */
    @Override
    public int updateTemplateMessage(TemplateMessage templateMessage) {
        templateMessage.setUpdateTime(DateUtils.getNowDate());
        return templateMessageMapper.updateTemplateMessage(templateMessage);
    }

    /**
     * 批量删除模板消息
     *
     * @param ids 需要删除的模板消息主键
     * @return 结果
     */
    @Override
    public int deleteTemplateMessageByIds(Long[] ids) {
        return templateMessageMapper.deleteTemplateMessageByIds(ids);
    }

    /**
     * 删除模板消息信息
     *
     * @param id 模板消息主键
     * @return 结果
     */
    @Override
    public int deleteTemplateMessageById(Long id) {
        return templateMessageMapper.deleteTemplateMessageById(id);
    }

    /**
     * 批量新增模板消息
     *
     * @param list 模板消息
     * @return 结果
     */
    @Override
    public int insertTemplateMessageBatch(List<TemplateMessage> list) {
        return templateMessageMapper.insertTemplateMessageBatch(list);
    }
}
