package com.ruoyi.erp.service.impl;

import com.ruoyi.erp.domain.PieceWageDetail;
import com.ruoyi.erp.mapper.PieceWageDetailMapper;
import com.ruoyi.erp.service.IPieceWageDetailService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 计件工资明细Service业务层处理
 *
 * @author zhangmingjian
 */
@Service
public class PieceWageDetailServiceImpl implements IPieceWageDetailService {

    @Autowired
    private PieceWageDetailMapper pieceWageDetailMapper;

    /**
     * 查询计件工资明细
     *
     * @param id 计件工资明细ID
     * @return 计件工资明细
     */
    @Override
    public PieceWageDetail selectPieceWageDetailById(Long id) {
        return pieceWageDetailMapper.selectPieceWageDetailById(id);
    }

    /**
     * 查询计件工资明细列表
     *
     * @param pieceWageDetail 计件工资明细
     * @return 计件工资明细
     */
    @Override
    public List<PieceWageDetail> selectPieceWageDetailList(PieceWageDetail pieceWageDetail) {
        return pieceWageDetailMapper.selectPieceWageDetailList(pieceWageDetail);
    }

    /**
     * 新增计件工资明细
     *
     * @param pieceWageDetail 计件工资明细
     * @return 结果
     */
    @Override
    public int insertPieceWageDetail(PieceWageDetail pieceWageDetail) {
        pieceWageDetail.setCreateBy(SecurityUtils.getUsername());
        pieceWageDetail.setCreateTime(DateUtils.getNowDate());
        pieceWageDetail.setUpdateBy(SecurityUtils.getUsername());
        pieceWageDetail.setUpdateTime(DateUtils.getNowDate());
        return pieceWageDetailMapper.insertPieceWageDetail(pieceWageDetail);
    }

    /**
     * 批量新增计件工资明细
     *
     * @param list 明细列表
     * @return 结果
     */
    @Override
    public int batchInsertPieceWageDetail(List<PieceWageDetail> list) {
        for (PieceWageDetail detail : list) {
            detail.setCreateBy(SecurityUtils.getUsername());
            detail.setCreateTime(DateUtils.getNowDate());
            detail.setUpdateBy(SecurityUtils.getUsername());
            detail.setUpdateTime(DateUtils.getNowDate());
        }
        return pieceWageDetailMapper.batchInsertPieceWageDetail(list);
    }

    /**
     * 修改计件工资明细
     *
     * @param pieceWageDetail 计件工资明细
     * @return 结果
     */
    @Override
    public int updatePieceWageDetail(PieceWageDetail pieceWageDetail) {
        pieceWageDetail.setUpdateBy(SecurityUtils.getUsername());
        pieceWageDetail.setUpdateTime(DateUtils.getNowDate());
        return pieceWageDetailMapper.updatePieceWageDetail(pieceWageDetail);
    }

    /**
     * 批量删除计件工资明细
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePieceWageDetailByIds(Long[] ids) {
        return pieceWageDetailMapper.deletePieceWageDetailByIds(ids);
    }

    /**
     * 删除计件工资明细信息
     *
     * @param id 计件工资明细ID
     * @return 结果
     */
    @Override
    public int deletePieceWageDetailById(Long id) {
        return pieceWageDetailMapper.deletePieceWageDetailById(id);
    }

    @Override
    public int deletePieceWageDetailByWageId(Long wageId) {
        return pieceWageDetailMapper.deletePieceWageDetailByWageId(wageId);
    }

    @Override
    public List<PieceWageDetail> selectByWageId(Long wageId) {
        return pieceWageDetailMapper.selectByWageId(wageId);
    }
}
