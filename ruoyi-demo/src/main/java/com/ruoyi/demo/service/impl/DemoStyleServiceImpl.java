package com.ruoyi.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.demo.domain.DemoStyle;
import com.ruoyi.demo.mapper.DemoStyleMapper;
import com.ruoyi.demo.service.IDemoStyleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
 * 款式管理Service实现类
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
@Service
public class DemoStyleServiceImpl extends ServiceImpl<DemoStyleMapper, DemoStyle> implements IDemoStyleService {

    @Override
    public IPage<DemoStyle> selectDemoStylePage(DemoStyle demoStyle, Integer page, Integer size) {
        Page<DemoStyle> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<DemoStyle> wrapper = new LambdaQueryWrapper<>();
        
        // 款号查询
        if (demoStyle.getStyleNo() != null && !demoStyle.getStyleNo().isEmpty()) {
            wrapper.like(DemoStyle::getStyleNo, demoStyle.getStyleNo());
        }
        
        // 款式名称查询
        if (demoStyle.getStyleName() != null && !demoStyle.getStyleName().isEmpty()) {
            wrapper.like(DemoStyle::getStyleName, demoStyle.getStyleName());
        }
        
        // 品类查询
        if (demoStyle.getCategory() != null && !demoStyle.getCategory().isEmpty()) {
            wrapper.like(DemoStyle::getCategory, demoStyle.getCategory());
        }
        
        // 季节查询
        if (demoStyle.getSeason() != null && !demoStyle.getSeason().isEmpty()) {
            wrapper.like(DemoStyle::getSeason, demoStyle.getSeason());
        }
        
        // 按创建时间倒序排序
        wrapper.orderByDesc(DemoStyle::getCreateTime);
        
        return this.page(pageParam, wrapper);
    }

    @Override
    public List<DemoStyle> selectDemoStyleList(DemoStyle demoStyle) {
        LambdaQueryWrapper<DemoStyle> wrapper = new LambdaQueryWrapper<>();
        
        // 款号查询
        if (demoStyle.getStyleNo() != null && !demoStyle.getStyleNo().isEmpty()) {
            wrapper.like(DemoStyle::getStyleNo, demoStyle.getStyleNo());
        }
        
        // 款式名称查询
        if (demoStyle.getStyleName() != null && !demoStyle.getStyleName().isEmpty()) {
            wrapper.like(DemoStyle::getStyleName, demoStyle.getStyleName());
        }
        
        // 品类查询
        if (demoStyle.getCategory() != null && !demoStyle.getCategory().isEmpty()) {
            wrapper.like(DemoStyle::getCategory, demoStyle.getCategory());
        }
        
        // 季节查询
        if (demoStyle.getSeason() != null && !demoStyle.getSeason().isEmpty()) {
            wrapper.like(DemoStyle::getSeason, demoStyle.getSeason());
        }
        
        // 按创建时间倒序排序
        wrapper.orderByDesc(DemoStyle::getCreateTime);
        
        return this.list(wrapper);
    }

    @Override
    public DemoStyle selectDemoStyleById(Long id) {
        return this.getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertDemoStyle(DemoStyle demoStyle) {
        // 计算总成本
        if (demoStyle.getCmtCost() == null) {
            demoStyle.setCmtCost(BigDecimal.ZERO);
        }
        if (demoStyle.getFabricCost() == null) {
            demoStyle.setFabricCost(BigDecimal.ZERO);
        }
        if (demoStyle.getAccessoryCost() == null) {
            demoStyle.setAccessoryCost(BigDecimal.ZERO);
        }
        
        BigDecimal totalCost = calculateTotalCost(demoStyle);
        demoStyle.setStandardCost(totalCost);
        
        return this.save(demoStyle);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateDemoStyle(DemoStyle demoStyle) {
        // 计算总成本
        if (demoStyle.getCmtCost() == null) {
            demoStyle.setCmtCost(BigDecimal.ZERO);
        }
        if (demoStyle.getFabricCost() == null) {
            demoStyle.setFabricCost(BigDecimal.ZERO);
        }
        if (demoStyle.getAccessoryCost() == null) {
            demoStyle.setAccessoryCost(BigDecimal.ZERO);
        }
        
        BigDecimal totalCost = calculateTotalCost(demoStyle);
        demoStyle.setStandardCost(totalCost);
        
        return this.updateById(demoStyle);
    }

    @Override
    public boolean deleteDemoStyleById(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean deleteDemoStyleByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return false;
        }
        
        return this.removeBatchByIds(List.of(ids));
    }

    @Override
    public BigDecimal calculateTotalCost(DemoStyle style) {
        BigDecimal cmtCost = style.getCmtCost() != null ? style.getCmtCost() : BigDecimal.ZERO;
        BigDecimal fabricCost = style.getFabricCost() != null ? style.getFabricCost() : BigDecimal.ZERO;
        BigDecimal accessoryCost = style.getAccessoryCost() != null ? style.getAccessoryCost() : BigDecimal.ZERO;
        
        return cmtCost.add(fabricCost).add(accessoryCost);
    }
}
