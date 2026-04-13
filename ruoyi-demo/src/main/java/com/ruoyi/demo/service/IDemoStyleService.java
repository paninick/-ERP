package com.ruoyi.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.demo.domain.DemoStyle;
import java.math.BigDecimal;
import java.util.List;

/**
 * 款式管理Service接口
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
public interface IDemoStyleService extends IService<DemoStyle> {
    /**
     * 查询款式列表（分页）
     * 
     * @param demoStyle 查询条件
     * @param page 页码
     * @param size 每页大小
     * @return 款式列表
     */
    IPage<DemoStyle> selectDemoStylePage(DemoStyle demoStyle, Integer page, Integer size);

    /**
     * 查询款式列表（不分页）
     * 
     * @param demoStyle 查询条件
     * @return 款式列表
     */
    List<DemoStyle> selectDemoStyleList(DemoStyle demoStyle);

    /**
     * 查询款式详情
     * 
     * @param id 款式ID
     * @return 款式详情
     */
    DemoStyle selectDemoStyleById(Long id);

    /**
     * 新增款式
     * 
     * @param demoStyle 款式信息
     * @return 新增结果
     */
    boolean insertDemoStyle(DemoStyle demoStyle);

    /**
     * 修改款式
     * 
     * @param demoStyle 款式信息
     * @return 修改结果
     */
    boolean updateDemoStyle(DemoStyle demoStyle);

    /**
     * 删除款式
     * 
     * @param id 款式ID
     * @return 删除结果
     */
    boolean deleteDemoStyleById(Long id);

    /**
     * 批量删除款式
     * 
     * @param ids 款式ID列表
     * @return 删除结果
     */
    boolean deleteDemoStyleByIds(Long[] ids);

    /**
     * 计算款式总成本
     * 
     * @param style 款式信息
     * @return 总成本
     */
    BigDecimal calculateTotalCost(DemoStyle style);
}
