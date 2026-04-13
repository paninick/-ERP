package com.ruoyi.erp.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.erp.domain.AuxiliaryMaterial;
import com.ruoyi.erp.service.IAuxiliaryMaterialService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 辅料Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping("/erp/auxiliary")
public class AuxiliaryMaterialController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(AuxiliaryMaterialController.class);
    @Autowired
    private IAuxiliaryMaterialService auxiliaryMaterialService;

    /**
     * 查询辅料列表
     */
    @PreAuthorize("@ss.hasPermi('erp:auxiliary:list')")
    @GetMapping("/list")
    public TableDataInfo list(AuxiliaryMaterial auxiliaryMaterial) {
        startPage();
        List<AuxiliaryMaterial> list = auxiliaryMaterialService.selectAuxiliaryMaterialList(auxiliaryMaterial);
        return getDataTable(list);
    }

    /**
     * 导出辅料列表
     */
    @PreAuthorize("@ss.hasPermi('erp:auxiliary:export')")
    @Log(title = "辅料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AuxiliaryMaterial auxiliaryMaterial) {
        List<AuxiliaryMaterial> list = auxiliaryMaterialService.selectAuxiliaryMaterialList(auxiliaryMaterial);
        ExcelUtil<AuxiliaryMaterial> util = new ExcelUtil<AuxiliaryMaterial>(AuxiliaryMaterial.class);
        util.exportExcel(response, list, "辅料数据");
    }

    /**
     * 获取辅料详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:auxiliary:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(auxiliaryMaterialService.selectAuxiliaryMaterialById(id));
    }

    /**
     * 新增辅料
     */
    @PreAuthorize("@ss.hasPermi('erp:auxiliary:add')")
    @Log(title = "辅料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AuxiliaryMaterial auxiliaryMaterial) {
        return toAjax(auxiliaryMaterialService.insertAuxiliaryMaterial(auxiliaryMaterial));
    }

    /**
     * 修改辅料
     */
    @PreAuthorize("@ss.hasPermi('erp:auxiliary:edit')")
    @Log(title = "辅料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AuxiliaryMaterial auxiliaryMaterial) {
        return toAjax(auxiliaryMaterialService.updateAuxiliaryMaterial(auxiliaryMaterial));
    }

    /**
     * 删除辅料
     */
    @PreAuthorize("@ss.hasPermi('erp:auxiliary:remove')")
    @Log(title = "辅料", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(auxiliaryMaterialService.deleteAuxiliaryMaterialByIds(ids));
    }

    /**
     * 下载导入模板
     */
    @PreAuthorize("@ss.hasPermi('erp:auxiliary:import')")
    @GetMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<AuxiliaryMaterial> util = new ExcelUtil<AuxiliaryMaterial>(AuxiliaryMaterial.class);
        util.importTemplateExcel(response, "辅料数据");
    }

    /**
     * 导入辅料数据
     */
    @PreAuthorize("@ss.hasPermi('erp:auxiliary:import')")
    @Log(title = "辅料导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        log.info("========== 接收到辅料导入请求 ==========");
        log.info("文件名: {}, 文件大小: {} bytes", file.getOriginalFilename(), file.getSize());
        log.info("是否支持更新: {}", updateSupport);
        
        try {
            ExcelUtil<AuxiliaryMaterial> util = new ExcelUtil<AuxiliaryMaterial>(AuxiliaryMaterial.class);
            log.info("开始读取Excel文件...");
            List<AuxiliaryMaterial> list = util.importExcel(file.getInputStream());
            
            log.info("Excel读取完成，读取到 {} 条数据", list.size());
            for (int i = 0; i < list.size(); i++) {
                log.info("读取到第 {} 条数据: {}", i + 1, list.get(i));
            }
            
            String message = auxiliaryMaterialService.importAuxiliary(list, updateSupport);
            log.info("导入处理完成，返回消息: {}", message);
            return success(message);
        } catch (Exception e) {
            log.error("导入辅料数据异常", e);
            throw e;
        }
    }

    /**
     * 快速批量导入辅料数据（使用INSERT IGNORE，更快速）
     */
    @PreAuthorize("@ss.hasPermi('erp:auxiliary:import')")
    @Log(title = "辅料快速导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importDataFast")
    public AjaxResult importDataFast(MultipartFile file) throws Exception {
        log.info("========== 接收到辅料快速导入请求 ==========");
        log.info("文件名: {}, 文件大小: {} bytes", file.getOriginalFilename(), file.getSize());
        
        try {
            ExcelUtil<AuxiliaryMaterial> util = new ExcelUtil<AuxiliaryMaterial>(AuxiliaryMaterial.class);
            log.info("开始读取Excel文件...");
            List<AuxiliaryMaterial> list = util.importExcel(file.getInputStream());
            
            log.info("Excel读取完成，读取到 {} 条数据", list.size());
            for (int i = 0; i < list.size(); i++) {
                log.info("读取到第 {} 条数据: {}", i + 1, list.get(i));
            }
            
            String message = auxiliaryMaterialService.importAuxiliaryFast(list);
            log.info("快速导入处理完成，返回消息: {}", message);
            return success(message);
        } catch (Exception e) {
            log.error("快速导入辅料数据异常", e);
            throw e;
        }
    }
}
