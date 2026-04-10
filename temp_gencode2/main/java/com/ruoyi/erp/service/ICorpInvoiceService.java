package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.CorpInvoice;

/**
 * 公司开票信息Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
public interface ICorpInvoiceService {
    /**
     * 查询公司开票信息
     *
     * @param id 公司开票信息主键
     * @return 公司开票信息
     */
    CorpInvoice selectCorpInvoiceById(Long id);

    /**
     * 查询公司开票信息列表
     *
     * @param corpInvoice 公司开票信息
     * @return 公司开票信息集合
     */
    List<CorpInvoice> selectCorpInvoiceList(CorpInvoice corpInvoice);

    /**
     * 新增公司开票信息
     *
     * @param corpInvoice 公司开票信息
     * @return 结果
     */
    int insertCorpInvoice(CorpInvoice corpInvoice);

    /**
     * 修改公司开票信息
     *
     * @param corpInvoice 公司开票信息
     * @return 结果
     */
    int updateCorpInvoice(CorpInvoice corpInvoice);

    /**
     * 批量删除公司开票信息
     *
     * @param ids 需要删除的公司开票信息主键集合
     * @return 结果
     */
    int deleteCorpInvoiceByIds(Long[] ids);

    /**
     * 删除公司开票信息信息
     *
     * @param id 公司开票信息主键
     * @return 结果
     */
    int deleteCorpInvoiceById(Long id);

    /**
     * 新增公司开票信息批量
     *
     * @param list 公司开票信息
     * @return 结果
     */
    int insertCorpInvoiceBatch(List<CorpInvoice> list);
}
