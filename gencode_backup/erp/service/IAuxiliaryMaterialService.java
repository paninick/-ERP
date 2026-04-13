package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.AuxiliaryMaterial;

/**
 * 辅料Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface IAuxiliaryMaterialService {
    /**
     * 查询辅料
     *
     * @param id 辅料主键
     * @return 辅料
     */
    AuxiliaryMaterial selectAuxiliaryMaterialById(Long id);

    /**
     * 查询辅料列表
     *
     * @param auxiliaryMaterial 辅料
     * @return 辅料集合
     */
    List<AuxiliaryMaterial> selectAuxiliaryMaterialList(AuxiliaryMaterial auxiliaryMaterial);

    /**
     * 新增辅料
     *
     * @param auxiliaryMaterial 辅料
     * @return 结果
     */
    int insertAuxiliaryMaterial(AuxiliaryMaterial auxiliaryMaterial);

    /**
     * 修改辅料
     *
     * @param auxiliaryMaterial 辅料
     * @return 结果
     */
    int updateAuxiliaryMaterial(AuxiliaryMaterial auxiliaryMaterial);

    /**
     * 批量删除辅料
     *
     * @param ids 需要删除的辅料主键集合
     * @return 结果
     */
    int deleteAuxiliaryMaterialByIds(Long[] ids);

    /**
     * 删除辅料信息
     *
     * @param id 辅料主键
     * @return 结果
     */
    int deleteAuxiliaryMaterialById(Long id);

    /**
     * 新增辅料批量
     *
     * @param list 辅料
     * @return 结果
     */
    int insertAuxiliaryMaterialBatch(List<AuxiliaryMaterial> list);
}
