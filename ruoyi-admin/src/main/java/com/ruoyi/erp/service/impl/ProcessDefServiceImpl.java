package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.ProcessDefMapper;
import com.ruoyi.erp.domain.ProcessDef;
import com.ruoyi.erp.service.IProcessDefService;

/**
 * 工序定义Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
@Service
public class ProcessDefServiceImpl implements IProcessDefService {
    @Autowired
    private ProcessDefMapper processDefMapper;

    /**
     * 查询工序定义
     *
     * @param id 工序定义主键
     * @return 工序定义
     */
    @Override
    public ProcessDef selectProcessDefById(Long id) {
        return processDefMapper.selectProcessDefById(id);
    }

    /**
     * 查询工序定义列表
     *
     * @param processDef 工序定义
     * @return 工序定义
     */
    @Override
    public List<ProcessDef> selectProcessDefList(ProcessDef processDef) {
        return processDefMapper.selectProcessDefList(processDef);
    }

    /**
     * 新增工序定义
     *
     * @param processDef 工序定义
     * @return 结果
     */
    @Override
    public int insertProcessDef(ProcessDef processDef) {
        processDef.setCreateBy(SecurityUtils.getUserId().toString());
        processDef.setCreateTime(DateUtils.getNowDate());
        return processDefMapper.insertProcessDef(processDef);
    }

    /**
     * 修改工序定义
     *
     * @param processDef 工序定义
     * @return 结果
     */
    @Override
    public int updateProcessDef(ProcessDef processDef) {
        processDef.setUpdateTime(DateUtils.getNowDate());
        return processDefMapper.updateProcessDef(processDef);
    }

    /**
     * 批量删除工序定义
     *
     * @param ids 需要删除的工序定义主键
     * @return 结果
     */
    @Override
    public int deleteProcessDefByIds(Long[] ids) {
        return processDefMapper.deleteProcessDefByIds(ids);
    }

    /**
     * 删除工序定义信息
     *
     * @param id 工序定义主键
     * @return 结果
     */
    @Override
    public int deleteProcessDefById(Long id) {
        return processDefMapper.deleteProcessDefById(id);
    }
}
