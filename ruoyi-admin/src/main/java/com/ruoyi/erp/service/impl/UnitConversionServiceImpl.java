package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.UnitConversionMapper;
import com.ruoyi.erp.domain.UnitConversion;
import com.ruoyi.erp.service.IUnitConversionService;

/**
 * 单位换算Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
@Service
public class UnitConversionServiceImpl implements IUnitConversionService {
    @Autowired
    private UnitConversionMapper unitConversionMapper;

    /**
     * 查询单位换算
     *
     * @param id 单位换算主键
     * @return 单位换算
     */
    @Override
    public UnitConversion selectUnitConversionById(Long id) {
        return unitConversionMapper.selectUnitConversionById(id);
    }

    /**
     * 查询单位换算列表
     *
     * @param unitConversion 单位换算
     * @return 单位换算
     */
    @Override
    public List<UnitConversion> selectUnitConversionList(UnitConversion unitConversion) {
        return unitConversionMapper.selectUnitConversionList(unitConversion);
    }

    /**
     * 新增单位换算
     *
     * @param unitConversion 单位换算
     * @return 结果
     */
    @Override
    public int insertUnitConversion(UnitConversion unitConversion) {
        unitConversion.setCreateBy(SecurityUtils.getUserId().toString());
        unitConversion.setCreateTime(DateUtils.getNowDate());
        return unitConversionMapper.insertUnitConversion(unitConversion);
    }

    /**
     * 修改单位换算
     *
     * @param unitConversion 单位换算
     * @return 结果
     */
    @Override
    public int updateUnitConversion(UnitConversion unitConversion) {
        unitConversion.setUpdateTime(DateUtils.getNowDate());
        return unitConversionMapper.updateUnitConversion(unitConversion);
    }

    /**
     * 批量删除单位换算
     *
     * @param ids 需要删除的单位换算主键
     * @return 结果
     */
    @Override
    public int deleteUnitConversionByIds(Long[] ids) {
        return unitConversionMapper.deleteUnitConversionByIds(ids);
    }

    /**
     * 删除单位换算信息
     *
     * @param id 单位换算主键
     * @return 结果
     */
    @Override
    public int deleteUnitConversionById(Long id) {
        return unitConversionMapper.deleteUnitConversionById(id);
    }

    @Override
    public UnitConversion selectUnitConversionByMaterialId(Long materialId) {
        return unitConversionMapper.selectUnitConversionByMaterialId(materialId);
    }
}
