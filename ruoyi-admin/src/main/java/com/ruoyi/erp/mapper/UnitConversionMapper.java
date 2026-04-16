package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.UnitConversion;

/**
 * 单位换算Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
public interface UnitConversionMapper {
    /**
     * 查询单位换算
     *
     * @param id 单位换算主键
     * @return 单位换算
     */
    public UnitConversion selectUnitConversionById(Long id);

    /**
     * 查询单位换算列表
     *
     * @param unitConversion 单位换算
     * @return 单位换算集合
     */
    public List<UnitConversion> selectUnitConversionList(UnitConversion unitConversion);

    /**
     * 新增单位换算
     *
     * @param unitConversion 单位换算
     * @return 结果
     */
    public int insertUnitConversion(UnitConversion unitConversion);

    /**
     * 修改单位换算
     *
     * @param unitConversion 单位换算
     * @return 结果
     */
    public int updateUnitConversion(UnitConversion unitConversion);

    /**
     * 删除单位换算
     *
     * @param id 单位换算主键
     * @return 结果
     */
    public int deleteUnitConversionById(Long id);

    /**
     * 批量删除单位换算
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUnitConversionByIds(Long[] ids);

    /**
     * 根据物料ID查询单位换算
     *
     * @param materialId 物料ID
     * @return 单位换算
     */
    public UnitConversion selectUnitConversionByMaterialId(Long materialId);
}
