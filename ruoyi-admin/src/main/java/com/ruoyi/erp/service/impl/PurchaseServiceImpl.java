package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.PurchaseMapper;
import com.ruoyi.erp.domain.Purchase;
import com.ruoyi.erp.service.IPurchaseService;
import com.ruoyi.erp.utils.BillNoGenerator;

/**
 * 采购单Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@Service
public class PurchaseServiceImpl implements IPurchaseService {
    @Autowired
    private PurchaseMapper purchaseMapper;

    @Autowired
    private BillNoGenerator billNoGenerator;

    /**
     * 查询采购单
     *
     * @param id 采购单主键
     * @return 采购单
     */
    @Override
    public Purchase selectPurchaseById(Long id) {
        return purchaseMapper.selectPurchaseById(id);
    }

    /**
     * 查询采购单列表
     *
     * @param purchase 采购单
     * @return 采购单
     */
    @Override
    public List<Purchase> selectPurchaseList(Purchase purchase) {
        return purchaseMapper.selectPurchaseList(purchase);
    }

    /**
     * 新增采购单
     *
     * @param purchase 采购单
     * @return 结果
     */
    @Override
    public int insertPurchase(Purchase purchase) {
        if (purchase.getSn() == null || purchase.getSn().isEmpty()) {
            purchase.setSn(billNoGenerator.generate("PO"));
        }
        purchase.setCreateBy(SecurityUtils.getUserId().toString());
        purchase.setCreateTime(DateUtils.getNowDate());
        return purchaseMapper.insertPurchase(purchase);
    }

    /**
     * 修改采购单
     *
     * @param purchase 采购单
     * @return 结果
     */
    @Override
    public int updatePurchase(Purchase purchase) {
        purchase.setUpdateTime(DateUtils.getNowDate());
        return purchaseMapper.updatePurchase(purchase);
    }

    /**
     * 批量删除采购单
     *
     * @param ids 需要删除的采购单主键
     * @return 结果
     */
    @Override
    public int deletePurchaseByIds(Long[] ids) {
        return purchaseMapper.deletePurchaseByIds(ids);
    }

    /**
     * 删除采购单信息
     *
     * @param id 采购单主键
     * @return 结果
     */
    @Override
    public int deletePurchaseById(Long id) {
        return purchaseMapper.deletePurchaseById(id);
    }

    /**
     * 批量新增采购单
     *
     * @param list 采购单
     * @return 结果
     */
    @Override
    public int insertPurchaseBatch(List<Purchase> list) {
        return purchaseMapper.insertPurchaseBatch(list);
    }
}
