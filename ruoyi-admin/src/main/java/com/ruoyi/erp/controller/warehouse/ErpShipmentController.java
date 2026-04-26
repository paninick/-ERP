package com.ruoyi.erp.controller.warehouse;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.erp.domain.ErpShipment;
import com.ruoyi.erp.service.IErpShipmentService;

@RestController
@RequestMapping("/erp/shipment")
public class ErpShipmentController extends BaseController {

    @Autowired
    private IErpShipmentService shipmentService;

    @PreAuthorize("@ss.hasPermi('erp:shipment:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpShipment shipment) {
        startPage();
        List<ErpShipment> list = shipmentService.selectList(shipment);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('erp:shipment:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return success(shipmentService.selectById(id));
    }

    @PreAuthorize("@ss.hasPermi('erp:shipment:add')")
    @Log(title = "出货单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpShipment shipment) {
        return toAjax(shipmentService.insert(shipment));
    }

    @PreAuthorize("@ss.hasPermi('erp:shipment:edit')")
    @Log(title = "出货单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpShipment shipment) {
        return toAjax(shipmentService.update(shipment));
    }

    @PreAuthorize("@ss.hasPermi('erp:shipment:remove')")
    @Log(title = "出货单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(shipmentService.deleteByIds(ids));
    }
}
