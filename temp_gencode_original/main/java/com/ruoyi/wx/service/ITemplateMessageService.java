package com.ruoyi.wx.service;

import java.util.List;
import com.ruoyi.wx.domain.TemplateMessage;

/**
 * 模板消息Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
public interface ITemplateMessageService {
    /**
     * 查询模板消息
     *
     * @param id 模板消息主键
     * @return 模板消息
     */
    TemplateMessage selectTemplateMessageById(Long id);

    /**
     * 查询模板消息列表
     *
     * @param templateMessage 模板消息
     * @return 模板消息集合
     */
    List<TemplateMessage> selectTemplateMessageList(TemplateMessage templateMessage);

    /**
     * 新增模板消息
     *
     * @param templateMessage 模板消息
     * @return 结果
     */
    int insertTemplateMessage(TemplateMessage templateMessage);

    /**
     * 修改模板消息
     *
     * @param templateMessage 模板消息
     * @return 结果
     */
    int updateTemplateMessage(TemplateMessage templateMessage);

    /**
     * 批量删除模板消息
     *
     * @param ids 需要删除的模板消息主键集合
     * @return 结果
     */
    int deleteTemplateMessageByIds(Long[] ids);

    /**
     * 删除模板消息信息
     *
     * @param id 模板消息主键
     * @return 结果
     */
    int deleteTemplateMessageById(Long id);

    /**
     * 新增模板消息批量
     *
     * @param list 模板消息
     * @return 结果
     */
    int insertTemplateMessageBatch(List<TemplateMessage> list);
}
