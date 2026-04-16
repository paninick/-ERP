package com.ruoyi.erp.controller;

import com.ruoyi.erp.domain.PieceWageDetail;
import com.ruoyi.erp.service.IPieceWageDetailService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 计件工资明细Controller
 *
 * @author zhangmingjian
 */
@RestController
@RequestMapping("/erp/piecewagedetail")
public class PieceWageDetailController extends BaseController {

    @Autowired
    private IPieceWageDetailService pieceWageDetailService;

    /**
     * 查询计件工资明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:piecewagedetail:list')")
    @GetMapping("/list")
    public TableDataInfo list(PieceWageDetail pieceWageDetail) {
        startPage();
        List<PieceWageDetail> list = pieceWageDetailService.selectPieceWageDetailList(pieceWageDetail);
        return getDataTable(list);
    }

    /**
     * 根据工资汇总ID查询明细
     */
    @PreAuthorize("@ss.hasPermi('erp:piecewagedetail:list')")
    @GetMapping("/listByWage/{wageId}")
    public AjaxResult listByWage(@PathVariable Long wageId) {
        List<PieceWageDetail> list = pieceWageDetailService.selectByWageId(wageId);
        return AjaxResult.success(list);
    }

    /**
     * 导出计件工资明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:piecewagedetail:export')")
    @Log(title = "计件工资明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PieceWageDetail pieceWageDetail) {
        List<PieceWageDetail> list = pieceWageDetailService.selectPieceWageDetailList(pieceWageDetail);
        ExcelUtil<PieceWageDetail> util = new ExcelUtil<PieceWageDetail>(PieceWageDetail.class);
        util.exportExcel(response, list, "计件工资明细数据");
    }

    /**
     * 获取计件工资明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:piecewagedetail:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(pieceWageDetailService.selectPieceWageDetailById(id));
    }

    /**
     * 新增计件工资明细
     */
    @PreAuthorize("@ss.hasPermi('erp:piecewagedetail:add')")
    @Log(title = "计件工资明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PieceWageDetail pieceWageDetail) {
        return toAjax(pieceWageDetailService.insertPieceWageDetail(pieceWageDetail));
    }

    /**
     * 修改计件工资明细
     */
    @PreAuthorize("@ss.hasPermi('erp:piecewagedetail:edit')")
    @Log(title = "计件工资明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PieceWageDetail pieceWageDetail) {
        return toAjax(pieceWageDetailService.updatePieceWageDetail(pieceWageDetail));
    }

    /**
     * 删除计件工资明细
     */
    @PreAuthorize("@ss.hasPermi('erp:piecewagedetail:remove')")
    @Log(title = "计件工资明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pieceWageDetailService.deletePieceWageDetailByIds(ids));
    }
}
