package com.ruoyi.erp.mapper;

import java.util.List;
import com.ruoyi.erp.domain.TErpSampleNoticeMaterial;

/**
 * 打样通知-面辅料要求Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
public interface TErpSampleNoticeMaterialMapper {
    /**
     * 查询打样通知-面辅料要求
     *
     * @param id 打样通知-面辅料要求主键
     * @return 打样通知-面辅料要求
     */
    TErpSampleNoticeMaterial selectTErpSampleNoticeMaterialById(Long id);

    /**
     * 查询打样通知-面辅料要求列表
     *
     * @param tErpSampleNoticeMaterial 打样通知-面辅料要求
     * @return 打样通知-面辅料要求集合
     */
    List<TErpSampleNoticeMaterial> selectTErpSampleNoticeMaterialList(TErpSampleNoticeMaterial tErpSampleNoticeMaterial);

    /**
     * 新增打样通知-面辅料要求
     *
     * @param tErpSampleNoticeMaterial 打样通知-面辅料要求
     * @return 结果
     */
    int insertTErpSampleNoticeMaterial(TErpSampleNoticeMaterial tErpSampleNoticeMaterial);

    /**
     * 修改打样通知-面辅料要求
     *
     * @param tErpSampleNoticeMaterial 打样通知-面辅料要求
     * @return 结果
     */
    int updateTErpSampleNoticeMaterial(TErpSampleNoticeMaterial tErpSampleNoticeMaterial);

    /**
     * 删除打样通知-面辅料要求
     *
     * @param id 打样通知-面辅料要求主键
     * @return 结果
     */
    int deleteTErpSampleNoticeMaterialById(Long id);

    /**
     * 批量删除打样通知-面辅料要求
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteTErpSampleNoticeMaterialByIds(Long[] ids);

    /**
     * 批量新增打样通知-面辅料要求批量
     *
     * @param list 打样通知-面辅料要求
     * @return 结果
     */
    int insertTErpSampleNoticeMaterialBatch(List<TErpSampleNoticeMaterial> list);
}
