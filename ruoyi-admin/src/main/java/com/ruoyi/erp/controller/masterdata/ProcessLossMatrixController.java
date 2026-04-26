package com.ruoyi.erp.controller.masterdata;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.erp.domain.ProcessLossMatrix;
import com.ruoyi.erp.service.IProcessLossMatrixService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工序损耗矩阵Controller
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
@RestController
@RequestMapping("/erp/processLossMatrix")
public class ProcessLossMatrixController extends BaseController {
    @Autowired
    private IProcessLossMatrixService processLossMatrixService;

    /**
     * 查询工序损耗矩阵列表
     */
    @PreAuthorize("@ss.hasPermi('erp:processLossMatrix:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProcessLossMatrix processLossMatrix) {
        startPage();
        List<ProcessLossMatrix> list = processLossMatrixService.selectProcessLossMatrixList(processLossMatrix);
        return getDataTable(list);
    }

    /**
     * 导出工序损耗矩阵列表
     */
    @PreAuthorize("@ss.hasPermi('erp:processLossMatrix:export')")
    @Log(title = "工序损耗矩阵", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProcessLossMatrix processLossMatrix) {
        List<ProcessLossMatrix> list = processLossMatrixService.selectProcessLossMatrixList(processLossMatrix);
        ExcelUtil<ProcessLossMatrix> util = new ExcelUtil<ProcessLossMatrix>(ProcessLossMatrix.class);
        util.exportExcel(response, list, "工序损耗矩阵数据");
    }

    /**
     * 获取工序损耗矩阵详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:processLossMatrix:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(processLossMatrixService.selectProcessLossMatrixById(id));
    }

    /**
     * 新增工序损耗矩阵
     */
    @PreAuthorize("@ss.hasPermi('erp:processLossMatrix:add')")
    @Log(title = "工序损耗矩阵", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProcessLossMatrix processLossMatrix) {
        if (processLossMatrixService.checkCombinationExists(
                processLossMatrix.getMaterialAType(),
                processLossMatrix.getMaterialBType(),
                processLossMatrix.getProcessCode())) {
            return error("该组合已存在，请不要重复添加");
        }
        return toAjax(processLossMatrixService.insertProcessLossMatrix(processLossMatrix));
    }

    /**
     * 修改工序损耗矩阵
     */
    @PreAuthorize("@ss.hasPermi('erp:processLossMatrix:edit')")
    @Log(title = "工序损耗矩阵", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProcessLossMatrix processLossMatrix) {
        return toAjax(processLossMatrixService.updateProcessLossMatrix(processLossMatrix));
    }

    /**
     * 删除工序损耗矩阵
     */
    @PreAuthorize("@ss.hasPermi('erp:processLossMatrix:remove')")
    @Log(title = "工序损耗矩阵", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(processLossMatrixService.deleteProcessLossMatrixByIds(ids));
    }
}
