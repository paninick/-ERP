package com.ruoyi.erp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 报表中心兼容接口
 */
@RestController
@RequestMapping("/erp/report")
public class ReportController extends BaseController {

    @PreAuthorize("@ss.hasPermi('erp:report:list')")
    @GetMapping("/overview")
    public TableDataInfo overview() {
        return list();
    }

    @PreAuthorize("@ss.hasPermi('erp:report:list')")
    @GetMapping("/list")
    public TableDataInfo list() {
        List<Map<String, String>> cards = new ArrayList<>();
        cards.add(Map.of(
            "title", "打样总览",
            "desc", "查看打样通知与样衣进度汇总",
            "path", "/erp/overview"
        ));
        cards.add(Map.of(
            "title", "生产看板",
            "desc", "查看生产计划、WIP 与员工排名",
            "path", "/erp/produceboard"
        ));
        cards.add(Map.of(
            "title", "计件工资",
            "desc", "查看月度工资汇总",
            "path", "/erp/piecewage"
        ));
        cards.add(Map.of(
            "title", "工资明细",
            "desc", "查看工资明细与导出数据",
            "path", "/erp/piecewagedetail"
        ));
        return getDataTable(cards);
    }
}
