package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.CorpInvoiceMapper;
import com.ruoyi.erp.domain.CorpInvoice;
import com.ruoyi.erp.service.ICorpInvoiceService;

/**
 * 公司开票信息Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@Service
public class CorpInvoiceServiceImpl implements ICorpInvoiceService {
    @Autowired
    private CorpInvoiceMapper corpInvoiceMapper;

    /**
     * 查询公司开票信息
     *
     * @param id 公司开票信息主键
     * @return 公司开票信息
     */
    @Override
    public CorpInvoice selectCorpInvoiceById(Long id) {
        return corpInvoiceMapper.selectCorpInvoiceById(id);
    }

    /**
     * 查询公司开票信息列表
     *
     * @param corpInvoice 公司开票信息
     * @return 公司开票信息
     */
    @Override
    public List<CorpInvoice> selectCorpInvoiceList(CorpInvoice corpInvoice) {
        return corpInvoiceMapper.selectCorpInvoiceList(corpInvoice);
    }

    /**
     * 新增公司开票信息
     *
     * @param corpInvoice 公司开票信息
     * @return 结果
     */
    @Override
    public int insertCorpInvoice(CorpInvoice corpInvoice) {
        corpInvoice.setCreateBy(SecurityUtils.getUserId().toString());
        corpInvoice.setCreateTime(DateUtils.getNowDate());
        return corpInvoiceMapper.insertCorpInvoice(corpInvoice);
    }

    /**
     * 修改公司开票信息
     *
     * @param corpInvoice 公司开票信息
     * @return 结果
     */
    @Override
    public int updateCorpInvoice(CorpInvoice corpInvoice) {
        corpInvoice.setUpdateTime(DateUtils.getNowDate());
        return corpInvoiceMapper.updateCorpInvoice(corpInvoice);
    }

    /**
     * 批量删除公司开票信息
     *
     * @param ids 需要删除的公司开票信息主键
     * @return 结果
     */
    @Override
    public int deleteCorpInvoiceByIds(Long[] ids) {
        return corpInvoiceMapper.deleteCorpInvoiceByIds(ids);
    }

    /**
     * 删除公司开票信息信息
     *
     * @param id 公司开票信息主键
     * @return 结果
     */
    @Override
    public int deleteCorpInvoiceById(Long id) {
        return corpInvoiceMapper.deleteCorpInvoiceById(id);
    }

    /**
     * 批量新增公司开票信息
     *
     * @param list 公司开票信息
     * @return 结果
     */
    @Override
    public int insertCorpInvoiceBatch(List<CorpInvoice> list) {
        return corpInvoiceMapper.insertCorpInvoiceBatch(list);
    }
}
