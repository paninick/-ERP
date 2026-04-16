package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.ProcessLossMatrix;

/**
 * 工序损耗矩阵Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public interface IProcessLossMatrixService {
    /**
     * 查询工序损耗矩阵
     *
     * @param id 工序损耗矩阵主键
     * @return 工序损耗矩阵
     */
    public ProcessLossMatrix selectProcessLossMatrixById(Long id);

    /**
     * 查询工序损耗矩阵列表
     *
     * @param processLossMatrix 工序损耗矩阵
     * @return 工序损耗矩阵集合
     */
    public List<ProcessLossMatrix> selectProcessLossMatrixList(ProcessLossMatrix processLossMatrix);

    /**
     * 新增工序损耗矩阵
     *
     * @param processLossMatrix 工序损耗矩阵
     * @return 结果
     */
    public int insertProcessLossMatrix(ProcessLossMatrix processLossMatrix);

    /**
     * 修改工序损耗矩阵
     *
     * @param processLossMatrix 工序损耗矩阵
     * @return 结果
     */
    public int updateProcessLossMatrix(ProcessLossMatrix processLossMatrix);

    /**
     * 批量删除工序损耗矩阵
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProcessLossMatrixByIds(Long[] ids);

    /**
     * 删除工序损耗矩阵信息
     *
     * @param id 工序损耗矩阵主键
     * @return 结果
     */
    public int deleteProcessLossMatrixById(Long id);

    /**
     * 更新实际平均损耗
     *
     * @param id 矩阵ID
     * @param actualAvgLoss 实际平均损耗
     * @return 结果
     */
    public int updateActualAvgLoss(Long id, Double actualAvgLoss);

    /**
     * 检查组合是否已存在
     *
     * @param materialAType 主料类型
     * @param materialBType 辅料类型
     * @param processCode 工艺代码
     * @return 是否已存在
     */
    public boolean checkCombinationExists(String materialAType, String materialBType, String processCode);
}
