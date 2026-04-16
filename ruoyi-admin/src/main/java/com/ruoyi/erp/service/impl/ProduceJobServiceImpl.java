package com.ruoyi.erp.service.impl;

import com.ruoyi.erp.domain.ProduceJob;
import com.ruoyi.erp.mapper.ProduceJobMapper;
import com.ruoyi.erp.service.IProduceJobService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 生产工票Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class ProduceJobServiceImpl implements IProduceJobService {

    @Autowired
    private ProduceJobMapper produceJobMapper;

    /**
     * 查询生产工票
     *
     * @param id 生产工票ID
     * @return 生产工票
     */
    @Override
    public ProduceJob selectProduceJobById(Long id) {
        return produceJobMapper.selectProduceJobById(id);
    }

    /**
     * 查询生产工票列表
     *
     * @param produceJob 生产工票
     * @return 生产工票
     */
    @Override
    public List<ProduceJob> selectProduceJobList(ProduceJob produceJob) {
        return produceJobMapper.selectProduceJobList(produceJob);
    }

    /**
     * 新增生产工票
     *
     * @param produceJob 生产工票
     * @return 结果
     */
    @Override
    public int insertProduceJob(ProduceJob produceJob) {
        produceJob.setCreateBy(SecurityUtils.getUsername());
        produceJob.setCreateTime(DateUtils.getNowDate());
        produceJob.setUpdateBy(SecurityUtils.getUsername());
        produceJob.setUpdateTime(DateUtils.getNowDate());
        return produceJobMapper.insertProduceJob(produceJob);
    }

    /**
     * 批量新增生产工票
     *
     * @param produceJobList 生产工票列表
     * @return 结果
     */
    @Override
    public int batchInsertProduceJob(List<ProduceJob> produceJobList) {
        for (ProduceJob job : produceJobList) {
            job.setCreateBy(SecurityUtils.getUsername());
            job.setCreateTime(DateUtils.getNowDate());
            job.setUpdateBy(SecurityUtils.getUsername());
            job.setUpdateTime(DateUtils.getNowDate());
        }
        return produceJobMapper.batchInsertProduceJob(produceJobList);
    }

    /**
     * 修改生产工票
     *
     * @param produceJob 生产工票
     * @return 结果
     */
    @Override
    public int updateProduceJob(ProduceJob produceJob) {
        produceJob.setUpdateBy(SecurityUtils.getUsername());
        produceJob.setUpdateTime(DateUtils.getNowDate());
        return produceJobMapper.updateProduceJob(produceJob);
    }

    /**
     * 批量删除生产工票
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteProduceJobByIds(Long[] ids) {
        return produceJobMapper.deleteProduceJobByIds(ids);
    }

    /**
     * 删除生产工票信息
     *
     * @param id 生产工票ID
     * @return 结果
     */
    @Override
    public int deleteProduceJobById(Long id) {
        return produceJobMapper.deleteProduceJobById(id);
    }

    @Override
    public List<ProduceJob> selectProduceJobByProducePlanId(Long producePlanId) {
        return produceJobMapper.selectProduceJobByProducePlanId(producePlanId);
    }

    @Override
    public Integer sumPlanQtyByProducePlanId(Long producePlanId) {
        return produceJobMapper.sumPlanQtyByProducePlanId(producePlanId);
    }

    @Override
    public Integer sumActualQtyByProducePlanId(Long producePlanId) {
        return produceJobMapper.sumActualQtyByProducePlanId(producePlanId);
    }
}
