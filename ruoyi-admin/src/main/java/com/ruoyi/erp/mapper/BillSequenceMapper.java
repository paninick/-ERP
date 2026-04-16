package com.ruoyi.erp.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;

/**
 * 单据号sequence Mapper
 *
 * @author ruoyi
 * @date 2026-04-16
 */
@Repository
public interface BillSequenceMapper {

    /**
     * 查询当前值
     */
    Long selectCurrentVal(@Param("seqDate") Date seqDate, @Param("billType") String billType);

    /**
     * 插入新记录
     */
    int insertSequence(@Param("seqDate") Date seqDate, @Param("billType") String billType, @Param("currentVal") Long currentVal);

    /**
     * 递增
     */
    int incrementSequence(@Param("seqDate") Date seqDate, @Param("billType") String billType);

    /**
     * upsert 更新
     */
    int upsertSequence(@Param("seqDate") Date seqDate, @Param("billType") String billType, @Param("currentVal") Long currentVal);
}
