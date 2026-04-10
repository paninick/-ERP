package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.CorpContacts;

/**
 * 公司联系人Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
public interface ICorpContactsService {
    /**
     * 查询公司联系人
     *
     * @param id 公司联系人主键
     * @return 公司联系人
     */
    CorpContacts selectCorpContactsById(Long id);

    /**
     * 查询公司联系人列表
     *
     * @param corpContacts 公司联系人
     * @return 公司联系人集合
     */
    List<CorpContacts> selectCorpContactsList(CorpContacts corpContacts);

    /**
     * 新增公司联系人
     *
     * @param corpContacts 公司联系人
     * @return 结果
     */
    int insertCorpContacts(CorpContacts corpContacts);

    /**
     * 修改公司联系人
     *
     * @param corpContacts 公司联系人
     * @return 结果
     */
    int updateCorpContacts(CorpContacts corpContacts);

    /**
     * 批量删除公司联系人
     *
     * @param ids 需要删除的公司联系人主键集合
     * @return 结果
     */
    int deleteCorpContactsByIds(Long[] ids);

    /**
     * 删除公司联系人信息
     *
     * @param id 公司联系人主键
     * @return 结果
     */
    int deleteCorpContactsById(Long id);

    /**
     * 新增公司联系人批量
     *
     * @param list 公司联系人
     * @return 结果
     */
    int insertCorpContactsBatch(List<CorpContacts> list);
}
