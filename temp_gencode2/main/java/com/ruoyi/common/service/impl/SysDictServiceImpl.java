package com.ruoyi.common.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.mapper.SysDictMapper;
import com.ruoyi.common.domain.SysDict;
import com.ruoyi.common.service.ISysDictService;

/**
 * 字典数据Service业务层处理
 *
 * @author zhangmingjian
 * @date 2026-04-02
 */
@RequiredArgsConstructor
@Service
public class SysDictServiceImpl implements ISysDictService {
    private final SysDictMapper sysDictMapper;

    /**
     * 查询字典数据
     *
     * @param id 字典数据主键
     * @return 字典数据
     */
    @Override
    public SysDict selectSysDictById(Long id) {
        return sysDictMapper.selectSysDictById(id);
    }

    /**
     * 查询字典数据列表
     *
     * @param sysDict 字典数据
     * @return 字典数据
     */
    @Override
    public List<SysDict> selectSysDictList(SysDict sysDict) {
        return sysDictMapper.selectSysDictList(sysDict);
    }

    /**
     * 新增字典数据
     *
     * @param sysDict 字典数据
     * @return 结果
     */
    @Override
    public int insertSysDict(SysDict sysDict) {
        sysDict.setCreateBy(SecurityUtils.getUserIdStr());
        sysDict.setCreateTime(DateUtils.getNowDate());
        return sysDictMapper.insertSysDict(sysDict);
    }

    /**
     * 修改字典数据
     *
     * @param sysDict 字典数据
     * @return 结果
     */
    @Override
    public int updateSysDict(SysDict sysDict) {
        sysDict.setUpdateTime(DateUtils.getNowDate());
        return sysDictMapper.updateSysDict(sysDict);
    }

    /**
     * 批量删除字典数据
     *
     * @param ids 需要删除的字典数据主键
     * @return 结果
     */
    @Override
    public int deleteSysDictByIds(Long[] ids) {
        return sysDictMapper.deleteSysDictByIds(ids);
    }

    /**
     * 删除字典数据信息
     *
     * @param id 字典数据主键
     * @return 结果
     */
    @Override
    public int deleteSysDictById(Long id) {
        return sysDictMapper.deleteSysDictById(id);
    }

    /**
     * 批量新增字典数据
     *
     * @param list 字典数据
     * @return 结果
     */
    @Override
    public int insertSysDictBatch(List<SysDict> list) {
        return sysDictMapper.insertSysDictBatch(list);
    }
}
