package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.StockLog;

/**
 * 出入库流水Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
public interface StockLogMapper {
    /**
     * 查询出入库流水
     *
     * @param id 出入库流水主键
     * @return 出入库流水
     */
    StockLog selectStockLogById(Long id);

    /**
     * 查询出入库流水列表
     *
     * @param stockLog 出入库流水
     * @return 出入库流水集合
     */
    List<StockLog> selectStockLogList(StockLog stockLog);

    /**
     * 新增出入库流水
     *
     * @param stockLog 出入库流水
     * @return 结果
     */
    int insertStockLog(StockLog stockLog);

    /**
     * 修改出入库流水
     *
     * @param stockLog 出入库流水
     * @return 结果
     */
    int updateStockLog(StockLog stockLog);

    /**
     * 删除出入库流水
     *
     * @param id 出入库流水主键
     * @return 结果
     */
    int deleteStockLogById(Long id);

    /**
     * 批量删除出入库流水
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteStockLogByIds(Long[] ids);

    /**
     * 批量新增出入库流水批量
     *
     * @param list 出入库流水
     * @return 结果
     */
    int insertStockLogBatch(List<StockLog> list);
}
