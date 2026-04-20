package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.AuxPropertyValue;
import org.apache.ibatis.annotations.Param;

/**
 * 辅助属性值Mapper接口
 *
 * @author ruoyi
 * @date 2026-04-16
 */
public interface AuxPropertyValueMapper {
    /**
     * 查询辅助属性值
     *
     * @param id 辅助属性值主键
     * @return 辅助属性值
     */
    public AuxPropertyValue selectAuxPropertyValueById(Long id);

    /**
     * 根据ID列表批量查询辅助属性值
     *
     * @param ids ID列表
     * @return 辅助属性值集合
     */
    public List<AuxPropertyValue> selectAuxPropertyValueByIds(@Param("ids") List<Long> ids);

    /**
     * 查询辅助属性值列表
     *
     * @param auxPropertyValue 辅助属性值
     * @return 辅助属性值集合
     */
    public List<AuxPropertyValue> selectAuxPropertyValueList(AuxPropertyValue auxPropertyValue);

    /**
     * 新增辅助属性值
     *
     * @param auxPropertyValue 辅助属性值
     * @return 结果
     */
    public int insertAuxPropertyValue(AuxPropertyValue auxPropertyValue);

    /**
     * 修改辅助属性值
     *
     * @param auxPropertyValue 辅助属性值
     * @return 结果
     */
    public int updateAuxPropertyValue(AuxPropertyValue auxPropertyValue);

    /**
     * 删除辅助属性值
     *
     * @param id 辅助属性值主键
     * @return 结果
     */
    public int deleteAuxPropertyValueById(Long id);

    /**
     * 批量删除辅助属性值
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAuxPropertyValueByIds(Long[] ids);
}
