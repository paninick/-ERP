package com.ruoyi.erp.mapper;

import com.ruoyi.erp.domain.OutsourceOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 外协加工单Mapper接口
 *
 * @author zhangmingjian
 */
@Mapper
public interface OutsourceOrderMapper {
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
     * 删除外协加工单
     *
     * @param id 外协加工单ID
     * @return 结果
     */
    public int deleteOutsourceOrderById(Long id);

    /**
     * 批量删除外协加工单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOutsourceOrderByIds(Long[] ids);
}
