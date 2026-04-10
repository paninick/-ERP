package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.TErpSampleNoticeMaterialMapper;
import com.ruoyi.erp.domain.TErpSampleNoticeMaterial;
import com.ruoyi.erp.service.ITErpSampleNoticeMaterialService;

/**
 * 打样通知-面辅料要求Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
@RequiredArgsConstructor
@Service
public class TErpSampleNoticeMaterialServiceImpl implements ITErpSampleNoticeMaterialService {
    private final TErpSampleNoticeMaterialMapper tErpSampleNoticeMaterialMapper;

    /**
     * 查询打样通知-面辅料要求
     *
     * @param id 打样通知-面辅料要求主键
     * @return 打样通知-面辅料要求
     */
    @Override
    public TErpSampleNoticeMaterial selectTErpSampleNoticeMaterialById(Long id) {
        return tErpSampleNoticeMaterialMapper.selectTErpSampleNoticeMaterialById(id);
    }

    /**
     * 查询打样通知-面辅料要求列表
     *
     * @param tErpSampleNoticeMaterial 打样通知-面辅料要求
     * @return 打样通知-面辅料要求
     */
    @Override
    public List<TErpSampleNoticeMaterial> selectTErpSampleNoticeMaterialList(TErpSampleNoticeMaterial tErpSampleNoticeMaterial) {
        return tErpSampleNoticeMaterialMapper.selectTErpSampleNoticeMaterialList(tErpSampleNoticeMaterial);
    }

    /**
     * 新增打样通知-面辅料要求
     *
     * @param tErpSampleNoticeMaterial 打样通知-面辅料要求
     * @return 结果
     */
    @Override
    public int insertTErpSampleNoticeMaterial(TErpSampleNoticeMaterial tErpSampleNoticeMaterial) {
        tErpSampleNoticeMaterial.setCreateBy(SecurityUtils.getUserIdStr());
        tErpSampleNoticeMaterial.setCreateTime(DateUtils.getNowDate());
        return tErpSampleNoticeMaterialMapper.insertTErpSampleNoticeMaterial(tErpSampleNoticeMaterial);
    }

    /**
     * 修改打样通知-面辅料要求
     *
     * @param tErpSampleNoticeMaterial 打样通知-面辅料要求
     * @return 结果
     */
    @Override
    public int updateTErpSampleNoticeMaterial(TErpSampleNoticeMaterial tErpSampleNoticeMaterial) {
        tErpSampleNoticeMaterial.setUpdateTime(DateUtils.getNowDate());
        return tErpSampleNoticeMaterialMapper.updateTErpSampleNoticeMaterial(tErpSampleNoticeMaterial);
    }

    /**
     * 批量删除打样通知-面辅料要求
     *
     * @param ids 需要删除的打样通知-面辅料要求主键
     * @return 结果
     */
    @Override
    public int deleteTErpSampleNoticeMaterialByIds(Long[] ids) {
        return tErpSampleNoticeMaterialMapper.deleteTErpSampleNoticeMaterialByIds(ids);
    }

    /**
     * 删除打样通知-面辅料要求信息
     *
     * @param id 打样通知-面辅料要求主键
     * @return 结果
     */
    @Override
    public int deleteTErpSampleNoticeMaterialById(Long id) {
        return tErpSampleNoticeMaterialMapper.deleteTErpSampleNoticeMaterialById(id);
    }

    /**
     * 批量新增打样通知-面辅料要求
     *
     * @param list 打样通知-面辅料要求
     * @return 结果
     */
    @Override
    public int insertTErpSampleNoticeMaterialBatch(List<TErpSampleNoticeMaterial> list) {
        return tErpSampleNoticeMaterialMapper.insertTErpSampleNoticeMaterialBatch(list);
    }
}
