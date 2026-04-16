package com.ruoyi.erp.service.impl;

import com.ruoyi.erp.domain.ProduceJobProcess;
import com.ruoyi.erp.mapper.ProduceJobProcessMapper;
import com.ruoyi.erp.service.IProduceJobProcessService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 工序流转记录Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class ProduceJobProcessServiceImpl implements IProduceJobProcessService {

    @Autowired
    private ProduceJobProcessMapper produceJobProcessMapper;

    /**
     * 查询工序流转记录
     *
     * @param id 工序流转记录ID
     * @return 工序流转记录
     */
    @Override
    public ProduceJobProcess selectProduceJobProcessById(Long id) {
        return produceJobProcessMapper.selectProduceJobProcessById(id);
    }

    /**
     * 查询工序流转记录列表
     *
     * @param produceJobProcess 工序流转记录
     * @return 工序流转记录
     */
    @Override
    public List<ProduceJobProcess> selectProduceJobProcessList(ProduceJobProcess produceJobProcess) {
        return produceJobProcessMapper.selectProduceJobProcessList(produceJobProcess);
    }

    /**
     * 新增工序流转记录
     *
     * @param produceJobProcess 工序流转记录
     * @return 结果
     */
    @Override
    public int insertProduceJobProcess(ProduceJobProcess produceJobProcess) {
        produceJobProcess.setCreateBy(SecurityUtils.getUsername());
        produceJobProcess.setCreateTime(DateUtils.getNowDate());
        return produceJobProcessMapper.insertProduceJobProcess(produceJobProcess);
    }

    /**
     * 修改工序流转记录
     *
     * @param produceJobProcess 工序流转记录
     * @return 结果
     */
    @Override
    public int updateProduceJobProcess(ProduceJobProcess produceJobProcess) {
        return produceJobProcessMapper.updateProduceJobProcess(produceJobProcess);
    }

    /**
     * 批量删除工序流转记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteProduceJobProcessByIds(Long[] ids) {
        return produceJobProcessMapper.deleteProduceJobProcessByIds(ids);
    }

    /**
     * 删除工序流转记录信息
     *
     * @param id 工序流转记录ID
     * @return 结果
     */
    @Override
    public int deleteProduceJobProcessById(Long id) {
        return produceJobProcessMapper.deleteProduceJobProcessById(id);
    }

    @Override
    public List<ProduceJobProcess> selectProduceJobProcessByJobId(Long jobId) {
        return produceJobProcessMapper.selectProduceJobProcessByJobId(jobId);
    }

    @Override
    public ProduceJobProcess selectCurrentProcessByJobId(Long jobId) {
        return produceJobProcessMapper.selectCurrentProcessByJobId(jobId);
    }
}
