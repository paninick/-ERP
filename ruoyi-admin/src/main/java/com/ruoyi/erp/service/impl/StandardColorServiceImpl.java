package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.StandardColorMapper;
import com.ruoyi.erp.domain.StandardColor;
import com.ruoyi.erp.service.IStandardColorService;

/**
 * 标准色卡Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
@Service
public class StandardColorServiceImpl implements IStandardColorService {
    @Autowired
    private StandardColorMapper standardColorMapper;

    /**
     * 查询标准色卡
     *
     * @param id 标准色卡主键
     * @return 标准色卡
     */
    @Override
    public StandardColor selectStandardColorById(Long id) {
        return standardColorMapper.selectStandardColorById(id);
    }

    /**
     * 查询标准色卡列表
     *
     * @param standardColor 标准色卡
     * @return 标准色卡
     */
    @Override
    public List<StandardColor> selectStandardColorList(StandardColor standardColor) {
        return standardColorMapper.selectStandardColorList(standardColor);
    }

    /**
     * 新增标准色卡
     *
     * @param standardColor 标准色卡
     * @return 结果
     */
    @Override
    public int insertStandardColor(StandardColor standardColor) {
        standardColor.setCreateBy(SecurityUtils.getUserId().toString());
        standardColor.setCreateTime(DateUtils.getNowDate());
        return standardColorMapper.insertStandardColor(standardColor);
    }

    /**
     * 修改标准色卡
     *
     * @param standardColor 标准色卡
     * @return 结果
     */
    @Override
    public int updateStandardColor(StandardColor standardColor) {
        standardColor.setUpdateTime(DateUtils.getNowDate());
        return standardColorMapper.updateStandardColor(standardColor);
    }

    /**
     * 批量删除标准色卡
     *
     * @param ids 需要删除的标准色卡主键
     * @return 结果
     */
    @Override
    public int deleteStandardColorByIds(Long[] ids) {
        return standardColorMapper.deleteStandardColorByIds(ids);
    }

    /**
     * 删除标准色卡信息
     *
     * @param id 标准色卡主键
     * @return 结果
     */
    @Override
    public int deleteStandardColorById(Long id) {
        return standardColorMapper.deleteStandardColorById(id);
    }
}
