package com.ruoyi.erp.controller.sales;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.erp.approval.service.IErpApprovalLogService;
import com.ruoyi.erp.domain.SalesOrder;
import com.ruoyi.erp.service.ISalesOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 销售订单Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping({"/erp/sales/order", "/erp/salesOrder", "/erp/salesorder", "/erp/sales"})
public class SalesOrderController extends BaseController {
    @Autowired
    private ISalesOrderService salesOrderService;

    @Autowired
    private IErpApprovalLogService approvalLogService;

    /**
     * 查询销售订单列表
     */
    @PreAuthorize("@ss.hasPermi('erp:sales:list')")
    @GetMapping("/list")
    public TableDataInfo list(SalesOrder salesOrder) {
        startPage();
        List<SalesOrder> list = salesOrderService.selectSalesOrderList(salesOrder);
        return getDataTable(list);
    }

    /**
     * 导出销售订单列表
     */
    @PreAuthorize("@ss.hasPermi('erp:sales:export')")
    @Log(title = "销售订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SalesOrder salesOrder) {
        List<SalesOrder> list = salesOrderService.selectSalesOrderList(salesOrder);
        ExcelUtil<SalesOrder> util = new ExcelUtil<SalesOrder>(SalesOrder.class);
        util.exportExcel(response, list, "销售订单数据");
    }

    /**
     * 获取销售订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:sales:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(salesOrderService.selectSalesOrderById(id));
    }

    /**
     * 新增销售订单
     */
    @PreAuthorize("@ss.hasPermi('erp:sales:add')")
    @Log(title = "销售订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SalesOrder salesOrder) {
        return toAjax(salesOrderService.insertSalesOrder(salesOrder));
    }

    /**
     * 修改销售订单
     */
    @PreAuthorize("@ss.hasPermi('erp:sales:edit')")
    @Log(title = "销售订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SalesOrder salesOrder) {
        return toAjax(salesOrderService.updateSalesOrder(salesOrder));
    }

    /**
     * 删除销售订单
     */
    @PreAuthorize("@ss.hasPermi('erp:sales:remove')")
    @Log(title = "销售订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(salesOrderService.deleteSalesOrderByIds(ids));
    }

    /**
     * 导入销售订单数据
     */
    @PreAuthorize("@ss.hasPermi('erp:sales:import')")
    @Log(title = "销售订单导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        if (file == null || file.isEmpty()) {
            return error("上传文件不能为空");
        }

        System.out.println("文件名称: " + file.getOriginalFilename());
        System.out.println("文件大小: " + file.getSize() + " bytes");
        System.out.println("文件类型: " + file.getContentType());

        try {
            ExcelUtil<SalesOrder> util = new ExcelUtil<SalesOrder>(SalesOrder.class);
            List<SalesOrder> list = util.importExcel(file.getInputStream());
            System.out.println("解析出的数据条数: " + (list != null ? list.size() : 0));

            if (list != null && !list.isEmpty()) {
                System.out.println("=== 前5条解析数据 ===");
                for (int i = 0; i < Math.min(5, list.size()); i++) {
                    SalesOrder salesOrder = list.get(i);
                    System.out.println("记录[" + i + "]:");
                    System.out.println("  salesNo=" + salesOrder.getSalesNo());
                    System.out.println("  salesDate=" + salesOrder.getSalesDate());
                }
                System.out.println("=== 前5条解析数据结束 ===");
            }

            String message = salesOrderService.importSalesOrder(list, updateSupport);
            return success(message);
        } catch (Exception e) {
            System.out.println("导入异常: " + e.getMessage());
            e.printStackTrace();
            return error("导入失败: " + e.getMessage());
        }
    }

    /**
     * 下载模板
     */
    @PreAuthorize("@ss.hasPermi('erp:sales:import')")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<SalesOrder> util = new ExcelUtil<SalesOrder>(SalesOrder.class);
        util.importTemplateExcel(response, "销售订单数据");
    }

    /** 提交审批 */
    @PreAuthorize("@ss.hasPermi('erp:sales:edit')")
    @Log(title = "销售订单审批", businessType = BusinessType.UPDATE)
    @PutMapping("/submit/{id}")
    public AjaxResult submit(@PathVariable Long id) {
        SalesOrder order = salesOrderService.selectSalesOrderById(id);
        if (order == null) return error("订单不存在");
        String from = order.getAuditStatus();
        order.setAuditStatus("待审批");
        salesOrderService.updateSalesOrder(order);
        approvalLogService.writeLog("SALES_ORDER", id, order.getSalesNo(),
            "SALES_APPROVE", "SUBMIT", from, "待审批", getUsername(), null, null);
        return success();
    }

    /** 审核通过 */
    @PreAuthorize("@ss.hasPermi('erp:sales:edit')")
    @Log(title = "销售订单审批", businessType = BusinessType.UPDATE)
    @PutMapping("/approve/{id}")
    public AjaxResult approve(@PathVariable Long id,
                              @RequestBody(required = false) java.util.Map<String, String> body) {
        SalesOrder order = salesOrderService.selectSalesOrderById(id);
        if (order == null) return error("订单不存在");
        String from = order.getAuditStatus();
        String remark = body != null ? body.get("remark") : null;
        order.setAuditStatus("审批通过");
        salesOrderService.updateSalesOrder(order);
        approvalLogService.writeLog("SALES_ORDER", id, order.getSalesNo(),
            "SALES_APPROVE", "APPROVE", from, "审批通过", getUsername(), remark, null);
        return success();
    }

    /** 驳回 */
    @PreAuthorize("@ss.hasPermi('erp:sales:edit')")
    @Log(title = "销售订单审批", businessType = BusinessType.UPDATE)
    @PutMapping("/reject/{id}")
    public AjaxResult reject(@PathVariable Long id,
                             @RequestBody java.util.Map<String, String> body) {
        SalesOrder order = salesOrderService.selectSalesOrderById(id);
        if (order == null) return error("订单不存在");
        String from = order.getAuditStatus();
        String remark = body.get("remark");
        order.setAuditStatus("已驳回");
        salesOrderService.updateSalesOrder(order);
        approvalLogService.writeLog("SALES_ORDER", id, order.getSalesNo(),
            "SALES_APPROVE", "REJECT", from, "已驳回", getUsername(), remark, null);
        return success();
    }
}
