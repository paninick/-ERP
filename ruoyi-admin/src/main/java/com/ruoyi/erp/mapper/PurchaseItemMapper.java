package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.PurchaseItem;

/**
 * 采购明细Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface PurchaseItemMapper {
    /**
     * 查询采购明细
     *
     * @param id 采购明细主键
     * @return 采购明细
     */
    PurchaseItem selectPurchaseItemById(Long id);

    /**
     * 查询采购明细列表
     *
     * @param purchaseItem 采购明细
     * @return 采购明细集合
     */
    List<PurchaseItem> selectPurchaseItemList(PurchaseItem purchaseItem);

    /**
     * 新增采购明细
     *
     * @param purchaseItem 采购明细
     * @return 结果
     */
    int insertPurchaseItem(PurchaseItem purchaseItem);

    /**
     * 修改采购明细
     *
     * @param purchaseItem 采购明细
     * @return 结果
     */
    int updatePurchaseItem(PurchaseItem purchaseItem);

    /**
     * 删除采购明细
     *
     * @param id 采购明细主键
     * @return 结果
     */
    int deletePurchaseItemById(Long id);

    /**
     * 批量删除采购明细
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deletePurchaseItemByIds(Long[] ids);

    /**
     * 批量新增采购明细批量
     *
     * @param list 采购明细
     * @return 结果
     */
    int insertPurchaseItemBatch(List<PurchaseItem> list);
}
