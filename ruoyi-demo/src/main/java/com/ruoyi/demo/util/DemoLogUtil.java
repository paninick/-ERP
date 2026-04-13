package com.ruoyi.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demo模块日志工具类
 *
 * @author ruoyi
 * @date 2026-04-01
 */
public class DemoLogUtil {

    private static final Logger log = LoggerFactory.getLogger(DemoLogUtil.class);

    private DemoLogUtil() {
    }

    /**
     * 记录订单操作日志
     *
     * @param operation 操作类型
     * @param orderId 订单ID
     * @param orderNo 订单号
     * @param message 日志消息
     */
    public static void logOrderOperation(String operation, Long orderId, String orderNo, String message) {
        log.info("[订单操作] 操作: {}, 订单ID: {}, 订单号: {}, 详情: {}", 
                operation, orderId, orderNo, message);
    }

    /**
     * 记录款式操作日志
     *
     * @param operation 操作类型
     * @param styleId 款式ID
     * @param styleNo 款号
     * @param message 日志消息
     */
    public static void logStyleOperation(String operation, Long styleId, String styleNo, String message) {
        log.info("[款式操作] 操作: {}, 款式ID: {}, 款号: {}, 详情: {}", 
                operation, styleId, styleNo, message);
    }

    /**
     * 记录外发操作日志
     *
     * @param operation 操作类型
     * @param outsourceId 外发单ID
     * @param orderId 订单ID
     * @param message 日志消息
     */
    public static void logOutsourceOperation(String operation, Long outsourceId, Long orderId, String message) {
        log.info("[外发操作] 操作: {}, 外发单ID: {}, 订单ID: {}, 详情: {}", 
                operation, outsourceId, orderId, message);
    }

    /**
     * 记录排程操作日志
     *
     * @param operation 操作类型
     * @param scheduleId 排程ID
     * @param orderId 订单ID
     * @param message 日志消息
     */
    public static void logScheduleOperation(String operation, Long scheduleId, Long orderId, String message) {
        log.info("[排程操作] 操作: {}, 排程ID: {}, 订单ID: {}, 详情: {}", 
                operation, scheduleId, orderId, message);
    }

    /**
     * 记录报工操作日志
     *
     * @param operation 操作类型
     * @param reportId 报工ID
     * @param orderId 订单ID
     * @param message 日志消息
     */
    public static void logReportOperation(String operation, Long reportId, Long orderId, String message) {
        log.info("[报工操作] 操作: {}, 报工ID: {}, 订单ID: {}, 详情: {}", 
                operation, reportId, orderId, message);
    }

    /**
     * 记录成本计算日志
     *
     * @param operation 操作类型
     * @param orderId 订单ID
     * @param styleNo 款号
     * @param message 日志消息
     */
    public static void logCostCalculation(String operation, Long orderId, String styleNo, String message) {
        log.info("[成本计算] 操作: {}, 订单ID: {}, 款号: {}, 详情: {}", 
                operation, orderId, styleNo, message);
    }

    /**
     * 记录错误日志
     *
     * @param module 模块名称
     * @param operation 操作类型
     * @param errorCode 错误码
     * @param errorMessage 错误消息
     * @param e 异常对象
     */
    public static void logError(String module, String operation, int errorCode, String errorMessage, Exception e) {
        log.error("[{}] 操作: {}, 错误码: {}, 错误消息: {}", 
                module, operation, errorCode, errorMessage, e);
    }

    /**
     * 记录警告日志
     *
     * @param module 模块名称
     * @param operation 操作类型
     * @param message 警告消息
     */
    public static void logWarn(String module, String operation, String message) {
        log.warn("[{}] 操作: {}, 警告: {}", module, operation, message);
    }

    /**
     * 记录调试日志
     *
     * @param module 模块名称
     * @param operation 操作类型
     * @param message 调试消息
     */
    public static void logDebug(String module, String operation, String message) {
        log.debug("[{}] 操作: {}, 调试: {}", module, operation, message);
    }
}
