package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.ProcessPrice;

/**
 * 工序工价Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public interface IProcessPriceService {
    /**
     * 查询工序工价
     *
     * @param id 工序工价主键
     * @return 工序工价
     */
    public ProcessPrice selectProcessPriceById(Long id);

    /**
     * 查询工序工价列表
     *
     * @param processPrice 工序工价
     * @return 工序工价集合
     */
    public List<ProcessPrice> selectProcessPriceList(ProcessPrice processPrice);

    /**
     * 新增工序工价
     *
     * @param processPrice 工序工价
     * @return 结果
     */
    public int insertProcessPrice(ProcessPrice processPrice);

    /**
     * 修改工序工价
     *
     * @param processPrice 工序工价
     * @return 结果
     */
    public int updateProcessPrice(ProcessPrice processPrice);

    /**
     * 批量删除工序工价
     *
     * @param ids 需要删除的工序工价主键集合
     * @return 结果
     */
    public int deleteProcessPriceByIds(Long[] ids);

    /**
     * 删除工序工价信息
     *
     * @param id 工序工价主键
     * @return 结果
     */
    public int deleteProcessPriceById(Long id);

    /**
     * 查询当前生效工价
     *
     * @param processId 工序ID
     * @param employeeId 员工ID
     * @return 当前生效工价
     */
    public ProcessPrice selectCurrentPrice(Long processId, Long employeeId);
}
