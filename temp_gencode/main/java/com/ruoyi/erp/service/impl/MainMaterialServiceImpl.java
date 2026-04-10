package com.ruoyi.erp.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.erp.mapper.MainMaterialMapper;
import com.ruoyi.erp.domain.MainMaterial;
import com.ruoyi.erp.service.IMainMaterialService;

/**
 * 主料Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
@RequiredArgsConstructor
@Service
public class MainMaterialServiceImpl implements IMainMaterialService {
    private final MainMaterialMapper mainMaterialMapper;

    /**
     * 查询主料
     *
     * @param id 主料主键
     * @return 主料
     */
    @Override
    public MainMaterial selectMainMaterialById(Long id) {
        return mainMaterialMapper.selectMainMaterialById(id);
    }

    /**
     * 查询主料列表
     *
     * @param mainMaterial 主料
     * @return 主料
     */
    @Override
    public List<MainMaterial> selectMainMaterialList(MainMaterial mainMaterial) {
        return mainMaterialMapper.selectMainMaterialList(mainMaterial);
    }

    /**
     * 新增主料
     *
     * @param mainMaterial 主料
     * @return 结果
     */
    @Override
    public int insertMainMaterial(MainMaterial mainMaterial) {
        mainMaterial.setCreateBy(SecurityUtils.getUserIdStr());
        mainMaterial.setCreateTime(DateUtils.getNowDate());
        return mainMaterialMapper.insertMainMaterial(mainMaterial);
    }

    /**
     * 修改主料
     *
     * @param mainMaterial 主料
     * @return 结果
     */
    @Override
    public int updateMainMaterial(MainMaterial mainMaterial) {
        mainMaterial.setUpdateTime(DateUtils.getNowDate());
        return mainMaterialMapper.updateMainMaterial(mainMaterial);
    }

    /**
     * 批量删除主料
     *
     * @param ids 需要删除的主料主键
     * @return 结果
     */
    @Override
    public int deleteMainMaterialByIds(Long[] ids) {
        return mainMaterialMapper.deleteMainMaterialByIds(ids);
    }

    /**
     * 删除主料信息
     *
     * @param id 主料主键
     * @return 结果
     */
    @Override
    public int deleteMainMaterialById(Long id) {
        return mainMaterialMapper.deleteMainMaterialById(id);
    }

    /**
     * 批量新增主料
     *
     * @param list 主料
     * @return 结果
     */
    @Override
    public int insertMainMaterialBatch(List<MainMaterial> list) {
        return mainMaterialMapper.insertMainMaterialBatch(list);
    }
}
