package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.ProcessRouteItem;

/**
 * 工艺路线明细Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public interface ProcessRouteItemMapper {
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
     * 删除工艺路线明细
     *
     * @param id 工艺路线明细主键
     * @return 结果
     */
    public int deleteProcessRouteItemById(Long id);

    /**
     * 批量删除工艺路线明细
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProcessRouteItemByIds(Long[] ids);

    /**
     * 根据工艺路线ID删除所有明细
     *
     * @param routeId 工艺路线ID
     * @return 结果
     */
    public int deleteProcessRouteItemByRouteId(Long routeId);

    /**
     * 根据工艺路线ID查询明细列表
     *
     * @param routeId 工艺路线ID
     * @return 工艺路线明细集合
     */
    public List<ProcessRouteItem> selectProcessRouteItemByRouteId(Long routeId);
}
