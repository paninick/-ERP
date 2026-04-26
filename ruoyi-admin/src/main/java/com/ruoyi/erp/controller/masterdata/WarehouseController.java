package com.ruoyi.erp.controller.masterdata;

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
import com.ruoyi.erp.domain.Warehouse;
import com.ruoyi.erp.service.IWarehouseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 库区管理Controller
 *
 * @author zhangmingjian
 * @date 2026-04-07
 */
@RestController
@RequestMapping("/erp/warehouse")
public class WarehouseController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WarehouseController.class);
    @Autowired
    private IWarehouseService warehouseService;

    /**
     * 查询库区管理列表
     */
    @PreAuthorize("@ss.hasPermi('erp:warehouse:list')")
    @GetMapping("/list")
    public TableDataInfo list(Warehouse warehouse) {
        startPage();
        List<Warehouse> list = warehouseService.selectWarehouseList(warehouse);
        return getDataTable(list);
    }

    /**
     * 导出库区管理列表
     */
    @PreAuthorize("@ss.hasPermi('erp:warehouse:export')")
    @Log(title = "库区管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Warehouse warehouse) {
        List<Warehouse> list = warehouseService.selectWarehouseList(warehouse);
        ExcelUtil<Warehouse> util = new ExcelUtil<Warehouse>(Warehouse.class);
        util.exportExcel(response, list, "库区管理数据");
    }

    /**
     * 获取库区管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:warehouse:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(warehouseService.selectWarehouseById(id));
    }

    /**
     * 新增库区管理
     */
    @PreAuthorize("@ss.hasPermi('erp:warehouse:add')")
    @Log(title = "库区管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Warehouse warehouse) {
        return toAjax(warehouseService.insertWarehouse(warehouse));
    }

    /**
     * 修改库区管理
     */
    @PreAuthorize("@ss.hasPermi('erp:warehouse:edit')")
    @Log(title = "库区管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Warehouse warehouse) {
        return toAjax(warehouseService.updateWarehouse(warehouse));
    }

    /**
     * 删除库区管理
     */
    @PreAuthorize("@ss.hasPermi('erp:warehouse:remove')")
    @Log(title = "库区管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(warehouseService.deleteWarehouseByIds(ids));
    }

    /**
     * 下载导入模板
     */
    @PreAuthorize("@ss.hasPermi('erp:warehouse:import')")
    @GetMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Warehouse> util = new ExcelUtil<Warehouse>(Warehouse.class);
        util.importTemplateExcel(response, "库区管理数据");
    }

    /**
     * 导入库区管理数据
     */
    @PreAuthorize("@ss.hasPermi('erp:warehouse:import')")
    @Log(title = "库区管理导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        log.info("========== 接收到库区管理导入请求 ==========");
        log.info("文件名: {}, 文件大小: {} bytes", file.getOriginalFilename(), file.getSize());
        log.info("是否支持更新: {}", updateSupport);
        
        try {
            ExcelUtil<Warehouse> util = new ExcelUtil<Warehouse>(Warehouse.class);
            log.info("开始读取Excel文件...");
            List<Warehouse> list = util.importExcel(file.getInputStream());
            
            log.info("Excel读取完成，读取到 {} 条数据", list.size());
            for (int i = 0; i < list.size(); i++) {
                log.info("读取到第 {} 条数据: {}", i + 1, list.get(i));
            }
            
            String message = warehouseService.importWarehouse(list, updateSupport);
            log.info("导入处理完成，返回消息: {}", message);
            return success(message);
        } catch (Exception e) {
            log.error("导入库区管理数据异常", e);
            throw e;
        }
    }

    /**
     * 快速批量导入库区管理数据（使用INSERT IGNORE，更快速）
     */
    @PreAuthorize("@ss.hasPermi('erp:warehouse:import')")
    @Log(title = "库区管理快速导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importDataFast")
    public AjaxResult importDataFast(MultipartFile file) throws Exception {
        log.info("========== 接收到库区管理快速导入请求 ==========");
        log.info("文件名: {}, 文件大小: {} bytes", file.getOriginalFilename(), file.getSize());
        
        try {
            ExcelUtil<Warehouse> util = new ExcelUtil<Warehouse>(Warehouse.class);
            log.info("开始读取Excel文件...");
            List<Warehouse> list = util.importExcel(file.getInputStream());
            
            log.info("Excel读取完成，读取到 {} 条数据", list.size());
            for (int i = 0; i < list.size(); i++) {
                log.info("读取到第 {} 条数据: {}", i + 1, list.get(i));
            }
            
            String message = warehouseService.importWarehouseFast(list);
            log.info("快速导入处理完成，返回消息: {}", message);
            return success(message);
        } catch (Exception e) {
            log.error("快速导入库区管理数据异常", e);
            throw e;
        }
    }
}
