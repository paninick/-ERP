package com.ruoyi.erp.mapper;

import com.ruoyi.erp.domain.PieceWageDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 计件工资明细Mapper接口
 *
 * @author zhangmingjian
 */
@Mapper
public interface PieceWageDetailMapper {
    /**
     * 查询计件工资明细
     *
     * @param id 计件工资明细ID
     * @return 计件工资明细
     */
    public PieceWageDetail selectPieceWageDetailById(Long id);

    /**
     * 查询计件工资明细列表
     *
     * @param pieceWageDetail 计件工资明细
     * @return 计件工资明细集合
     */
    public List<PieceWageDetail> selectPieceWageDetailList(PieceWageDetail pieceWageDetail);

    /**
     * 新增计件工资明细
     *
     * @param pieceWageDetail 计件工资明细
     * @return 结果
     */
    public int insertPieceWageDetail(PieceWageDetail pieceWageDetail);

    /**
     * 批量新增计件工资明细
     *
     * @param list 明细列表
     * @return 结果
     */
    public int batchInsertPieceWageDetail(@Param("list") List<PieceWageDetail> list);

    /**
     * 修改计件工资明细
     *
     * @param pieceWageDetail 计件工资明细
     * @return 结果
     */
    public int updatePieceWageDetail(PieceWageDetail pieceWageDetail);

    /**
     * 删除计件工资明细
     *
     * @param id 计件工资明细ID
     * @return 结果
     */
    public int deletePieceWageDetailById(Long id);

    /**
     * 批量删除计件工资明细
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePieceWageDetailByIds(Long[] ids);

    /**
     * 根据工资汇总ID删除明细
     *
     * @param wageId 工资汇总ID
     * @return 结果
     */
    public int deletePieceWageDetailByWageId(Long wageId);

    /**
     * 根据工资汇总ID查询明细
     *
     * @param wageId 工资汇总ID
     * @return 明细列表
     */
    public List<PieceWageDetail> selectByWageId(Long wageId);
}
