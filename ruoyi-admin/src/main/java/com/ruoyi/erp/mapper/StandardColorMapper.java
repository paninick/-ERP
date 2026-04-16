package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.StandardColor;

/**
 * 标准色卡Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public interface StandardColorMapper {
    /**
     * 查询标准色卡
     *
     * @param id 标准色卡主键
     * @return 标准色卡
     */
    public StandardColor selectStandardColorById(Long id);

    /**
     * 查询标准色卡列表
     *
     * @param standardColor 标准色卡
     * @return 标准色卡集合
     */
    public List<StandardColor> selectStandardColorList(StandardColor standardColor);

    /**
     * 新增标准色卡
     *
     * @param standardColor 标准色卡
     * @return 结果
     */
    public int insertStandardColor(StandardColor standardColor);

    /**
     * 修改标准色卡
     *
     * @param standardColor 标准色卡
     * @return 结果
     */
    public int updateStandardColor(StandardColor standardColor);

    /**
     * 删除标准色卡
     *
     * @param id 标准色卡主键
     * @return 结果
     */
    public int deleteStandardColorById(Long id);

    /**
     * 批量删除标准色卡
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStandardColorByIds(Long[] ids);

    /**
     * 根据颜色编码查询
     *
     * @param colorCode 颜色编码
     * @return 标准色卡
     */
    public StandardColor selectStandardColorByCode(String colorCode);
}
