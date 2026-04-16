package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.ProcessDef;

/**
 * 工序定义Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public interface IProcessDefService {
    /**
     * 查询工序定义
     *
     * @param id 工序定义主键
     * @return 工序定义
     */
    public ProcessDef selectProcessDefById(Long id);

    /**
     * 查询工序定义列表
     *
     * @param processDef 工序定义
     * @return 工序定义集合
     */
    public List<ProcessDef> selectProcessDefList(ProcessDef processDef);

    /**
     * 新增工序定义
     *
     * @param processDef 工序定义
     * @return 结果
     */
    public int insertProcessDef(ProcessDef processDef);

    /**
     * 修改工序定义
     *
     * @param processDef 工序定义
     * @return 结果
     */
    public int updateProcessDef(ProcessDef processDef);

    /**
     * 批量删除工序定义
     *
     * @param ids 需要删除的工序定义主键集合
     * @return 结果
     */
    public int deleteProcessDefByIds(Long[] ids);

    /**
     * 删除工序定义信息
     *
     * @param id 工序定义主键
     * @return 结果
     */
    public int deleteProcessDefById(Long id);
}
