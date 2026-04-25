package com.ruoyi.erp.service.impl;

import com.ruoyi.erp.domain.ProduceJobProcess;
import com.ruoyi.erp.mapper.ProduceJobProcessMapper;
import com.ruoyi.erp.service.IProduceJobProcessService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertCustomProcess(ProduceJobProcess produceJobProcess) {
        if (produceJobProcess.getJobId() == null || produceJobProcess.getProcessId() == null) {
            throw new ServiceException("jobId and processId are required");
        }

        Integer targetSeq = produceJobProcess.getProcessSeq();
        if (targetSeq == null || targetSeq <= 0) {
            Integer maxSeq = produceJobProcessMapper.selectMaxProcessSeqByJobId(produceJobProcess.getJobId());
            targetSeq = (maxSeq == null ? 0 : maxSeq) + 10;
        } else {
            ProduceJobProcess shiftParam = new ProduceJobProcess();
            shiftParam.setJobId(produceJobProcess.getJobId());
            shiftParam.setProcessSeq(targetSeq);
            produceJobProcessMapper.shiftProcessSeqAfter(shiftParam);
        }

        produceJobProcess.setProcessSeq(targetSeq);
        produceJobProcess.setInQty(defaultNumber(produceJobProcess.getInQty()));
        produceJobProcess.setOutQty(defaultNumber(produceJobProcess.getOutQty()));
        produceJobProcess.setDefectQty(defaultNumber(produceJobProcess.getDefectQty()));
        produceJobProcess.setLossQty(defaultNumber(produceJobProcess.getLossQty()));
        produceJobProcess.setLossExceed(defaultFlag(produceJobProcess.getLossExceed()));
        produceJobProcess.setIsOutsource(defaultFlag(produceJobProcess.getIsOutsource()));
        produceJobProcess.setIsInserted("1");
        produceJobProcess.setIsSkipped("0");
        produceJobProcess.setIsRework(defaultFlag(produceJobProcess.getIsRework()));
        produceJobProcess.setProcessStatus("PENDING");
        produceJobProcess.setCreateBy(SecurityUtils.getUsername());
        produceJobProcess.setCreateTime(DateUtils.getNowDate());
        return produceJobProcessMapper.insertProduceJobProcess(produceJobProcess);
    }

    @Override
    public int skipProcess(ProduceJobProcess produceJobProcess) {
        if (produceJobProcess.getId() == null) {
            throw new ServiceException("process record id is required");
        }
        produceJobProcess.setIsSkipped("1");
        produceJobProcess.setProcessStatus("PASS");
        return produceJobProcessMapper.updateProduceJobProcess(produceJobProcess);
    }

    @Override
    public int insertReworkProcess(ProduceJobProcess produceJobProcess) {
        if (produceJobProcess.getReworkSourceProcessId() == null) {
            throw new ServiceException("reworkSourceProcessId is required");
        }
        produceJobProcess.setIsRework("1");
        produceJobProcess.setInsertReason(
            produceJobProcess.getInsertReason() == null || produceJobProcess.getInsertReason().isEmpty()
                ? "REWORK"
                : produceJobProcess.getInsertReason()
        );
        return insertCustomProcess(produceJobProcess);
    }

    private Integer defaultNumber(Integer value) {
        return value == null ? 0 : value;
    }

    private String defaultFlag(String value) {
        return value == null || value.isEmpty() ? "0" : value;
    }
}
