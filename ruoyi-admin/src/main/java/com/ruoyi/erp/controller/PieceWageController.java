package com.ruoyi.erp.controller;

import com.ruoyi.erp.domain.PieceWage;
import com.ruoyi.erp.service.IPieceWageService;
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
 * 计件工资汇总Controller
 *
 * @author zhangmingjian
 */
@RestController
@RequestMapping("/erp/piecewage")
public class PieceWageController extends BaseController {

    @Autowired
    private IPieceWageService pieceWageService;

    /**
     * 查询计件工资汇总列表
     */
    @PreAuthorize("@ss.hasPermi('erp:piecewage:list')")
    @GetMapping("/list")
    public TableDataInfo list(PieceWage pieceWage) {
        startPage();
        List<PieceWage> list = pieceWageService.selectPieceWageList(pieceWage);
        return getDataTable(list);
    }

    /**
     * 导出计件工资汇总列表
     */
    @PreAuthorize("@ss.hasPermi('erp:piecewage:export')")
    @Log(title = "计件工资汇总", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PieceWage pieceWage) {
        List<PieceWage> list = pieceWageService.selectPieceWageList(pieceWage);
        ExcelUtil<PieceWage> util = new ExcelUtil<PieceWage>(PieceWage.class);
        util.exportExcel(response, list, "计件工资汇总数据");
    }

    /**
     * 获取计件工资汇总详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:piecewage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(pieceWageService.selectPieceWageById(id));
    }

    /**
     * 新增计件工资汇总
     */
    @PreAuthorize("@ss.hasPermi('erp:piecewage:add')")
    @Log(title = "计件工资汇总", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PieceWage pieceWage) {
        int count = pieceWageService.countByEmployeeAndMonth(pieceWage.getEmployeeId(), pieceWage.getWageMonth());
        if (count > 0) {
            return AjaxResult.error("该员工本月已存在工资汇总");
        }
        return toAjax(pieceWageService.insertPieceWage(pieceWage));
    }

    /**
     * 修改计件工资汇总
     */
    @PreAuthorize("@ss.hasPermi('erp:piecewage:edit')")
    @Log(title = "计件工资汇总", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PieceWage pieceWage) {
        return toAjax(pieceWageService.updatePieceWage(pieceWage));
    }

    /**
     * 删除计件工资汇总
     */
    @PreAuthorize("@ss.hasPermi('erp:piecewage:remove')")
    @Log(title = "计件工资汇总", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pieceWageService.deletePieceWageByIds(ids));
    }

    /**
     * 按月自动汇总员工产量生成工资单
     *
     * @param wageMonth 月份 yyyy-MM，例如 2026-04
     */
    @PreAuthorize("@ss.hasPermi('erp:piecewage:add')")
    @Log(title = "计件工资自动结算", businessType = BusinessType.INSERT)
    @PostMapping("/autoGenerate")
    public AjaxResult autoGenerate(@RequestParam String wageMonth) {
        int count = pieceWageService.autoGenerateWageByMonth(wageMonth);
        return success("本月共生成 " + count + " 名员工工资单（已存在的自动跳过）");
    }
}
