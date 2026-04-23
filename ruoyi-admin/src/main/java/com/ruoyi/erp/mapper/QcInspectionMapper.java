package com.ruoyi.erp.mapper;

import java.util.List;
import java.util.Map;
import com.ruoyi.erp.domain.QcInspection;
import org.apache.ibatis.annotations.Param;

public interface QcInspectionMapper {
    QcInspection selectById(Long id);
    List<QcInspection> selectList(QcInspection query);
    int insert(QcInspection inspection);
    int update(QcInspection inspection);
    int deleteById(Long id);
    int updateStatus(@Param("id") Long id, @Param("status") String status, @Param("rejectReason") String rejectReason);
    Map<String, Object> selectStats(@Param("factoryId") Long factoryId);
    List<Map<String, Object>> selectDefectReasons(@Param("factoryId") Long factoryId);
}
