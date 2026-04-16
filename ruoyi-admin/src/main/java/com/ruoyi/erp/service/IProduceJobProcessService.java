package com.ruoyi.erp.service;

import com.ruoyi.erp.domain.ProduceJobProcess;

import java.util.List;

/**
 * 工序流转记录Service接口
 *
 * @author ruoyi
 */
public interface IProduceJobProcessService {
    /**
     * 查询工序流转记录
     *
     * @param id 工序流转记录ID
     * @return 工序流转记录
     */
    public ProduceJobProcess selectProduceJobProcessById(Long id);

    /**
     * 查询工序流转记录列表
     *
     * @param produceJobProcess 工序流转记录
     * @return 工序流转记录集合
     */
    public List<ProduceJobProcess> selectProduceJobProcessList(ProduceJobProcess produceJobProcess);

    /**
     * 新增工序流转记录
     *
     * @param produceJobProcess 工序流转记录
     * @return 结果
     */
    public int insertProduceJobProcess(ProduceJobProcess produceJobProcess);

    /**
     * 修改工序流转记录
     *
     * @param produceJobProcess 工序流转记录
     * @return 结果
     */
    public int updateProduceJobProcess(ProduceJobProcess produceJobProcess);

    /**
     * 批量删除工序流转记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteProduceJobProcessByIds(Long[] ids);

    /**
     * 删除工序流转记录信息
     *
     * @param id 工序流转记录ID
     * @return 结果
     */
    public int deleteProduceJobProcessById(Long id);

    /**
     * 根据工票ID查询工序流转记录列表
     *
     * @param jobId 工票ID
     * @return 工序流转记录列表
     */
    public List<ProduceJobProcess> selectProduceJobProcessByJobId(Long jobId);

    /**
     * 根据工票ID查询当前工序
     *
     * @param jobId 工票ID
     * @return 当前工序
     */
    public ProduceJobProcess selectCurrentProcessByJobId(Long jobId);
}
