package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.ProcessPriceMapper;
import com.ruoyi.erp.domain.ProcessPrice;
import com.ruoyi.erp.service.IProcessPriceService;

/**
 * 工序工价Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
@Service
public class ProcessPriceServiceImpl implements IProcessPriceService {
    @Autowired
    private ProcessPriceMapper processPriceMapper;

    /**
     * 查询工序工价
     *
     * @param id 工序工价主键
     * @return 工序工价
     */
    @Override
    public ProcessPrice selectProcessPriceById(Long id) {
        return processPriceMapper.selectProcessPriceById(id);
    }

    /**
     * 查询工序工价列表
     *
     * @param processPrice 工序工价
     * @return 工序工价
     */
    @Override
    public List<ProcessPrice> selectProcessPriceList(ProcessPrice processPrice) {
        return processPriceMapper.selectProcessPriceList(processPrice);
    }

    /**
     * 新增工序工价
     *
     * @param processPrice 工序工价
     * @return 结果
     */
    @Override
    public int insertProcessPrice(ProcessPrice processPrice) {
        processPrice.setCreateBy(SecurityUtils.getUserId().toString());
        processPrice.setCreateTime(DateUtils.getNowDate());
        return processPriceMapper.insertProcessPrice(processPrice);
    }

    /**
     * 修改工序工价
     *
     * @param processPrice 工序工价
     * @return 结果
     */
    @Override
    public int updateProcessPrice(ProcessPrice processPrice) {
        processPrice.setUpdateTime(DateUtils.getNowDate());
        return processPriceMapper.updateProcessPrice(processPrice);
    }

    /**
     * 批量删除工序工价
     *
     * @param ids 需要删除的工序工价主键
     * @return 结果
     */
    @Override
    public int deleteProcessPriceByIds(Long[] ids) {
        return processPriceMapper.deleteProcessPriceByIds(ids);
    }

    /**
     * 删除工序工价信息
     *
     * @param id 工序工价主键
     * @return 结果
     */
    @Override
    public int deleteProcessPriceById(Long id) {
        return processPriceMapper.deleteProcessPriceById(id);
    }

    @Override
    public ProcessPrice selectCurrentPrice(Long processId, Long employeeId) {
        return processPriceMapper.selectCurrentPrice(processId, employeeId);
    }
}
