package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.CorpContactsMapper;
import com.ruoyi.erp.domain.CorpContacts;
import com.ruoyi.erp.service.ICorpContactsService;

/**
 * 公司联系人Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@Service
public class CorpContactsServiceImpl implements ICorpContactsService {
    @Autowired
    private CorpContactsMapper corpContactsMapper;

    /**
     * 查询公司联系人
     *
     * @param id 公司联系人主键
     * @return 公司联系人
     */
    @Override
    public CorpContacts selectCorpContactsById(Long id) {
        return corpContactsMapper.selectCorpContactsById(id);
    }

    /**
     * 查询公司联系人列表
     *
     * @param corpContacts 公司联系人
     * @return 公司联系人
     */
    @Override
    public List<CorpContacts> selectCorpContactsList(CorpContacts corpContacts) {
        return corpContactsMapper.selectCorpContactsList(corpContacts);
    }

    /**
     * 新增公司联系人
     *
     * @param corpContacts 公司联系人
     * @return 结果
     */
    @Override
    public int insertCorpContacts(CorpContacts corpContacts) {
        corpContacts.setCreateBy(SecurityUtils.getUserId().toString());
        corpContacts.setCreateTime(DateUtils.getNowDate());
        return corpContactsMapper.insertCorpContacts(corpContacts);
    }

    /**
     * 修改公司联系人
     *
     * @param corpContacts 公司联系人
     * @return 结果
     */
    @Override
    public int updateCorpContacts(CorpContacts corpContacts) {
        corpContacts.setUpdateTime(DateUtils.getNowDate());
        return corpContactsMapper.updateCorpContacts(corpContacts);
    }

    /**
     * 批量删除公司联系人
     *
     * @param ids 需要删除的公司联系人主键
     * @return 结果
     */
    @Override
    public int deleteCorpContactsByIds(Long[] ids) {
        return corpContactsMapper.deleteCorpContactsByIds(ids);
    }

    /**
     * 删除公司联系人信息
     *
     * @param id 公司联系人主键
     * @return 结果
     */
    @Override
    public int deleteCorpContactsById(Long id) {
        return corpContactsMapper.deleteCorpContactsById(id);
    }

    /**
     * 批量新增公司联系人
     *
     * @param list 公司联系人
     * @return 结果
     */
    @Override
    public int insertCorpContactsBatch(List<CorpContacts> list) {
        return corpContactsMapper.insertCorpContactsBatch(list);
    }
}
