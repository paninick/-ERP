package com.ruoyi.erp.service.impl;

import com.ruoyi.erp.domain.OutsourceOrder;
import com.ruoyi.erp.mapper.OutsourceOrderMapper;
import com.ruoyi.erp.service.IOutsourceOrderService;
import com.ruoyi.erp.utils.BillNoGenerator;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 外协加工单Service业务层处理
 *
 * @author zhangmingjian
 */
@Service
public class OutsourceOrderServiceImpl implements IOutsourceOrderService {

    @Autowired
    private OutsourceOrderMapper outsourceOrderMapper;

    @Autowired
    private BillNoGenerator billNoGenerator;

    /**
     * 查询外协加工单
     *
     * @param id 外协加工单ID
     * @return 外协加工单
     */
    @Override
    public OutsourceOrder selectOutsourceOrderById(Long id) {
        return outsourceOrderMapper.selectOutsourceOrderById(id);
    }

    /**
     * 查询外协加工单列表
     *
     * @param outsourceOrder 外协加工单
     * @return 外协加工单
     */
    @Override
    public List<OutsourceOrder> selectOutsourceOrderList(OutsourceOrder outsourceOrder) {
        return outsourceOrderMapper.selectOutsourceOrderList(outsourceOrder);
    }

    /**
     * 新增外协加工单
     *
     * @param outsourceOrder 外协加工单
     * @return 结果
     */
    @Override
    public int insertOutsourceOrder(OutsourceOrder outsourceOrder) {
        if (outsourceOrder.getOutsourceNo() == null || outsourceOrder.getOutsourceNo().isEmpty()) {
            outsourceOrder.setOutsourceNo(billNoGenerator.generate("OO"));
        }
        outsourceOrder.setCreateBy(SecurityUtils.getUsername());
        outsourceOrder.setCreateTime(DateUtils.getNowDate());
        outsourceOrder.setUpdateBy(SecurityUtils.getUsername());
        outsourceOrder.setUpdateTime(DateUtils.getNowDate());
        return outsourceOrderMapper.insertOutsourceOrder(outsourceOrder);
    }

    /**
     * 修改外协加工单
     *
     * @param outsourceOrder 外协加工单
     * @return 结果
     */
    @Override
    public int updateOutsourceOrder(OutsourceOrder outsourceOrder) {
        outsourceOrder.setUpdateBy(SecurityUtils.getUsername());
        outsourceOrder.setUpdateTime(DateUtils.getNowDate());
        return outsourceOrderMapper.updateOutsourceOrder(outsourceOrder);
    }

    /**
     * 批量删除外协加工单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteOutsourceOrderByIds(Long[] ids) {
        return outsourceOrderMapper.deleteOutsourceOrderByIds(ids);
    }

    /**
     * 删除外协加工单信息
     *
     * @param id 外协加工单ID
     * @return 结果
     */
    @Override
    public int deleteOutsourceOrderById(Long id) {
        return outsourceOrderMapper.deleteOutsourceOrderById(id);
    }
}
