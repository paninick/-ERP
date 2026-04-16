package com.ruoyi.erp.service.impl;

import com.ruoyi.erp.domain.ProduceDefect;
import com.ruoyi.erp.mapper.ProduceDefectMapper;
import com.ruoyi.erp.service.IProduceDefectService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 次品记录Service业务层处理
 *
 * @author zhangmingjian
 */
@Service
public class ProduceDefectServiceImpl implements IProduceDefectService {

    @Autowired
    private ProduceDefectMapper produceDefectMapper;

    /**
     * 查询次品记录
     *
     * @param id 次品记录ID
     * @return 次品记录
     */
    @Override
    public ProduceDefect selectProduceDefectById(Long id) {
        return produceDefectMapper.selectProduceDefectById(id);
    }

    /**
     * 查询次品记录列表
     *
     * @param produceDefect 次品记录
     * @return 次品记录
     */
    @Override
    public List<ProduceDefect> selectProduceDefectList(ProduceDefect produceDefect) {
        return produceDefectMapper.selectProduceDefectList(produceDefect);
    }

    /**
     * 新增次品记录
     *
     * @param produceDefect 次品记录
     * @return 结果
     */
    @Override
    public int insertProduceDefect(ProduceDefect produceDefect) {
        produceDefect.setCreateBy(SecurityUtils.getUsername());
        produceDefect.setCreateTime(DateUtils.getNowDate());
        produceDefect.setUpdateBy(SecurityUtils.getUsername());
        produceDefect.setUpdateTime(DateUtils.getNowDate());
        return produceDefectMapper.insertProduceDefect(produceDefect);
    }

    /**
     * 修改次品记录
     *
     * @param produceDefect 次品记录
     * @return 结果
     */
    @Override
    public int updateProduceDefect(ProduceDefect produceDefect) {
        produceDefect.setUpdateBy(SecurityUtils.getUsername());
        produceDefect.setUpdateTime(DateUtils.getNowDate());
        return produceDefectMapper.updateProduceDefect(produceDefect);
    }

    /**
     * 批量删除次品记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteProduceDefectByIds(Long[] ids) {
        return produceDefectMapper.deleteProduceDefectByIds(ids);
    }

    /**
     * 删除次品记录信息
     *
     * @param id 次品记录ID
     * @return 结果
     */
    @Override
    public int deleteProduceDefectById(Long id) {
        return produceDefectMapper.deleteProduceDefectById(id);
    }

    @Override
    public Integer sumDefectQtyByJobId(Long jobId) {
        return produceDefectMapper.sumDefectQtyByJobId(jobId);
    }
}
