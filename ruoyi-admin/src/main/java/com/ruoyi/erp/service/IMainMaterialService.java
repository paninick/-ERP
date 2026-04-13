package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.MainMaterial;

/**
 * 主料Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface IMainMaterialService {
    /**
     * 查询主料
     *
     * @param id 主料主键
     * @return 主料
     */
    MainMaterial selectMainMaterialById(Long id);

    /**
     * 查询主料列表
     *
     * @param mainMaterial 主料
     * @return 主料集合
     */
    List<MainMaterial> selectMainMaterialList(MainMaterial mainMaterial);

    /**
     * 新增主料
     *
     * @param mainMaterial 主料
     * @return 结果
     */
    int insertMainMaterial(MainMaterial mainMaterial);

    /**
     * 修改主料
     *
     * @param mainMaterial 主料
     * @return 结果
     */
    int updateMainMaterial(MainMaterial mainMaterial);

    /**
     * 批量删除主料
     *
     * @param ids 需要删除的主料主键集合
     * @return 结果
     */
    int deleteMainMaterialByIds(Long[] ids);

    /**
     * 删除主料信息
     *
     * @param id 主料主键
     * @return 结果
     */
    int deleteMainMaterialById(Long id);

    /**
     * 新增主料批量
     *
     * @param list 主料
     * @return 结果
     */
    int insertMainMaterialBatch(List<MainMaterial> list);

    /**
     * 导入主料数据
     *
     * @param list 主料数据列表
     * @param updateSupport 是否更新支持，如果已存在则更新
     * @return 结果
     */
    String importMaterial(List<MainMaterial> list, boolean updateSupport);

    /**
     * 快速批量导入主料数据（使用INSERT IGNORE）
     *
     * @param list 主料数据列表
     * @return 结果信息
     */
    String importMaterialFast(List<MainMaterial> list);

    /**
     * 安全新增主料（INSERT IGNORE）
     *
     * @param mainMaterial 主料
     * @return 结果
     */
    int insertMainMaterialSafe(MainMaterial mainMaterial);

    /**
     * 安全批量新增主料（INSERT IGNORE）
     *
     * @param list 主料列表
     * @return 结果
     */
    int insertMainMaterialBatchSafe(List<MainMaterial> list);
}
