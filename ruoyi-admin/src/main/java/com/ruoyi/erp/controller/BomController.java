package com.ruoyi.erp.controller;

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
import com.ruoyi.erp.domain.Bom;
import com.ruoyi.erp.service.IBomService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

@RestController
@RequestMapping("/erp/bom")
public class BomController extends BaseController {
    @Autowired
    private IBomService bomService;

    @PreAuthorize("@ss.hasPermi('erp:bom:list')")
    @GetMapping("/list")
    public TableDataInfo list(Bom bom) {
        startPage();
        List<Bom> list = bomService.selectBomList(bom);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('erp:bom:export')")
    @Log(title = "样衣BOM", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Bom bom) {
        List<Bom> list = bomService.selectBomList(bom);
        ExcelUtil<Bom> util = new ExcelUtil<Bom>(Bom.class);
        util.exportExcel(response, list, "样衣BOM数据");
    }

    @PreAuthorize("@ss.hasPermi('erp:bom:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(bomService.selectBomById(id));
    }

    @PreAuthorize("@ss.hasPermi('erp:bom:add')")
    @Log(title = "样衣BOM", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Bom bom) {
        return toAjax(bomService.insertBom(bom));
    }

    @PreAuthorize("@ss.hasPermi('erp:bom:edit')")
    @Log(title = "样衣BOM", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Bom bom) {
        return toAjax(bomService.updateBom(bom));
    }

    @PreAuthorize("@ss.hasPermi('erp:bom:remove')")
    @Log(title = "样衣BOM", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bomService.deleteBomByIds(ids));
    }
}
