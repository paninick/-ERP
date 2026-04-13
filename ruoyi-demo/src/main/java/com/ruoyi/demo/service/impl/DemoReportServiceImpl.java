package com.ruoyi.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.demo.domain.DemoReport;
import com.ruoyi.demo.mapper.DemoReportMapper;
import com.ruoyi.demo.service.IDemoReportService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 报工管理Service实现类
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
@Service
public class DemoReportServiceImpl extends ServiceImpl<DemoReportMapper, DemoReport> implements IDemoReportService {

    @Override
    public IPage<DemoReport> selectDemoReportPage(DemoReport demoReport, Integer page, Integer size) {
        Page<DemoReport> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<DemoReport> wrapper = new LambdaQueryWrapper<>();
        
        // 订单ID查询
        if (demoReport.getOrderId() != null) {
            wrapper.eq(DemoReport::getOrderId, demoReport.getOrderId());
        }
        
        // 工序查询
        if (demoReport.getProcess() != null && !demoReport.getProcess().isEmpty()) {
            wrapper.like(DemoReport::getProcess, demoReport.getProcess());
        }
        
        // 日期查询
        if (demoReport.getWorkDate() != null) {
            wrapper.eq(DemoReport::getWorkDate, demoReport.getWorkDate());
        }
        
        // 按创建时间倒序排序
        wrapper.orderByDesc(DemoReport::getCreateTime);
        
        return this.page(pageParam, wrapper);
    }

    @Override
    public List<DemoReport> selectDemoReportList(DemoReport demoReport) {
        LambdaQueryWrapper<DemoReport> wrapper = new LambdaQueryWrapper<>();
        
        // 订单ID查询
        if (demoReport.getOrderId() != null) {
            wrapper.eq(DemoReport::getOrderId, demoReport.getOrderId());
        }
        
        // 工序查询
        if (demoReport.getProcess() != null && !demoReport.getProcess().isEmpty()) {
            wrapper.like(DemoReport::getProcess, demoReport.getProcess());
        }
        
        // 日期查询
        if (demoReport.getWorkDate() != null) {
            wrapper.eq(DemoReport::getWorkDate, demoReport.getWorkDate());
        }
        
        // 按创建时间倒序排序
        wrapper.orderByDesc(DemoReport::getCreateTime);
        
        return this.list(wrapper);
    }

    @Override
    public DemoReport selectDemoReportById(Long id) {
        return this.getById(id);
    }

    @Override
    public boolean insertDemoReport(DemoReport demoReport) {
        return this.save(demoReport);
    }

    @Override
    public boolean updateDemoReport(DemoReport demoReport) {
        return this.updateById(demoReport);
    }

    @Override
    public boolean deleteDemoReportById(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean deleteDemoReportByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return false;
        }
        
        return this.removeBatchByIds(List.of(ids));
    }
}
