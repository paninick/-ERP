package com.ruoyi.erp.service;

import com.ruoyi.erp.domain.ProduceDefect;

import java.util.List;

/**
 * 次品记录Service接口
 *
 * @author zhangmingjian
 */
public interface IProduceDefectService {
    /**
     * 查询次品记录
     *
     * @param id 次品记录ID
     * @return 次品记录
     */
    public ProduceDefect selectProduceDefectById(Long id);

    /**
     * 查询次品记录列表
     *
     * @param produceDefect 次品记录
     * @return 次品记录集合
     */
    public List<ProduceDefect> selectProduceDefectList(ProduceDefect produceDefect);

    /**
     * 新增次品记录
     *
     * @param produceDefect 次品记录
     * @return 结果
     */
    public int insertProduceDefect(ProduceDefect produceDefect);

    /**
     * 修改次品记录
     *
     * @param produceDefect 次品记录
     * @return 结果
     */
    public int updateProduceDefect(ProduceDefect produceDefect);

    /**
     * 批量删除次品记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteProduceDefectByIds(Long[] ids);

    /**
     * 删除次品记录信息
     *
     * @param id 次品记录ID
     * @return 结果
     */
    public int deleteProduceDefectById(Long id);

    /**
     * 根据工票ID统计次品数量
     *
     * @param jobId 工票ID
     * @return 次品数量
     */
    public Integer sumDefectQtyByJobId(Long jobId);
}
