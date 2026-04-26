package com.ruoyi.erp.controller.quality;

import com.ruoyi.erp.domain.BizAbnormalPool;
import com.ruoyi.erp.service.IBizAbnormalPoolService;
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
 * 业务异常池Controller
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
@RestController
@RequestMapping("/erp/bizabnormal")
public class BizAbnormalPoolController extends BaseController {

    @Autowired
    private IBizAbnormalPoolService bizAbnormalPoolService;

    /**
     * 查询业务异常池列表
     */
    @PreAuthorize("@ss.hasPermi('erp:bizabnormal:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizAbnormalPool bizAbnormalPool) {
        startPage();
        List<BizAbnormalPool> list = bizAbnormalPoolService.selectBizAbnormalPoolList(bizAbnormalPool);
        return getDataTable(list);
    }

    /**
     * 导出业务异常池列表
     */
    @PreAuthorize("@ss.hasPermi('erp:bizabnormal:export')")
    @Log(title = "业务异常池", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizAbnormalPool bizAbnormalPool) {
        List<BizAbnormalPool> list = bizAbnormalPoolService.selectBizAbnormalPoolList(bizAbnormalPool);
        ExcelUtil<BizAbnormalPool> util = new ExcelUtil<BizAbnormalPool>(BizAbnormalPool.class);
        util.exportExcel(response, list, "业务异常池数据");
    }

    /**
     * 获取业务异常池详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:bizabnormal:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id) {
        return AjaxResult.success(bizAbnormalPoolService.selectBizAbnormalPoolById(id));
    }

    /**
     * 新增业务异常池
     */
    @PreAuthorize("@ss.hasPermi('erp:bizabnormal:add')")
    @Log(title = "业务异常池", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizAbnormalPool bizAbnormalPool) {
        return toAjax(bizAbnormalPoolService.insertBizAbnormalPool(bizAbnormalPool));
    }

    /**
     * 修改业务异常池
     */
    @PreAuthorize("@ss.hasPermi('erp:bizabnormal:edit')")
    @Log(title = "业务异常池", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizAbnormalPool bizAbnormalPool) {
        return toAjax(bizAbnormalPoolService.updateBizAbnormalPool(bizAbnormalPool));
    }

    /**
     * 删除业务异常池
     */
    @PreAuthorize("@ss.hasPermi('erp:bizabnormal:remove')")
    @Log(title = "业务异常池", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bizAbnormalPoolService.deleteBizAbnormalPoolByIds(ids));
    }

    /**
     * 检查业务是否存在未处理异常
     */
    @PreAuthorize("@ss.hasPermi('erp:bizabnormal:query')")
    @GetMapping("/check/{bizType}/{bizId}")
    public AjaxResult checkUnhandled(@PathVariable String bizType, @PathVariable Long bizId) {
        boolean hasUnhandled = bizAbnormalPoolService.hasUnhandledAbnormal(bizType, bizId);
        AjaxResult result = AjaxResult.success();
        result.put("hasUnhandled", hasUnhandled);
        return result;
    }
}
