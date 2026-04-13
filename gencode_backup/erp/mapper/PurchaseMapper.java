package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.Purchase;

/**
 * 采购单Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface PurchaseMapper {
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
     * 删除采购单
     *
     * @param id 采购单主键
     * @return 结果
     */
    int deletePurchaseById(Long id);

    /**
     * 批量删除采购单
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deletePurchaseByIds(Long[] ids);

    /**
     * 批量新增采购单批量
     *
     * @param list 采购单
     * @return 结果
     */
    int insertPurchaseBatch(List<Purchase> list);
}
