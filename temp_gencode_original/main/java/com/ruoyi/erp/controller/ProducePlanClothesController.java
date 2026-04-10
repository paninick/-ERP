package com.ruoyi.erp.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.erp.domain.ProducePlanClothes;
import com.ruoyi.erp.service.IProducePlanClothesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 生产计划衣服明细Controller
 *
 * @author zhangmingjian
 * @date 2026-04-06
 */
@RestController
@RequestMapping("/erp/planclothes")
public class ProducePlanClothesController extends BaseController {
    @Autowired
    private IProducePlanClothesService producePlanClothesService;

    /**
     * 查询生产计划衣服明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:planclothes:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProducePlanClothes producePlanClothes) {
        startPage();
        List<ProducePlanClothes> list = producePlanClothesService.selectProducePlanClothesList(producePlanClothes);
        return getDataTable(list);
    }

    /**
     * 导出生产计划衣服明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:planclothes:export')")
    @Log(title = "生产计划衣服明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProducePlanClothes producePlanClothes) {
        List<ProducePlanClothes> list = producePlanClothesService.selectProducePlanClothesList(producePlanClothes);
        ExcelUtil<ProducePlanClothes> util = new ExcelUtil<ProducePlanClothes>(ProducePlanClothes.class);
        util.exportExcel(response, list, "生产计划衣服明细数据");
    }

    /**
     * 获取生产计划衣服明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:planclothes:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(producePlanClothesService.selectProducePlanClothesById(id));
    }

    /**
     * 新增生产计划衣服明细
     */
    @PreAuthorize("@ss.hasPermi('erp:planclothes:add')")
    @Log(title = "生产计划衣服明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProducePlanClothes producePlanClothes) {
        return toAjax(producePlanClothesService.insertProducePlanClothes(producePlanClothes));
    }

    /**
     * 修改生产计划衣服明细
     */
    @PreAuthorize("@ss.hasPermi('erp:planclothes:edit')")
    @Log(title = "生产计划衣服明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProducePlanClothes producePlanClothes) {
        return toAjax(producePlanClothesService.updateProducePlanClothes(producePlanClothes));
    }

    /**
     * 删除生产计划衣服明细
     */
    @PreAuthorize("@ss.hasPermi('erp:planclothes:remove')")
    @Log(title = "生产计划衣服明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(producePlanClothesService.deleteProducePlanClothesByIds(ids));
    }
}
