package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.ProcessRoute;

/**
 * 工艺路线Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public interface ProcessRouteMapper {
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
     * @return 结果
     */
    public int insertProcessRoute(ProcessRoute processRoute);

    /**
     * 修改工艺路线
     *
     * @param processRoute 工艺路线
     * @return 结果
     */
    public int updateProcessRoute(ProcessRoute processRoute);

    /**
     * 删除工艺路线
     *
     * @param id 工艺路线主键
     * @return 结果
     */
    public int deleteProcessRouteById(Long id);

    /**
     * 批量删除工艺路线
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProcessRouteByIds(Long[] ids);

    /**
     * 查询默认工艺路线
     *
     * @param productType 产品类型
     * @return 默认工艺路线
     */
    public ProcessRoute selectDefaultRouteByProductType(String productType);
}
