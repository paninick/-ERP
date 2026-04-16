package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.ProcessRouteItem;

/**
 * 工艺路线明细Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public interface IProcessRouteItemService {
    /**
     * 查询工艺路线明细
     *
     * @param id 工艺路线明细主键
     * @return 工艺路线明细
     */
    public ProcessRouteItem selectProcessRouteItemById(Long id);

    /**
     * 查询工艺路线明细列表
     *
     * @param processRouteItem 工艺路线明细
     * @return 工艺路线明细集合
     */
    public List<ProcessRouteItem> selectProcessRouteItemList(ProcessRouteItem processRouteItem);

    /**
     * 新增工艺路线明细
     *
     * @param processRouteItem 工艺路线明细
     * @return 结果
     */
    public int insertProcessRouteItem(ProcessRouteItem processRouteItem);

    /**
     * 修改工艺路线明细
     *
     * @param processRouteItem 工艺路线明细
     * @return 结果
     */
    public int updateProcessRouteItem(ProcessRouteItem processRouteItem);

    /**
     * 批量删除工艺路线明细
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProcessRouteItemByIds(Long[] ids);

    /**
     * 删除工艺路线明细信息
     *
     * @param id 工艺路线明细主键
     * @return 结果
     */
    public int deleteProcessRouteItemById(Long id);

    /**
     * 根据工艺路线ID删除所有明细
     *
     * @param routeId 工艺路线ID
     * @return 结果
     */
    public int deleteProcessRouteItemByRouteId(Long routeId);
}
