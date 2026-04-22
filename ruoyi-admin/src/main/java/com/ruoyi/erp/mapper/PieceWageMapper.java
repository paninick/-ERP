package com.ruoyi.erp.mapper;

import com.ruoyi.erp.domain.PieceWage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 计件工资汇总Mapper接口
 *
 * @author zhangmingjian
 */
@Mapper
public interface PieceWageMapper {
    /**
     * 查询计件工资汇总
     *
     * @param id 计件工资汇总ID
     * @return 计件工资汇总
     */
    public PieceWage selectPieceWageById(Long id);

    /**
     * 查询计件工资汇总列表
     *
     * @param pieceWage 计件工资汇总
     * @return 计件工资汇总集合
     */
    public List<PieceWage> selectPieceWageList(PieceWage pieceWage);

    /**
     * 新增计件工资汇总
     *
     * @param pieceWage 计件工资汇总
     * @return 结果
     */
    public int insertPieceWage(PieceWage pieceWage);

    /**
     * 修改计件工资汇总
     *
     * @param pieceWage 计件工资汇总
     * @return 结果
     */
    public int updatePieceWage(PieceWage pieceWage);

    /**
     * 删除计件工资汇总
     *
     * @param id 计件工资汇总ID
     * @return 结果
     */
    public int deletePieceWageById(Long id);

    /**
     * 批量删除计件工资汇总
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePieceWageByIds(Long[] ids);

    /**
     * 根据员工ID和月份查询是否已存在汇总
     *
     * @param employeeId 员工ID
     * @param wageMonth 月份
     * @return 存在返回数量
     */
    public int countByEmployeeAndMonth(@Param("employeeId") Long employeeId, @Param("wageMonth") String wageMonth);

    /**
     * 按月份查询工资结算汇总视图
     * 返回每个员工×工序的产量和应得工资明细，用于 autoGenerateWageByMonth
     */
    @Select("SELECT employee_id, employee_name, process_id, process_no, process_name, " +
            "total_ok_qty, unit_price, should_wage " +
            "FROM v_erp_piece_wage_summary " +
            "WHERE wage_month = #{wageMonth}")
    List<Map<String, Object>> selectSummaryByMonth(@Param("wageMonth") String wageMonth);
}
