package com.ruoyi.erp.controller.system;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.AjaxResult;

@RestController
public class HealthController {

    @Autowired(required = false)
    private DataSource dataSource;

    @Anonymous
    @GetMapping({"/erp/health", "/profile/health"})
    public AjaxResult health() {
        Map<String, Object> status = new LinkedHashMap<>();
        status.put("status", "UP");
        status.put("service", "ERP-MES");

        try {
            if (dataSource != null) {
                dataSource.getConnection().isValid(3);
                status.put("database", "UP");
            } else {
                status.put("database", "UNKNOWN");
            }
        } catch (Exception e) {
            status.put("database", "DOWN");
            status.put("status", "DEGRADED");
        }

        status.put("java_version", System.getProperty("java.version"));
        status.put("available_processors", Runtime.getRuntime().availableProcessors());
        status.put("free_memory_mb", Runtime.getRuntime().freeMemory() / 1024 / 1024);

        return AjaxResult.success(status);
    }
}
