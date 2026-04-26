package com.ruoyi.erp.controller.production;

import com.ruoyi.erp.approval.service.IErpApprovalLogService;
import com.ruoyi.erp.domain.ProduceDefect;
import com.ruoyi.erp.service.IProduceDefectService;
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
 * 次品记录Controller
 *
 * @author zhangmingjian
 */
@RestController
@RequestMapping("/erp/defect")
public class ProduceDefectController extends BaseController {

    @Autowired
    private IProduceDefectService produceDefectService;

    @Autowired
    private IErpApprovalLogService approvalLogService;

    /**
     * 查询次品记录列表
     */
    @PreAuthorize("@ss.hasPermi('erp:defect:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProduceDefect produceDefect) {
        startPage();
        List<ProduceDefect> list = produceDefectService.selectProduceDefectList(produceDefect);
        return getDataTable(list);
    }

    /**
     * 导出次品记录列表
     */
    @PreAuthorize("@ss.hasPermi('erp:defect:export')")
    @Log(title = "次品记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProduceDefect produceDefect) {
        List<ProduceDefect> list = produceDefectService.selectProduceDefectList(produceDefect);
        ExcelUtil<ProduceDefect> util = new ExcelUtil<ProduceDefect>(ProduceDefect.class);
        util.exportExcel(response, list, "次品记录数据");
    }

    /**
     * 获取次品记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:defect:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(produceDefectService.selectProduceDefectById(id));
    }

    /**
     * 新增次品记录
     */
    @PreAuthorize("@ss.hasPermi('erp:defect:add')")
    @Log(title = "次品记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProduceDefect produceDefect) {
        return toAjax(produceDefectService.insertProduceDefect(produceDefect));
    }

    /**
     * 修改次品记录
     */
    @PreAuthorize("@ss.hasPermi('erp:defect:edit')")
    @Log(title = "次品记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProduceDefect produceDefect) {
        return toAjax(produceDefectService.updateProduceDefect(produceDefect));
    }

    /**
     * 删除次品记录
     */
    @PreAuthorize("@ss.hasPermi('erp:defect:remove')")
    @Log(title = "次品记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(produceDefectService.deleteProduceDefectByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('erp:defect:approve')")
    @Log(title = "次品审批", businessType = BusinessType.UPDATE)
    @PutMapping("/submit/{id}")
    public AjaxResult submit(@PathVariable Long id) {
        ProduceDefect defect = produceDefectService.selectProduceDefectById(id);
        String from = defect.getAuditStatus() != null ? defect.getAuditStatus() : "DRAFT";
        defect.setAuditStatus("SUBMITTED");
        produceDefectService.updateProduceDefect(defect);
        approvalLogService.writeLog("PRODUCE_DEFECT", id, null, "DEFECT_APPROVE", "SUBMIT",
            from, "SUBMITTED", getUsername(), null, null);
        return success();
    }

    @PreAuthorize("@ss.hasPermi('erp:defect:approve')")
    @Log(title = "次品审批", businessType = BusinessType.UPDATE)
    @PutMapping("/approve/{id}")
    public AjaxResult approve(@PathVariable Long id) {
        ProduceDefect defect = produceDefectService.selectProduceDefectById(id);
        defect.setAuditStatus("APPROVED");
        produceDefectService.updateProduceDefect(defect);
        approvalLogService.writeLog("PRODUCE_DEFECT", id, null, "DEFECT_APPROVE", "APPROVE",
            "SUBMITTED", "APPROVED", getUsername(), null, null);
        return success();
    }

    @PreAuthorize("@ss.hasPermi('erp:defect:approve')")
    @Log(title = "次品审批", businessType = BusinessType.UPDATE)
    @PutMapping("/reject/{id}")
    public AjaxResult reject(@PathVariable Long id) {
        ProduceDefect defect = produceDefectService.selectProduceDefectById(id);
        defect.setAuditStatus("REJECTED");
        produceDefectService.updateProduceDefect(defect);
        approvalLogService.writeLog("PRODUCE_DEFECT", id, null, "DEFECT_APPROVE", "REJECT",
            "SUBMITTED", "REJECTED", getUsername(), null, null);
        return success();
    }
}
