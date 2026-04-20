package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.StockIn;

/**
 * 入库单Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface IStockInService {
    /**
     * 查询入库单
     *
     * @param id 入库单主键
     * @return 入库单
     */
    StockIn selectStockInById(Long id);

    /**
     * 查询入库单列表
     *
     * @param stockIn 入库单
     * @return 入库单集合
     */
    List<StockIn> selectStockInList(StockIn stockIn);

    /**
     * 新增入库单
     *
     * @param stockIn 入库单
     * @return 结果
     */
    int insertStockIn(StockIn stockIn);

    /**
     * 修改入库单
     *
     * @param stockIn 入库单
     * @return 结果
     */
    int updateStockIn(StockIn stockIn);

    /**
     * 批量删除入库单
     *
     * @param ids 需要删除的入库单主键集合
     * @return 结果
     */
    int deleteStockInByIds(Long[] ids);

    /**
     * 删除入库单信息
     *
     * @param id 入库单主键
     * @return 结果
     */
    int deleteStockInById(Long id);

    /**
     * 新增入库单批量
     *
     * @param list 入库单
     * @return 结果
     */
    int insertStockInBatch(List<StockIn> list);

    /**
     * 确认入库单
     *
     * @param stockIn 入库单
     * @return 是否成功
     */
    boolean confirm(StockIn stockIn);

    /**
     * 取消确认入库单
     *
     * @param stockIn 入库单
     * @return 是否成功
     */
    boolean cancelConfirm(StockIn stockIn);
}
