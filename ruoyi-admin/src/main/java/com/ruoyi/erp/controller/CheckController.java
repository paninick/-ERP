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
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.erp.domain.Check;
import com.ruoyi.erp.service.ICheckService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 大货核版Controller
 *
 * @author ruoyi
 * @date 2026-04-10
 */
@RestController
@RequestMapping("/erp/check")
public class CheckController extends BaseController
{
    @Autowired
    private ICheckService checkService;

    /**
     * 查询大货核版列表
     */
    @PreAuthorize("@ss.hasPermi('erp:check:list')")
    @GetMapping("/list")
    public TableDataInfo list(Check check)
    {
        startPage();
        List<Check> list = checkService.selectCheckList(check);
        return getDataTable(list);
    }

    /**
     * 导出大货核版列表
     */
    @PreAuthorize("@ss.hasPermi('erp:check:export')")
    @Log(title = "大货核版", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Check check)
    {
        List<Check> list = checkService.selectCheckList(check);
        ExcelUtil<Check> util = new ExcelUtil<Check>(Check.class);
        util.exportExcel(response, list, "大货核版数据");
    }

    /**
     * 获取大货核版详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:check:query')")
    @GetMapping(value = "/{checkId}")
    public AjaxResult getInfo(@PathVariable("checkId") Long checkId)
    {
        return success(checkService.selectCheckByCheckId(checkId));
    }

    /**
     * 新增大货核版
     */
    @PreAuthorize("@ss.hasPermi('erp:check:add')")
    @Log(title = "大货核版", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Check check)
    {
        return toAjax(checkService.insertCheck(check));
    }

    /**
     * 修改大货核版
     */
    @PreAuthorize("@ss.hasPermi('erp:check:edit')")
    @Log(title = "大货核版", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Check check)
    {
        return toAjax(checkService.updateCheck(check));
    }

    /**
     * 删除大货核版
     */
    @PreAuthorize("@ss.hasPermi('erp:check:remove')")
    @Log(title = "大货核版", businessType = BusinessType.DELETE)
	@DeleteMapping("/{checkIds}")
    public AjaxResult remove(@PathVariable Long[] checkIds)
    {
        return toAjax(checkService.deleteCheckByIds(checkIds));
    }

    /**
     * 审批大货核版
     */
    @PreAuthorize("@ss.hasPermi('erp:check:approve')")
    @Log(title = "大货核版审批", businessType = BusinessType.UPDATE)
    @PostMapping("/approve")
    public AjaxResult approve(@RequestBody Check check)
    {
        return toAjax(checkService.approveCheck(check));
    }

    /**
     * 导入大货核版数据
     */
    @PreAuthorize("@ss.hasPermi('erp:check:import')")
    @Log(title = "大货核版导入", businessType = BusinessType.IMPORT)
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
            ExcelUtil<Check> util = new ExcelUtil<Check>(Check.class);
            List<Check> list = util.importExcel(file.getInputStream());
            System.out.println("解析出的数据条数: " + (list != null ? list.size() : 0));

            if (list != null && !list.isEmpty()) {
                System.out.println("=== 前5条解析数据 ===");
                for (int i = 0; i < Math.min(5, list.size()); i++) {
                    Check check = list.get(i);
                    System.out.println("记录[" + i + "]:");
                    System.out.println("  checkNo=" + check.getCheckNo());
                    System.out.println("  styleNo=" + check.getStyleNo());
                    System.out.println("  customerName=" + check.getCustomerName());
                }
                System.out.println("=== 前5条解析数据结束 ===");
            }

            String message = checkService.importCheck(list, updateSupport);
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
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<Check> util = new ExcelUtil<Check>(Check.class);
        util.importTemplateExcel(response, "大货核版数据");
    }
}
