package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.StockInItem;

/**
 * 入库明细Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
public interface StockInItemMapper {
    /**
     * 查询入库明细
     *
     * @param id 入库明细主键
     * @return 入库明细
     */
    StockInItem selectStockInItemById(Long id);

    /**
     * 查询入库明细列表
     *
     * @param stockInItem 入库明细
     * @return 入库明细集合
     */
    List<StockInItem> selectStockInItemList(StockInItem stockInItem);

    /**
     * 新增入库明细
     *
     * @param stockInItem 入库明细
     * @return 结果
     */
    int insertStockInItem(StockInItem stockInItem);

    /**
     * 修改入库明细
     *
     * @param stockInItem 入库明细
     * @return 结果
     */
    int updateStockInItem(StockInItem stockInItem);

    /**
     * 删除入库明细
     *
     * @param id 入库明细主键
     * @return 结果
     */
    int deleteStockInItemById(Long id);

    /**
     * 批量删除入库明细
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteStockInItemByIds(Long[] ids);

    /**
     * 批量新增入库明细批量
     *
     * @param list 入库明细
     * @return 结果
     */
    int insertStockInItemBatch(List<StockInItem> list);
}
