package com.ruoyi.erp.service;

import com.ruoyi.erp.domain.Check;

import java.util.List;

/**
 * 大货核版Service接口
 *
 * @author ruoyi
 * @date 2026-04-10
 */
public interface ICheckService
{
    /**
     * 查询大货核版
     *
     * @param checkId 大货核版主键
     * @return 大货核版
     */
    public Check selectCheckByCheckId(Long checkId);

    /**
     * 查询大货核版列表
     *
     * @param check 大货核版
     * @return 大货核版集合
     */
    public List<Check> selectCheckList(Check check);

    /**
     * 新增大货核版
     *
     * @param check 大货核版
     * @return 结果
     */
    public int insertCheck(Check check);

    /**
     * 修改大货核版
     *
     * @param check 大货核版
     * @return 结果
     */
    public int updateCheck(Check check);

    /**
     * 删除大货核版信息
     *
     * @param checkId 大货核版主键
     * @return 结果
     */
    public int deleteCheckByCheckId(Long checkId);

    /**
     * 批量删除大货核版信息
     *
     * @param checkIds 需要删除的大货核版主键集合
     * @return 结果
     */
    public int deleteCheckByIds(Long[] checkIds);

    /**
     * 审批大货核版
     *
     * @param check 大货核版
     * @return 结果
     */
    public int approveCheck(Check check);

    /**
     * 导入大货核版数据
     *
     * @param checkList 大货核版数据列表
     * @param updateSupport 是否更新支持，如果已存在，则进行更新
     * @return 结果
     */
    String importCheck(List<Check> checkList, boolean updateSupport);
}
