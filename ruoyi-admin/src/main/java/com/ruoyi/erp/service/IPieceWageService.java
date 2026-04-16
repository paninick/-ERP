package com.ruoyi.erp.service;

import com.ruoyi.erp.domain.PieceWage;

import java.util.List;

/**
 * 计件工资汇总Service接口
 *
 * @author zhangmingjian
 */
public interface IPieceWageService {
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
     * 批量删除计件工资汇总
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePieceWageByIds(Long[] ids);

    /**
     * 删除计件工资汇总信息
     *
     * @param id 计件工资汇总ID
     * @return 结果
     */
    public int deletePieceWageById(Long id);

    /**
     * 根据员工ID和月份检查是否已存在
     */
    public int countByEmployeeAndMonth(Long employeeId, String wageMonth);
}
