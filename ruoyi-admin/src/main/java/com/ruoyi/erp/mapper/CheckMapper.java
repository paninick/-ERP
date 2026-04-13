package com.ruoyi.erp.mapper;

import com.ruoyi.erp.domain.Check;

import java.util.List;

/**
 * 大货核版Mapper接口
 *
 * @author ruoyi
 * @date 2026-04-10
 */
public interface CheckMapper
{
    /**
     * 查询大货核版
     *
     * @param checkId 大货核版主键
     * @return 大货核版
     */
    public Check selectCheckByCheckId(Long checkId);

    /**
     * 根据核版编号查询大货核版
     *
     * @param checkNo 核版编号
     * @return 大货核版
     */
    public Check selectCheckByCheckNo(String checkNo);

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
     * 删除大货核版
     *
     * @param checkId 大货核版主键
     * @return 结果
     */
    public int deleteCheckByCheckId(Long checkId);

    /**
     * 批量删除大货核版
     *
     * @param checkIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCheckByCheckIds(Long[] checkIds);
}
