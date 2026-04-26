package com.ruoyi.erp.mapper;

import com.ruoyi.erp.domain.PieceWage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Piece wage mapper.
 */
@Mapper
public interface PieceWageMapper {
    PieceWage selectPieceWageById(Long id);

    List<PieceWage> selectPieceWageList(PieceWage pieceWage);

    int insertPieceWage(PieceWage pieceWage);

    int updatePieceWage(PieceWage pieceWage);

    int deletePieceWageById(Long id);

    int deletePieceWageByIds(Long[] ids);

    int countByEmployeeAndMonth(@Param("employeeId") Long employeeId, @Param("wageMonth") String wageMonth);

    List<Map<String, Object>> selectEventSummaryByMonth(@Param("wageMonth") String wageMonth);
}
