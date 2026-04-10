package com.ruoyi.common.service;

import java.util.List;
import com.ruoyi.common.domain.SysDict;

/**
 * 字典数据Service接口
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
public interface ISysDictService {
    /**
     * 查询字典数据
     *
     * @param id 字典数据主键
     * @return 字典数据
     */
    SysDict selectSysDictById(Long id);

    /**
     * 查询字典数据列表
     *
     * @param sysDict 字典数据
     * @return 字典数据集合
     */
    List<SysDict> selectSysDictList(SysDict sysDict);

    /**
     * 新增字典数据
     *
     * @param sysDict 字典数据
     * @return 结果
     */
    int insertSysDict(SysDict sysDict);

    /**
     * 修改字典数据
     *
     * @param sysDict 字典数据
     * @return 结果
     */
    int updateSysDict(SysDict sysDict);

    /**
     * 批量删除字典数据
     *
     * @param ids 需要删除的字典数据主键集合
     * @return 结果
     */
    int deleteSysDictByIds(Long[] ids);

    /**
     * 删除字典数据信息
     *
     * @param id 字典数据主键
     * @return 结果
     */
    int deleteSysDictById(Long id);

    /**
     * 新增字典数据批量
     *
     * @param list 字典数据
     * @return 结果
     */
    int insertSysDictBatch(List<SysDict> list);
}
