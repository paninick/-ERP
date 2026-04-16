package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.ProcessRouteItemMapper;
import com.ruoyi.erp.domain.ProcessRouteItem;
import com.ruoyi.erp.service.IProcessRouteItemService;

/**
 * 工艺路线明细Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
@Service
public class ProcessRouteItemServiceImpl implements IProcessRouteItemService {
    @Autowired
    private ProcessRouteItemMapper processRouteItemMapper;

    /**
     * 查询工艺路线明细
     *
     * @param id 工艺路线明细主键
     * @return 工艺路线明细
     */
    @Override
    public ProcessRouteItem selectProcessRouteItemById(Long id) {
        return processRouteItemMapper.selectProcessRouteItemById(id);
    }

    /**
     * 查询工艺路线明细列表
     *
     * @param processRouteItem 工艺路线明细
     * @return 工艺路线明细
     */
    @Override
    public List<ProcessRouteItem> selectProcessRouteItemList(ProcessRouteItem processRouteItem) {
        return processRouteItemMapper.selectProcessRouteItemList(processRouteItem);
    }

    /**
     * 新增工艺路线明细
     *
     * @param processRouteItem 工艺路线明细
     * @return 结果
     */
    @Override
    public int insertProcessRouteItem(ProcessRouteItem processRouteItem) {
        return processRouteItemMapper.insertProcessRouteItem(processRouteItem);
    }

    /**
     * 修改工艺路线明细
     *
     * @param processRouteItem 工艺路线明细
     * @return 结果
     */
    @Override
    public int updateProcessRouteItem(ProcessRouteItem processRouteItem) {
        return processRouteItemMapper.updateProcessRouteItem(processRouteItem);
    }

    /**
     * 批量删除工艺路线明细
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @Override
    public int deleteProcessRouteItemByIds(Long[] ids) {
        return processRouteItemMapper.deleteProcessRouteItemByIds(ids);
    }

    /**
     * 删除工艺路线明细信息
     *
     * @param id 工艺路线明细主键
     * @return 结果
     */
    @Override
    public int deleteProcessRouteItemById(Long id) {
        return processRouteItemMapper.deleteProcessRouteItemById(id);
    }

    @Override
    public int deleteProcessRouteItemByRouteId(Long routeId) {
        return processRouteItemMapper.deleteProcessRouteItemByRouteId(routeId);
    }
}
