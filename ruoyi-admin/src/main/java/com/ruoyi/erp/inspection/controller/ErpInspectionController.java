package com.ruoyi.erp.inspection.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.erp.approval.service.IErpApprovalLogService;
import com.ruoyi.erp.inspection.domain.ErpInspectionBooking;
import com.ruoyi.erp.inspection.domain.ErpInspectionCompany;
import com.ruoyi.erp.inspection.service.IErpInspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erp/inspection")
public class ErpInspectionController extends BaseController {

    @Autowired
    private IErpInspectionService inspectionService;

    @Autowired
    private IErpApprovalLogService approvalLogService;

    // ---- 检品公司 ----

    @PreAuthorize("@ss.hasPermi('erp:inspectionCompany:list')")
    @GetMapping("/company/list")
    public TableDataInfo companyList(ErpInspectionCompany company) {
        startPage();
        return getDataTable(inspectionService.selectCompanyList(company));
    }

    @PreAuthorize("@ss.hasPermi('erp:inspectionCompany:query')")
    @GetMapping("/company/{id}")
    public AjaxResult companyInfo(@PathVariable Long id) {
        return success(inspectionService.selectCompanyById(id));
    }

    @PreAuthorize("@ss.hasPermi('erp:inspectionCompany:add')")
    @Log(title = "检品公司", businessType = BusinessType.INSERT)
    @PostMapping("/company")
    public AjaxResult addCompany(@RequestBody @Validated ErpInspectionCompany company) {
        return toAjax(inspectionService.insertCompany(company));
    }

    @PreAuthorize("@ss.hasPermi('erp:inspectionCompany:edit')")
    @Log(title = "检品公司", businessType = BusinessType.UPDATE)
    @PutMapping("/company")
    public AjaxResult editCompany(@RequestBody @Validated ErpInspectionCompany company) {
        return toAjax(inspectionService.updateCompany(company));
    }

    @PreAuthorize("@ss.hasPermi('erp:inspectionCompany:remove')")
    @Log(title = "检品公司", businessType = BusinessType.DELETE)
    @DeleteMapping("/company/{ids}")
    public AjaxResult removeCompany(@PathVariable Long[] ids) {
        return toAjax(inspectionService.deleteCompanyByIds(ids));
    }

    // ---- 检品预约 ----

    @PreAuthorize("@ss.hasPermi('erp:inspectionBooking:list')")
    @GetMapping("/booking/list")
    public TableDataInfo bookingList(ErpInspectionBooking booking) {
        startPage();
        return getDataTable(inspectionService.selectBookingList(booking));
    }

    @PreAuthorize("@ss.hasPermi('erp:inspectionBooking:query')")
    @GetMapping("/booking/{id}")
    public AjaxResult bookingInfo(@PathVariable Long id) {
        return success(inspectionService.selectBookingById(id));
    }

    @PreAuthorize("@ss.hasPermi('erp:inspectionBooking:add')")
    @Log(title = "检品预约", businessType = BusinessType.INSERT)
    @PostMapping("/booking")
    public AjaxResult addBooking(@RequestBody @Validated ErpInspectionBooking booking) {
        return toAjax(inspectionService.insertBooking(booking));
    }

    @PreAuthorize("@ss.hasPermi('erp:inspectionBooking:edit')")
    @Log(title = "检品预约", businessType = BusinessType.UPDATE)
    @PutMapping("/booking")
    public AjaxResult editBooking(@RequestBody @Validated ErpInspectionBooking booking) {
        return toAjax(inspectionService.updateBooking(booking));
    }

    @PreAuthorize("@ss.hasPermi('erp:inspectionBooking:remove')")
    @Log(title = "检品预约", businessType = BusinessType.DELETE)
    @DeleteMapping("/booking/{ids}")
    public AjaxResult removeBooking(@PathVariable Long[] ids) {
        return toAjax(inspectionService.deleteBookingByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('erp:inspectionBooking:edit')")
    @Log(title = "检品放行", businessType = BusinessType.UPDATE)
    @PutMapping("/booking/release/{id}")
    public AjaxResult release(@PathVariable Long id,
                              @RequestBody(required = false) java.util.Map<String, String> body) {
        ErpInspectionBooking booking = inspectionService.selectBookingById(id);
        if (booking == null) return error("检品预约单不存在");
        String from = booking.getStatus();
        booking.setStatus("RELEASED");
        booking.setReleaseBy(getUsername());
        booking.setReleaseTime(new java.util.Date());
        booking.setInspectionResult("PASS");
        inspectionService.updateBooking(booking);
        approvalLogService.writeLog("INSPECTION_BOOKING", id, booking.getBookingNo(),
            "QUALITY_RELEASE", "RELEASE", from, "RELEASED",
            getUsername(), body != null ? body.get("remark") : null, booking.getFactoryId());
        return success();
    }

    @PreAuthorize("@ss.hasPermi('erp:inspectionBooking:edit')")
    @Log(title = "检品驳回", businessType = BusinessType.UPDATE)
    @PutMapping("/booking/reject/{id}")
    public AjaxResult rejectBooking(@PathVariable Long id,
                                    @RequestBody java.util.Map<String, String> body) {
        ErpInspectionBooking booking = inspectionService.selectBookingById(id);
        if (booking == null) return error("检品预约单不存在");
        String from = booking.getStatus();
        booking.setStatus("FAIL");
        booking.setInspectionResult("FAIL");
        booking.setDefectSummary(body.get("defectSummary"));
        inspectionService.updateBooking(booking);
        approvalLogService.writeLog("INSPECTION_BOOKING", id, booking.getBookingNo(),
            "QUALITY_RELEASE", "REJECT", from, "FAIL",
            getUsername(), body.get("remark"), booking.getFactoryId());
        return success();
    }
}
