package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.StockIn;

/**
 * 入库单Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
public interface StockInMapper {
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
     * 删除入库单
     *
     * @param id 入库单主键
     * @return 结果
     */
    int deleteStockInById(Long id);

    /**
     * 批量删除入库单
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteStockInByIds(Long[] ids);

    /**
     * 批量新增入库单批量
     *
     * @param list 入库单
     * @return 结果
     */
    int insertStockInBatch(List<StockIn> list);
}
