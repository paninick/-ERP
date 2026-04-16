package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.ProcessLossMatrixMapper;
import com.ruoyi.erp.domain.ProcessLossMatrix;
import com.ruoyi.erp.service.IProcessLossMatrixService;

/**
 * 工序损耗矩阵Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
@Service
public class ProcessLossMatrixServiceImpl implements IProcessLossMatrixService {
    @Autowired
    private ProcessLossMatrixMapper processLossMatrixMapper;

    /**
     * 查询工序损耗矩阵
     *
     * @param id 工序损耗矩阵主键
     * @return 工序损耗矩阵
     */
    @Override
    public ProcessLossMatrix selectProcessLossMatrixById(Long id) {
        return processLossMatrixMapper.selectProcessLossMatrixById(id);
    }

    /**
     * 查询工序损耗矩阵列表
     *
     * @param processLossMatrix 工序损耗矩阵
     * @return 工序损耗矩阵
     */
    @Override
    public List<ProcessLossMatrix> selectProcessLossMatrixList(ProcessLossMatrix processLossMatrix) {
        return processLossMatrixMapper.selectProcessLossMatrixList(processLossMatrix);
    }

    /**
     * 新增工序损耗矩阵
     *
     * @param processLossMatrix 工序损耗矩阵
     * @return 结果
     */
    @Override
    public int insertProcessLossMatrix(ProcessLossMatrix processLossMatrix) {
        processLossMatrix.setCreateBy(SecurityUtils.getUserId().toString());
        processLossMatrix.setCreateTime(DateUtils.getNowDate());
        return processLossMatrixMapper.insertProcessLossMatrix(processLossMatrix);
    }

    /**
     * 修改工序损耗矩阵
     *
     * @param processLossMatrix 工序损耗矩阵
     * @return 结果
     */
    @Override
    public int updateProcessLossMatrix(ProcessLossMatrix processLossMatrix) {
        processLossMatrix.setUpdateTime(DateUtils.getNowDate());
        return processLossMatrixMapper.updateProcessLossMatrix(processLossMatrix);
    }

    /**
     * 批量删除工序损耗矩阵
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @Override
    public int deleteProcessLossMatrixByIds(Long[] ids) {
        return processLossMatrixMapper.deleteProcessLossMatrixByIds(ids);
    }

    /**
     * 删除工序损耗矩阵信息
     *
     * @param id 工序损耗矩阵主键
     * @return 结果
     */
    @Override
    public int deleteProcessLossMatrixById(Long id) {
        return processLossMatrixMapper.deleteProcessLossMatrixById(id);
    }

    @Override
    public int updateActualAvgLoss(Long id, Double actualAvgLoss) {
        return processLossMatrixMapper.updateActualAvgLoss(id, actualAvgLoss);
    }

    @Override
    public boolean checkCombinationExists(String materialAType, String materialBType, String processCode) {
        ProcessLossMatrix exist = processLossMatrixMapper.selectByCombination(materialAType, materialBType, processCode);
        return exist != null;
    }
}
