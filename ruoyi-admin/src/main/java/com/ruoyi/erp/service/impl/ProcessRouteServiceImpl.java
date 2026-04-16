package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.erp.mapper.ProcessRouteMapper;
import com.ruoyi.erp.mapper.ProcessRouteItemMapper;
import com.ruoyi.erp.domain.ProcessRoute;
import com.ruoyi.erp.domain.ProcessRouteItem;
import com.ruoyi.erp.service.IProcessRouteService;

/**
 * 工艺路线Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
@Service
public class ProcessRouteServiceImpl implements IProcessRouteService {
    @Autowired
    private ProcessRouteMapper processRouteMapper;

    @Autowired
    private ProcessRouteItemMapper processRouteItemMapper;

    /**
     * 查询工艺路线
     *
     * @param id 工艺路线主键
     * @return 工艺路线
     */
    @Override
    public ProcessRoute selectProcessRouteById(Long id) {
        return processRouteMapper.selectProcessRouteById(id);
    }

    /**
     * 查询工艺路线列表
     *
     * @param processRoute 工艺路线
     * @return 工艺路线
     */
    @Override
    public List<ProcessRoute> selectProcessRouteList(ProcessRoute processRoute) {
        return processRouteMapper.selectProcessRouteList(processRoute);
    }

    /**
     * 新增工艺路线
     *
     * @param processRoute 工艺路线
     * @param items 工艺路线明细
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertProcessRoute(ProcessRoute processRoute, List<ProcessRouteItem> items) {
        processRoute.setCreateBy(SecurityUtils.getUserId().toString());
        processRoute.setCreateTime(DateUtils.getNowDate());
        int result = processRouteMapper.insertProcessRoute(processRoute);
        if (result > 0 && items != null && !items.isEmpty()) {
            for (ProcessRouteItem item : items) {
                item.setRouteId(processRoute.getId());
                processRouteItemMapper.insertProcessRouteItem(item);
            }
        }
        return result;
    }

    /**
     * 修改工艺路线
     *
     * @param processRoute 工艺路线
     * @param items 工艺路线明细
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateProcessRoute(ProcessRoute processRoute, List<ProcessRouteItem> items) {
        processRoute.setUpdateTime(DateUtils.getNowDate());
        // 删除原有明细
        processRouteItemMapper.deleteProcessRouteItemByRouteId(processRoute.getId());
        // 新增明细
        if (items != null && !items.isEmpty()) {
            for (ProcessRouteItem item : items) {
                item.setRouteId(processRoute.getId());
                processRouteItemMapper.insertProcessRouteItem(item);
            }
        }
        return processRouteMapper.updateProcessRoute(processRoute);
    }

    /**
     * 批量删除工艺路线
     *
     * @param ids 需要删除的工艺路线主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteProcessRouteByIds(Long[] ids) {
        for (Long id : ids) {
            processRouteItemMapper.deleteProcessRouteItemByRouteId(id);
        }
        return processRouteMapper.deleteProcessRouteByIds(ids);
    }

    /**
     * 删除工艺路线信息
     *
     * @param id 工艺路线主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteProcessRouteById(Long id) {
        processRouteItemMapper.deleteProcessRouteItemByRouteId(id);
        return processRouteMapper.deleteProcessRouteById(id);
    }

    @Override
    public ProcessRoute selectDefaultRouteByProductType(String productType) {
        return processRouteMapper.selectDefaultRouteByProductType(productType);
    }

    @Override
    public List<ProcessRouteItem> selectProcessRouteItemByRouteId(Long routeId) {
        return processRouteItemMapper.selectProcessRouteItemByRouteId(routeId);
    }
}
