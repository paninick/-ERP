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
import com.ruoyi.erp.domain.ProcessRoute;
import com.ruoyi.erp.domain.ProcessRouteItem;
import com.ruoyi.erp.service.IProcessRouteService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工艺路线Controller
 *
 * @author zhangmingjian
 * @date 2026-04-15
 */
@RestController
@RequestMapping("/erp/processRoute")
public class ProcessRouteController extends BaseController {
    @Autowired
    private IProcessRouteService processRouteService;

    /**
     * 查询工艺路线列表
     */
    @PreAuthorize("@ss.hasPermi('erp:processRoute:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProcessRoute processRoute) {
        startPage();
        List<ProcessRoute> list = processRouteService.selectProcessRouteList(processRoute);
        return getDataTable(list);
    }

    /**
     * 导出工艺路线列表
     */
    @PreAuthorize("@ss.hasPermi('erp:processRoute:export')")
    @Log(title = "工艺路线", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProcessRoute processRoute) {
        List<ProcessRoute> list = processRouteService.selectProcessRouteList(processRoute);
        ExcelUtil<ProcessRoute> util = new ExcelUtil<ProcessRoute>(ProcessRoute.class);
        util.exportExcel(response, list, "工艺路线数据");
    }

    /**
     * 获取工艺路线详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:processRoute:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        ProcessRoute route = processRouteService.selectProcessRouteById(id);
        List<ProcessRouteItem> items = processRouteService.selectProcessRouteItemByRouteId(id);
        AjaxResult result = success(route);
        result.put("items", items);
        return result;
    }

    /**
     * 获取工艺路线明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:processRoute:query')")
    @GetMapping(value = "/items/{routeId}")
    public AjaxResult getItems(@PathVariable Long routeId) {
        List<ProcessRouteItem> items = processRouteService.selectProcessRouteItemByRouteId(routeId);
        return success(items);
    }

    /**
     * 新增工艺路线
     */
    @PreAuthorize("@ss.hasPermi('erp:processRoute:add')")
    @Log(title = "工艺路线", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProcessRouteWithItems routeWithItems) {
        return toAjax(processRouteService.insertProcessRoute(routeWithItems.getRoute(), routeWithItems.getItems()));
    }

    /**
     * 修改工艺路线
     */
    @PreAuthorize("@ss.hasPermi('erp:processRoute:edit')")
    @Log(title = "工艺路线", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProcessRouteWithItems routeWithItems) {
        return toAjax(processRouteService.updateProcessRoute(routeWithItems.getRoute(), routeWithItems.getItems()));
    }

    /**
     * 删除工艺路线
     */
    @PreAuthorize("@ss.hasPermi('erp:processRoute:remove')")
    @Log(title = "工艺路线", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(processRouteService.deleteProcessRouteByIds(ids));
    }

    /**
     * 封装工艺路线和明细一起传递
     */
    public static class ProcessRouteWithItems {
        private ProcessRoute route;
        private List<ProcessRouteItem> items;

        public ProcessRoute getRoute() {
            return route;
        }

        public void setRoute(ProcessRoute route) {
            this.route = route;
        }

        public List<ProcessRouteItem> getItems() {
            return items;
        }

        public void setItems(List<ProcessRouteItem> items) {
            this.items = items;
        }
    }
}
