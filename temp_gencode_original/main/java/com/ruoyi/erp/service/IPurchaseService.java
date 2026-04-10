package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.Purchase;

/**
 * 采购单Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
public interface IPurchaseService {
    /**
     * 查询采购单
     *
     * @param id 采购单主键
     * @return 采购单
     */
    Purchase selectPurchaseById(Long id);

    /**
     * 查询采购单列表
     *
     * @param purchase 采购单
     * @return 采购单集合
     */
    List<Purchase> selectPurchaseList(Purchase purchase);

    /**
     * 新增采购单
     *
     * @param purchase 采购单
     * @return 结果
     */
    int insertPurchase(Purchase purchase);

    /**
     * 修改采购单
     *
     * @param purchase 采购单
     * @return 结果
     */
    int updatePurchase(Purchase purchase);

    /**
     * 批量删除采购单
     *
     * @param ids 需要删除的采购单主键集合
     * @return 结果
     */
    int deletePurchaseByIds(Long[] ids);

    /**
     * 删除采购单信息
     *
     * @param id 采购单主键
     * @return 结果
     */
    int deletePurchaseById(Long id);

    /**
     * 新增采购单批量
     *
     * @param list 采购单
     * @return 结果
     */
    int insertPurchaseBatch(List<Purchase> list);
}
