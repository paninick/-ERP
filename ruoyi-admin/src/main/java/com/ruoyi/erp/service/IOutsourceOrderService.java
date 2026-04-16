package com.ruoyi.erp.service;

import com.ruoyi.erp.domain.OutsourceOrder;

import java.util.List;

/**
 * 外协加工单Service接口
 *
 * @author zhangmingjian
 */
public interface IOutsourceOrderService {
    /**
     * 查询外协加工单
     *
     * @param id 外协加工单ID
     * @return 外协加工单
     */
    public OutsourceOrder selectOutsourceOrderById(Long id);

    /**
     * 查询外协加工单列表
     *
     * @param outsourceOrder 外协加工单
     * @return 外协加工单集合
     */
    public List<OutsourceOrder> selectOutsourceOrderList(OutsourceOrder outsourceOrder);

    /**
     * 新增外协加工单
     *
     * @param outsourceOrder 外协加工单
     * @return 结果
     */
    public int insertOutsourceOrder(OutsourceOrder outsourceOrder);

    /**
     * 修改外协加工单
     *
     * @param outsourceOrder 外协加工单
     * @return 结果
     */
    public int updateOutsourceOrder(OutsourceOrder outsourceOrder);

    /**
     * 批量删除外协加工单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOutsourceOrderByIds(Long[] ids);

    /**
     * 删除外协加工单信息
     *
     * @param id 外协加工单ID
     * @return 结果
     */
    public int deleteOutsourceOrderById(Long id);
}
