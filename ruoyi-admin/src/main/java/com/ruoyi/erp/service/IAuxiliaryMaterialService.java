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

    /**
     * 导入辅料数据
     *
     * @param list 辅料数据列表
     * @param updateSupport 是否更新支持，如果已存在则更新
     * @return 结果
     */
    String importAuxiliary(List<AuxiliaryMaterial> list, boolean updateSupport);

    /**
     * 快速批量导入辅料数据
     *
     * @param list 辅料数据列表
     * @return 结果
     */
    String importAuxiliaryFast(List<AuxiliaryMaterial> list);

    /**
     * 安全插入辅料
     *
     * @param auxiliaryMaterial 辅料
     * @return 结果
     */
    int insertAuxiliaryMaterialSafe(AuxiliaryMaterial auxiliaryMaterial);

    /**
     * 批量安全插入辅料
     *
     * @param list 辅料列表
     * @return 结果
     */
    int insertAuxiliaryMaterialBatchSafe(List<AuxiliaryMaterial> list);
}
