package com.ruoyi.erp.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.erp.domain.MainMaterial;
import com.ruoyi.erp.domain.MaterialSku;
import com.ruoyi.erp.domain.vo.AuxPropertySelection;
import com.ruoyi.erp.service.IMainMaterialService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 主料Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping("/erp/material/main")
public class MainMaterialController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(MainMaterialController.class);
    @Autowired
    private IMainMaterialService mainMaterialService;

    /**
     * 查询主料列表
     */
    @PreAuthorize("@ss.hasPermi('erp:material:list')")
    @GetMapping("/list")
    public TableDataInfo list(MainMaterial mainMaterial) {
        startPage();
        List<MainMaterial> list = mainMaterialService.selectMainMaterialList(mainMaterial);
        return getDataTable(list);
    }

    /**
     * 导出主料列表
     */
    @PreAuthorize("@ss.hasPermi('erp:material:export')")
    @Log(title = "主料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MainMaterial mainMaterial) {
        List<MainMaterial> list = mainMaterialService.selectMainMaterialList(mainMaterial);
        ExcelUtil<MainMaterial> util = new ExcelUtil<MainMaterial>(MainMaterial.class);
        util.exportExcel(response, list, "主料数据");
    }

    /**
     * 获取主料详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:material:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(mainMaterialService.selectMainMaterialById(id));
    }

    /**
     * 新增主料
     */
    @PreAuthorize("@ss.hasPermi('erp:material:add')")
    @Log(title = "主料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MainMaterial mainMaterial) {
        return toAjax(mainMaterialService.insertMainMaterial(mainMaterial));
    }

    /**
     * 修改主料
     */
    @PreAuthorize("@ss.hasPermi('erp:material:edit')")
    @Log(title = "主料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MainMaterial mainMaterial) {
        return toAjax(mainMaterialService.updateMainMaterial(mainMaterial));
    }

    /**
     * 删除主料
     */
    @PreAuthorize("@ss.hasPermi('erp:material:remove')")
    @Log(title = "主料", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(mainMaterialService.deleteMainMaterialByIds(ids));
    }

    /**
     * 下载导入模板
     */
    @PreAuthorize("@ss.hasPermi('erp:material:import')")
    @GetMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<MainMaterial> util = new ExcelUtil<MainMaterial>(MainMaterial.class);
        util.importTemplateExcel(response, "主料数据");
    }

    /**
     * 导入主料数据
     */
    @PreAuthorize("@ss.hasPermi('erp:material:import')")
    @Log(title = "主料导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        log.info("========== 接收到主料导入请求 ==========");
        log.info("文件名: {}, 文件大小: {} bytes", file.getOriginalFilename(), file.getSize());
        log.info("是否支持更新: {}", updateSupport);
        
        try {
            ExcelUtil<MainMaterial> util = new ExcelUtil<MainMaterial>(MainMaterial.class);
            log.info("开始读取Excel文件...");
            List<MainMaterial> list = util.importExcel(file.getInputStream());
            
            log.info("Excel读取完成，读取到 {} 条数据", list.size());
            for (int i = 0; i < list.size(); i++) {
                log.info("读取到第 {} 条数据: {}", i + 1, list.get(i));
            }
            
            String message = mainMaterialService.importMaterial(list, updateSupport);
            log.info("导入处理完成，返回消息: {}", message);
            return success(message);
        } catch (Exception e) {
            log.error("导入主料数据异常", e);
            throw e;
        }
    }

    /**
     * 快速批量导入主料数据（使用INSERT IGNORE，更快速）
     */
    @PreAuthorize("@ss.hasPermi('erp:material:import')")
    @Log(title = "主料快速导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importDataFast")
    public AjaxResult importDataFast(MultipartFile file) throws Exception {
        log.info("========== 接收到主料快速导入请求 ==========");
        log.info("文件名: {}, 文件大小: {} bytes", file.getOriginalFilename(), file.getSize());
        
        try {
            ExcelUtil<MainMaterial> util = new ExcelUtil<MainMaterial>(MainMaterial.class);
            log.info("开始读取Excel文件...");
            List<MainMaterial> list = util.importExcel(file.getInputStream());
            
            log.info("Excel读取完成，读取到 {} 条数据", list.size());
            for (int i = 0; i < list.size(); i++) {
                log.info("读取到第 {} 条数据: {}", i + 1, list.get(i));
            }
            
            String message = mainMaterialService.importMaterialFast(list);
            log.info("快速导入处理完成，返回消息: {}", message);
            return success(message);
        } catch (Exception e) {
            log.error("快速导入主料数据异常", e);
            throw e;
        }
    }

    /**
     * 根据选中的辅助属性生成SKU笛卡尔积矩阵
     */
    @PreAuthorize("@ss.hasPermi('erp:material:generateSku')")
    @PostMapping("/generate-sku/{materialId}")
    public AjaxResult generateSkuMatrix(@PathVariable Long materialId, @RequestBody List<AuxPropertySelection> auxProperties) {
        List<MaterialSku> skuList = mainMaterialService.generateSkuMatrix(materialId, auxProperties);
        return success(skuList);
    }
}
