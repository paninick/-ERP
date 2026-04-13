package com.ruoyi.generator.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.sql.SqlUtil;
import com.ruoyi.generator.config.GenConfig;
import com.ruoyi.generator.domain.GenTable;
import com.ruoyi.generator.domain.GenTableColumn;
import com.ruoyi.generator.service.IGenTableColumnService;
import com.ruoyi.generator.service.IGenTableService;

/**
 * 代码生成 操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/tool/gen")
public class GenController extends BaseController
{
    @Autowired
    private IGenTableService genTableService;

    @Autowired
    private IGenTableColumnService genTableColumnService;

    /**
     * 查询代码生成列表
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:list')")
    @GetMapping("/list")
    public TableDataInfo genList(GenTable genTable)
    {
        startPage();
        List<GenTable> list = genTableService.selectGenTableList(genTable);
        return getDataTable(list);
    }

    /**
     * 获取代码生成信息
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:query')")
    @GetMapping(value = "/{tableId}")
    public AjaxResult getInfo(@PathVariable Long tableId)
    {
        GenTable table = genTableService.selectGenTableById(tableId);
        List<GenTable> tables = genTableService.selectGenTableAll();
        List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(tableId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("info", table);
        map.put("rows", list);
        map.put("tables", tables);
        return success(map);
    }

    /**
     * 查询数据库列表
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:list')")
    @GetMapping("/db/list")
    public TableDataInfo dataList(GenTable genTable)
    {
        startPage();
        List<GenTable> list = genTableService.selectDbTableList(genTable);
        return getDataTable(list);
    }

    /**
     * 查询数据表字段列表
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:list')")
    @GetMapping(value = "/column/{tableId}")
    public TableDataInfo columnList(Long tableId)
    {
        TableDataInfo dataInfo = new TableDataInfo();
        List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(tableId);
        dataInfo.setRows(list);
        dataInfo.setTotal(list.size());
        return dataInfo;
    }

    /**
     * 导入表结构（保存）
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:import')")
    @Log(title = "代码生成", businessType = BusinessType.IMPORT)
    @PostMapping("/importTable")
    public AjaxResult importTableSave(@RequestParam("tables") String tables, @RequestParam("tplWebType") String tplWebType)
    {
        String[] tableNames = Convert.toStrArray(tables);
        // 查询表信息
        List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames);
        genTableService.importGenTable(tableList, tplWebType, SecurityUtils.getUsername());
        return success();
    }

    /**
     * 创建表结构（保存）
     */
    @PreAuthorize("@ss.hasRole('admin')")
    @Log(title = "创建表", businessType = BusinessType.OTHER)
    @PostMapping("/createTable")
    public AjaxResult createTableSave(@RequestParam("sql") String sql, @RequestParam("tplWebType") String tplWebType)
    {
        try
        {
            SqlUtil.filterKeyword(sql);
            List<SQLStatement> sqlStatements = SQLUtils.parseStatements(sql, DbType.mysql);
            List<String> tableNames = new ArrayList<>();
            for (SQLStatement sqlStatement : sqlStatements)
            {
                if (sqlStatement instanceof MySqlCreateTableStatement)
                {
                    MySqlCreateTableStatement createTableStatement = (MySqlCreateTableStatement) sqlStatement;
                    if (genTableService.createTable(createTableStatement.toString()))
                    {
                        String tableName = createTableStatement.getTableName().replaceAll("`", "");
                        tableNames.add(tableName);
                    }
                }
            }
            List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames.toArray(new String[tableNames.size()]));
            String operName = SecurityUtils.getUsername();
            genTableService.importGenTable(tableList, tplWebType, operName);
            return AjaxResult.success();
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            return AjaxResult.error("创建表结构异常");
        }
    }

    /**
     * 修改保存代码生成业务
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:edit')")
    @Log(title = "代码生成", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult editSave(@Validated @RequestBody GenTable genTable)
    {
        genTableService.validateEdit(genTable);
        genTableService.updateGenTable(genTable);
        return success();
    }

    /**
     * 删除代码生成
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:remove')")
    @Log(title = "代码生成", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tableIds}")
    public AjaxResult remove(@PathVariable Long[] tableIds)
    {
        genTableService.deleteGenTableByIds(tableIds);
        return success();
    }

    /**
     * 预览代码
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:preview')")
    @GetMapping("/preview/{tableId}")
    public AjaxResult preview(@PathVariable("tableId") Long tableId) throws IOException
    {
        Map<String, String> dataMap = genTableService.previewCode(tableId);
        return success(dataMap);
    }

    /**
     * 生成代码（下载方式）
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:code')")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/download/{tableName}")
    public void download(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException
    {
        byte[] data = genTableService.downloadCode(tableName);
        genCode(response, data);
    }

    /**
     * 生成代码（自定义路径）
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:code')")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/genCode/{tableName}")
    public AjaxResult genCode(@PathVariable("tableName") String tableName)
    {
        if (!GenConfig.isAllowOverwrite())
        {
            return AjaxResult.error("【系统预设】不允许生成文件覆盖到本地");
        }
        genTableService.generatorCode(tableName);
        return success();
    }

    /**
     * 同步数据库
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:edit')")
    @Log(title = "代码生成", businessType = BusinessType.UPDATE)
    @GetMapping("/synchDb/{tableName}")
    public AjaxResult synchDb(@PathVariable("tableName") String tableName)
    {
        genTableService.synchDb(tableName);
        return success();
    }

    /**
     * 批量生成代码
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:code')")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/batchGenCode")
    public void batchGenCode(HttpServletResponse response, String tables) throws IOException
    {
        String[] tableNames = Convert.toStrArray(tables);
        byte[] data = genTableService.downloadCode(tableNames);
        genCode(response, data);
    }

    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException
    {
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=\"ruoyi.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }

    /**
     * 从ZIP文件导入表（正式库导出zip，解析提取表名）
     */
    @PreAuthorize("@ss.hasPermi('tool:gen:import')")
    @Log(title = "代码生成", businessType = BusinessType.IMPORT)
    @PostMapping("/importZip")
    public AjaxResult importZip(HttpServletRequest request, MultipartFile file)
    {
        List<String> tableNames = new ArrayList<>();
        try
        {
            ZipInputStream zis = new ZipInputStream(file.getInputStream());
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null)
            {
                if (!entry.isDirectory())
                {
                    String name = entry.getName();
                    // Ruoyi生成代码目录结构: main/java/com/xxx/.../domain/TableName.java
                    // 从文件路径提取表名
                    if (name.endsWith(".java"))
                    {
                        // 如果是 Domain 文件，提取类名，转换为下划线表名
                        // 比如 Customer.java -> t_erp_customer
                        int lastSlash = name.lastIndexOf('/');
                        int dot = name.lastIndexOf('.');
                        if (lastSlash >= 0 && dot > lastSlash)
                        {
                            String className = name.substring(lastSlash + 1, dot);
                            // 如果已经在gen表信息中，就是目标表
                            // 这里简单处理：从实体类名转换为表名
                            // 实际导入只需要表名，后端会去数据库读取结构
                            // 所以我们直接返回提取到的表名给前端
                            // 用户点击确定后会正常导入
                        }
                    }
                    // 另一种方式：如果zip是从genCode下载的，表名就是我们要的
                    // 直接找java/domain/ 下的文件名
                    if (name.contains("/domain/") || name.contains("\\domain\\"))
                    {
                        int lastSlash = name.lastIndexOf('/');
                        int dot = name.lastIndexOf('.');
                        if (lastSlash >= 0 && dot > lastSlash)
                        {
                            // 这里不转换，因为用户下载的zip本身就是从正式库生成的
                            // 表名已经在gen库中存在，我们提取文件名没用
                            // 换个思路：zip包中的 sql 目录放的建表语句，解析sql提取表名
                        }
                    }
                    // 如果是 .sql 文件，提取表名
                     // 支持两种格式：
                     // 1. sql/tableName.sql  (标准若依格式)
                     // 2. tableNameMenu.sql  (当前项目格式)
                     if (entry.getName().endsWith(".sql"))
                     {
                         String entryName = entry.getName();
                         String tableName;
                         if (entryName.contains("/sql/"))
                         {
                             // 标准格式: main/resources/sql/t_erp_customer.sql
                             tableName = entryName.substring(entryName.lastIndexOf('/') + 1, entryName.length() - 4);
                         }
                         else if (entryName.endsWith("Menu.sql"))
                         {
                             // 当前项目格式: customerMenu.sql -> customer
                             tableName = entryName.substring(0, entryName.length() - 8);
                         }
                         else
                         {
                             // 其他格式: xxx.sql -> xxx
                             tableName = entryName.substring(0, entryName.length() - 4);
                         }
                         if (!tableNames.contains(tableName))
                         {
                             tableNames.add(tableName);
                         }
                     }
                }
                zis.closeEntry();
            }
            zis.close();

             // 同时从 domain java 文件提取（兼容不同的zip结构）
             // 因为有的zip SQL文件名和实际表名不一致，domain文件名一定正确
             try {
                 ZipInputStream zis2 = new ZipInputStream(file.getInputStream());
                 ZipEntry entry2;
                 while ((entry2 = zis2.getNextEntry()) != null)
                 {
                     if (!entry2.isDirectory())
                     {
                         String entryName = entry2.getName();
                         if (entryName.endsWith(".java") && (entryName.contains("/domain/") || entryName.contains("\\domain\\") || entryName.contains("/erp/domain/")))
                         {
                             int lastSlash = entryName.lastIndexOf('/');
                             if (lastSlash < 0) {
                                 lastSlash = entryName.lastIndexOf('\\');
                             }
                             String fileName = entryName.substring(lastSlash + 1, entryName.length() - 5);
                             // 转换驼峰到下划线
                             String tableName = camelToUnderline(fileName);

                             // 检测是否需要添加前缀
                             // 如果你的项目约定 domain 类名对应表名 t_erp_xxx
                             // 检测当前提取的表名，如果已经有 t_erp_ 前缀就保持，否则添加
                             if (!tableName.startsWith("t_erp_") && !tableName.startsWith("sys_") && !tableName.startsWith("wx_")) {
                                 tableName = "t_erp_" + tableName;
                             }

                             if (!tableNames.contains(tableName))
                             {
                                 tableNames.add(tableName);
                             }
                         }
                     }
                     zis2.closeEntry();
                 }
                 zis2.close();
             } catch (Exception e) {
                 // ignore
             }

             if (tableNames.isEmpty())
             {
                 return AjaxResult.error("未能在ZIP中提取到表，请检查ZIP格式是否为若依生成的代码包");
             }
            if (tableNames.isEmpty())
            {
                return AjaxResult.error("未能在ZIP中提取到表，请检查ZIP格式是否为若依生成的代码包");
            }
            return AjaxResult.success(tableNames);
        }
        catch (Exception e)
        {
            logger.error("解析ZIP失败", e);
            return AjaxResult.error("解析ZIP失败: " + e.getMessage());
        }
    }

    /**
     * 驼峰转下划线
     */
    private String camelToUnderline(String camel)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < camel.length(); i++)
        {
            char c = camel.charAt(i);
            if (Character.isUpperCase(c))
            {
                if (i > 0)
                {
                    sb.append('_');
                }
                sb.append(Character.toLowerCase(c));
            }
            else
            {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}