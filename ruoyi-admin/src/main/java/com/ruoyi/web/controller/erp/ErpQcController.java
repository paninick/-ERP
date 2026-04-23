package com.ruoyi.web.controller.erp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.FactoryDataScope;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.QcInspection;
import com.ruoyi.system.mapper.QcInspectionMapper;
import com.ruoyi.common.utils.SecurityUtils;

/**
 * 质检工作台 Controller
 * 路由：/erp/check
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/erp/check")
public class ErpQcController extends BaseController
{
    @Autowired
    private QcInspectionMapper qcInspectionMapper;

    /**
     * GET /erp/check/stats
     * 返回 KPI：{pendingCount, todayChecked, passRate, criticalCount}
     */
    @GetMapping("/stats")
    @PreAuthorize("@ss.hasPermi('erp:qc:list')")
    public AjaxResult getStats()
    {
        Long factoryId = getFactoryId();
        Map<String, Object> stats = qcInspectionMapper.selectQcStats(factoryId);
        return AjaxResult.success(stats);
    }

    /**
     * GET /erp/check/defectReasons
     * 返回 TOP5 不合格原因：[{name, count}]
     */
    @GetMapping("/defectReasons")
    @PreAuthorize("@ss.hasPermi('erp:qc:list')")
    public AjaxResult getDefectReasons()
    {
        Long factoryId = getFactoryId();
        List<Map<String, Object>> reasons = qcInspectionMapper.selectDefectReasonTop5(factoryId);
        return AjaxResult.success(reasons);
    }

    /**
     * GET /erp/check/list
     * 检验记录列表（含工厂隔离）
     */
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermi('erp:qc:list')")
    @FactoryDataScope(factoryAlias = "qi", userAlias = "")
    public TableDataInfo list(QcInspection qcInspection)
    {
        startPage();
        List<QcInspection> list = qcInspectionMapper.selectQcList(qcInspection);
        return getDataTable(list);
    }

    /**
     * GET /erp/check/barcode/{code}
     * 按条码/批次号查询检验记录
     */
    @GetMapping("/barcode/{code}")
    @PreAuthorize("@ss.hasPermi('erp:qc:list')")
    public AjaxResult getByBarcode(@PathVariable("code") String code)
    {
        QcInspection qcInspection = qcInspectionMapper.selectQcByBatchNo(code);
        return AjaxResult.success(qcInspection);
    }

    /**
     * POST /erp/check/reject/{id}
     * 打回操作，将状态改为 REJECTED
     * (复杂联动逻辑由 Service 层 Model C 处理，此处骨架先行)
     */
    @PostMapping("/reject/{id}")
    @PreAuthorize("@ss.hasPermi('erp:qc:edit')")
    public AjaxResult reject(@PathVariable("id") Long id,
                             @RequestBody(required = false) Map<String, String> body)
    {
        QcInspection update = new QcInspection();
        update.setId(id);
        update.setStatus("REJECTED");
        update.setRejectReason(body != null ? body.get("reason") : "");
        update.setUpdateBy(getUsername());
        return toAjax(qcInspectionMapper.updateQcInspection(update));
    }

    /** 从当前登录用户获取工厂 ID（兜底：返回 -1 表示无工厂隔离需求，超管用） */
    private Long getFactoryId()
    {
        try {
            // 实际项目中从 LoginUser 的扩展字段获取
            return SecurityUtils.getLoginUser().getUser().getDeptId();
        } catch (Exception e) {
            return -1L;
        }
    }
}
