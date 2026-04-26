package com.ruoyi.erp.style.controller;

import java.util.List;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.erp.style.domain.ErpStyle;
import com.ruoyi.erp.style.service.IErpStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/erp/style")
public class ErpStyleController extends BaseController {

    @Autowired
    private IErpStyleService styleService;

    @PreAuthorize("@ss.hasPermi('erp:style:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpStyle style) {
        startPage();
        List<ErpStyle> list = styleService.selectErpStyleList(style);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('erp:style:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return success(styleService.selectErpStyleById(id));
    }

    @PreAuthorize("@ss.hasPermi('erp:style:query')")
    @GetMapping("/code/{styleCode}")
    public AjaxResult getByCode(@PathVariable String styleCode) {
        return success(styleService.selectErpStyleByCode(styleCode));
    }

    @PreAuthorize("@ss.hasPermi('erp:style:add')")
    @Log(title = "款号档案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody @Validated ErpStyle style) {
        return toAjax(styleService.insertErpStyle(style));
    }

    @PreAuthorize("@ss.hasPermi('erp:style:edit')")
    @Log(title = "款号档案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody @Validated ErpStyle style) {
        return toAjax(styleService.updateErpStyle(style));
    }

    @PreAuthorize("@ss.hasPermi('erp:style:remove')")
    @Log(title = "款号档案", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(styleService.deleteErpStyleByIds(ids));
    }
}
