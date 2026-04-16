package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.ProcessDef;

/**
 * 工序定义Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public interface ProcessDefMapper {
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
     * 删除工序定义
     *
     * @param id 工序定义主键
     * @return 结果
     */
    public int deleteProcessDefById(Long id);

    /**
     * 批量删除工序定义
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProcessDefByIds(Long[] ids);

    /**
     * 根据工序编码查询
     *
     * @param processCode 工序编码
     * @return 工序信息
     */
    public ProcessDef selectProcessDefByCode(String processCode);
}
