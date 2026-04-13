package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.MainMaterial;

/**
 * 主料Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface MainMaterialMapper {
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
     * 删除主料
     *
     * @param id 主料主键
     * @return 结果
     */
    int deleteMainMaterialById(Long id);

    /**
     * 批量删除主料
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteMainMaterialByIds(Long[] ids);

    /**
     * 批量新增主料批量
     *
     * @param list 主料
     * @return 结果
     */
    int insertMainMaterialBatch(List<MainMaterial> list);

    /**
     * 根据主料编号查询主料
     *
     * @param mainMaterialNo 主料编号
     * @return 主料信息
     */
    MainMaterial selectMainMaterialByNo(String mainMaterialNo);

    /**
     * 安全新增主料（INSERT IGNORE，重复时忽略）
     *
     * @param mainMaterial 主料
     * @return 结果
     */
    int insertMainMaterialSafe(MainMaterial mainMaterial);

    /**
     * 安全批量新增主料（INSERT IGNORE，重复时忽略）
     *
     * @param list 主料列表
     * @return 结果
     */
    int insertMainMaterialBatchSafe(List<MainMaterial> list);
}
