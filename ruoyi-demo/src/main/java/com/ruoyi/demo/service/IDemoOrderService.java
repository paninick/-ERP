package com.ruoyi.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.demo.domain.DemoOrder;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单管理Service接口
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
public interface IDemoOrderService extends IService<DemoOrder> {
    /**
     * 查询订单列表（分页）
     * 
     * @param demoOrder 查询条件
     * @param page 页码
     * @param size 每页大小
     * @return 订单列表
     */
    IPage<DemoOrder> selectDemoOrderPage(DemoOrder demoOrder, Integer page, Integer size);

    /**
     * 查询订单列表（不分页）
     * 
     * @param demoOrder 查询条件
     * @return 订单列表
     */
    List<DemoOrder> selectDemoOrderList(DemoOrder demoOrder);

    /**
     * 查询订单详情
     * 
     * @param id 订单ID
     * @return 订单详情
     */
    DemoOrder selectDemoOrderById(Long id);

    /**
     * 新增订单
     * 
     * @param demoOrder 订单信息
     * @return 新增结果
     */
    boolean insertDemoOrder(DemoOrder demoOrder);

    /**
     * 修改订单
     * 
     * @param demoOrder 订单信息
     * @return 修改结果
     */
    boolean updateDemoOrder(DemoOrder demoOrder);

    /**
     * 删除订单
     * 
     * @param id 订单ID
     * @return 删除结果
     */
    boolean deleteDemoOrderById(Long id);

    /**
     * 批量删除订单
     * 
     * @param ids 订单ID列表
     * @return 删除结果
     */
    boolean deleteDemoOrderByIds(Long[] ids);

    /**
     * 计算订单利润
     * 
     * @param revenue 收入
     * @param cost 成本
     * @return 利润
     */
    BigDecimal calculateProfit(BigDecimal revenue, BigDecimal cost);

    /**
     * 计算利润率
     * 
     * @param revenue 收入
     * @param cost 成本
     * @return 利润率
     */
    BigDecimal calculateProfitRate(BigDecimal revenue, BigDecimal cost);
}
