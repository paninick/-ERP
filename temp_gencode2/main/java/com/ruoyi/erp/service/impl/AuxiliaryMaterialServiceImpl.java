package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.AuxiliaryMaterialMapper;
import com.ruoyi.erp.domain.AuxiliaryMaterial;
import com.ruoyi.erp.service.IAuxiliaryMaterialService;

/**
 * 辅料Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
@RequiredArgsConstructor
@Service
public class AuxiliaryMaterialServiceImpl implements IAuxiliaryMaterialService {
    private final AuxiliaryMaterialMapper auxiliaryMaterialMapper;

    /**
     * 查询辅料
     *
     * @param id 辅料主键
     * @return 辅料
     */
    @Override
    public AuxiliaryMaterial selectAuxiliaryMaterialById(Long id) {
        return auxiliaryMaterialMapper.selectAuxiliaryMaterialById(id);
    }

    /**
     * 查询辅料列表
     *
     * @param auxiliaryMaterial 辅料
     * @return 辅料
     */
    @Override
    public List<AuxiliaryMaterial> selectAuxiliaryMaterialList(AuxiliaryMaterial auxiliaryMaterial) {
        return auxiliaryMaterialMapper.selectAuxiliaryMaterialList(auxiliaryMaterial);
    }

    /**
     * 新增辅料
     *
     * @param auxiliaryMaterial 辅料
     * @return 结果
     */
    @Override
    public int insertAuxiliaryMaterial(AuxiliaryMaterial auxiliaryMaterial) {
        auxiliaryMaterial.setCreateBy(SecurityUtils.getUserIdStr());
        auxiliaryMaterial.setCreateTime(DateUtils.getNowDate());
        return auxiliaryMaterialMapper.insertAuxiliaryMaterial(auxiliaryMaterial);
    }

    /**
     * 修改辅料
     *
     * @param auxiliaryMaterial 辅料
     * @return 结果
     */
    @Override
    public int updateAuxiliaryMaterial(AuxiliaryMaterial auxiliaryMaterial) {
        auxiliaryMaterial.setUpdateTime(DateUtils.getNowDate());
        return auxiliaryMaterialMapper.updateAuxiliaryMaterial(auxiliaryMaterial);
    }

    /**
     * 批量删除辅料
     *
     * @param ids 需要删除的辅料主键
     * @return 结果
     */
    @Override
    public int deleteAuxiliaryMaterialByIds(Long[] ids) {
        return auxiliaryMaterialMapper.deleteAuxiliaryMaterialByIds(ids);
    }

    /**
     * 删除辅料信息
     *
     * @param id 辅料主键
     * @return 结果
     */
    @Override
    public int deleteAuxiliaryMaterialById(Long id) {
        return auxiliaryMaterialMapper.deleteAuxiliaryMaterialById(id);
    }

    /**
     * 批量新增辅料
     *
     * @param list 辅料
     * @return 结果
     */
    @Override
    public int insertAuxiliaryMaterialBatch(List<AuxiliaryMaterial> list) {
        return auxiliaryMaterialMapper.insertAuxiliaryMaterialBatch(list);
    }
}
