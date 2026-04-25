package com.ruoyi.erp.controller;

import com.ruoyi.erp.domain.ProduceJobProcess;
import com.ruoyi.erp.service.IProduceJobProcessService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 工序流转记录Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/erp/produceJobProcess")
public class ProduceJobProcessController extends BaseController {

    @Autowired
    private IProduceJobProcessService produceJobProcessService;

    /**
     * 查询工序流转记录列表
     */
    @PreAuthorize("@ss.hasPermi('erp:produceJobProcess:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProduceJobProcess produceJobProcess) {
        startPage();
        List<ProduceJobProcess> list = produceJobProcessService.selectProduceJobProcessList(produceJobProcess);
        return getDataTable(list);
    }

    /**
     * 根据工票ID查询工序流转记录列表
     */
    @PreAuthorize("@ss.hasPermi('erp:produceJobProcess:list')")
    @GetMapping("/listByJob/{jobId}")
    public AjaxResult listByJob(@PathVariable Long jobId) {
        List<ProduceJobProcess> list = produceJobProcessService.selectProduceJobProcessByJobId(jobId);
        return AjaxResult.success(list);
    }

    /**
     * 获取工票当前工序
     */
    @PreAuthorize("@ss.hasPermi('erp:produceJobProcess:query')")
    @GetMapping("/currentProcess/{jobId}")
    public AjaxResult getCurrentProcess(@PathVariable Long jobId) {
        ProduceJobProcess process = produceJobProcessService.selectCurrentProcessByJobId(jobId);
        return AjaxResult.success(process);
    }

    /**
     * 导出工序流转记录列表
     */
    @PreAuthorize("@ss.hasPermi('erp:produceJobProcess:export')")
    @Log(title = "工序流转记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProduceJobProcess produceJobProcess) {
        List<ProduceJobProcess> list = produceJobProcessService.selectProduceJobProcessList(produceJobProcess);
        ExcelUtil<ProduceJobProcess> util = new ExcelUtil<ProduceJobProcess>(ProduceJobProcess.class);
        util.exportExcel(response, list, "工序流转记录数据");
    }

    /**
     * 获取工序流转记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:produceJobProcess:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(produceJobProcessService.selectProduceJobProcessById(id));
    }

    /**
     * 新增工序流转记录
     */
    @PreAuthorize("@ss.hasPermi('erp:produceJobProcess:add')")
    @Log(title = "工序流转记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProduceJobProcess produceJobProcess) {
        return toAjax(produceJobProcessService.insertProduceJobProcess(produceJobProcess));
    }

    /**
     * 修改工序流转记录
     * 当状态变为 FAIL 时，将 rejectReason 追加写入 remark，便于后续审计查阅
     */
    @PreAuthorize("@ss.hasPermi('erp:produceJobProcess:edit')")
    @Log(title = "工序流转记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProduceJobProcess produceJobProcess) {
        if ("FAIL".equals(produceJobProcess.getProcessStatus()) && produceJobProcess.getRejectReason() != null) {
            String remark = produceJobProcess.getRemark();
            String suffix = "REJECT_REASON: " + produceJobProcess.getRejectReason();
            produceJobProcess.setRemark(remark == null || remark.isEmpty() ? suffix : remark + "\n" + suffix);
        }
        return toAjax(produceJobProcessService.updateProduceJobProcess(produceJobProcess));
    }

    /**
     * 删除工序流转记录
     */
    @PreAuthorize("@ss.hasPermi('erp:produceJobProcess:remove')")
    @Log(title = "工序流转记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(produceJobProcessService.deleteProduceJobProcessByIds(ids));
    }
}
