package com.ruoyi.erp.service;

import java.util.List;
import com.ruoyi.erp.domain.MainMaterial;

/**
 * 主料Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-02
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
}
