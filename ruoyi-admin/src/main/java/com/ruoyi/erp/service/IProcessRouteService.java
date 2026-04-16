package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.ProcessRoute;
import com.ruoyi.erp.domain.ProcessRouteItem;

/**
 * 工艺路线Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public interface IProcessRouteService {
    /**
     * 查询工艺路线
     *
     * @param id 工艺路线主键
     * @return 工艺路线
     */
    public ProcessRoute selectProcessRouteById(Long id);

    /**
     * 查询工艺路线列表
     *
     * @param processRoute 工艺路线
     * @return 工艺路线集合
     */
    public List<ProcessRoute> selectProcessRouteList(ProcessRoute processRoute);

    /**
     * 新增工艺路线
     *
     * @param processRoute 工艺路线
     * @param items 工艺路线明细
     * @return 结果
     */
    public int insertProcessRoute(ProcessRoute processRoute, List<ProcessRouteItem> items);

    /**
     * 修改工艺路线
     *
     * @param processRoute 工艺路线
     * @param items 工艺路线明细
     * @return 结果
     */
    public int updateProcessRoute(ProcessRoute processRoute, List<ProcessRouteItem> items);

    /**
     * 批量删除工艺路线
     *
     * @param ids 需要删除的工艺路线主键集合
     * @return 结果
     */
    public int deleteProcessRouteByIds(Long[] ids);

    /**
     * 删除工艺路线信息
     *
     * @param id 工艺路线主键
     * @return 结果
     */
    public int deleteProcessRouteById(Long id);

    /**
     * 获取默认工艺路线
     *
     * @param productType 产品类型
     * @return 默认工艺路线
     */
    public ProcessRoute selectDefaultRouteByProductType(String productType);

    /**
     * 根据工艺路线ID获取明细列表
     *
     * @param routeId 工艺路线ID
     * @return 明细列表
     */
    public List<ProcessRouteItem> selectProcessRouteItemByRouteId(Long routeId);
}
