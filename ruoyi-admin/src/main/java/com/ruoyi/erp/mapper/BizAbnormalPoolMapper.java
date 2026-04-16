package com.ruoyi.erp.mapper;

import com.ruoyi.erp.domain.BizAbnormalPool;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 业务异常池Mapper接口
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
@Mapper
public interface BizAbnormalPoolMapper {
    /**
     * 查询业务异常池
     *
     * @param id 业务异常池ID
     * @return 业务异常池
     */
    public BizAbnormalPool selectBizAbnormalPoolById(Long id);

    /**
     * 查询业务异常池列表
     *
     * @param bizAbnormalPool 业务异常池
     * @return 业务异常池集合
     */
    public List<BizAbnormalPool> selectBizAbnormalPoolList(BizAbnormalPool bizAbnormalPool);

    /**
     * 新增业务异常池
     *
     * @param bizAbnormalPool 业务异常池
     * @return 结果
     */
    public int insertBizAbnormalPool(BizAbnormalPool bizAbnormalPool);

    /**
     * 修改业务异常池
     *
     * @param bizAbnormalPool 业务异常池
     * @return 结果
     */
    public int updateBizAbnormalPool(BizAbnormalPool bizAbnormalPool);

    /**
     * 删除业务异常池
     *
     * @param id 业务异常池ID
     * @return 结果
     */
    public int deleteBizAbnormalPoolById(Long id);

    /**
     * 批量删除业务异常池
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizAbnormalPoolByIds(Long[] ids);

    /**
     * 查询指定业务是否存在未处理异常
     *
     * @param bizType 业务类型
     * @param bizId 业务ID
     * @return 数量
     */
    public int countUnhandledByBiz(String bizType, Long bizId);

    /**
     * 统计所有未处理异常数量
     *
     * @return 未处理数量
     */
    public int countUnhandled();
}
