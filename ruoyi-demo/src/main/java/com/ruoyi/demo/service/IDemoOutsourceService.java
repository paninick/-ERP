package com.ruoyi.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.demo.domain.DemoOutsource;
import com.ruoyi.demo.domain.DemoOutsourceExtra;
import java.math.BigDecimal;
import java.util.List;

/**
 * 外发加工管理Service接口
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
public interface IDemoOutsourceService extends IService<DemoOutsource> {
    /**
     * 查询外发加工订单列表（分页）
     * 
     * @param demoOutsource 查询条件
     * @param page 页码
     * @param size 每页大小
     * @return 外发加工订单列表
     */
    IPage<DemoOutsource> selectDemoOutsourcePage(DemoOutsource demoOutsource, Integer page, Integer size);

    /**
     * 查询外发加工订单列表（不分页）
     * 
     * @param demoOutsource 查询条件
     * @return 外发加工订单列表
     */
    List<DemoOutsource> selectDemoOutsourceList(DemoOutsource demoOutsource);

    /**
     * 查询外发加工订单详情
     * 
     * @param id 外发加工订单ID
     * @return 外发加工订单详情
     */
    DemoOutsource selectDemoOutsourceById(Long id);

    /**
     * 新增外发加工订单
     * 
     * @param demoOutsource 外发加工订单信息
     * @return 新增结果
     */
    boolean insertDemoOutsource(DemoOutsource demoOutsource);

    /**
     * 修改外发加工订单
     * 
     * @param demoOutsource 外发加工订单信息
     * @return 修改结果
     */
    boolean updateDemoOutsource(DemoOutsource demoOutsource);

    /**
     * 删除外发加工订单
     * 
     * @param id 外发加工订单ID
     * @return 删除结果
     */
    boolean deleteDemoOutsourceById(Long id);

    /**
     * 批量删除外发加工订单
     * 
     * @param ids 外发加工订单ID列表
     * @return 删除结果
     */
    boolean deleteDemoOutsourceByIds(Long[] ids);

    /**
     * 新增外发加工补料记录
     * 
     * @param demoOutsourceExtra 外发加工补料记录信息
     * @return 新增结果
     */
    boolean insertDemoOutsourceExtra(DemoOutsourceExtra demoOutsourceExtra);

    /**
     * 查询外发加工补料记录列表
     * 
     * @param demoOutsourceExtra 查询条件
     * @return 外发加工补料记录列表
     */
    List<DemoOutsourceExtra> selectDemoOutsourceExtraList(DemoOutsourceExtra demoOutsourceExtra);

    /**
     * 查询外发加工补料记录详情
     * 
     * @param id 外发加工补料记录ID
     * @return 外发加工补料记录详情
     */
    DemoOutsourceExtra selectDemoOutsourceExtraById(Long id);

    /**
     * 审批外发加工补料记录
     * 
     * @param id 外发加工补料记录ID
     * @param approved 审批状态（0待审批 1已审批 2已拒绝）
     * @return 审批结果
     */
    boolean approveDemoOutsourceExtra(Long id, String approved);

    /**
     * 计算外发加工订单的损耗率
     * 
     * @param demoOutsource 外发加工订单信息
     * @return 损耗率
     */
    BigDecimal calculateLossRate(DemoOutsource demoOutsource);

    /**
     * 计算外发加工订单的加工费总额
     * 
     * @param demoOutsource 外发加工订单信息
     * @return 加工费总额
     */
    BigDecimal calculateTotalAmount(DemoOutsource demoOutsource);
}
