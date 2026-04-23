package com.ruoyi.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demo module logging helpers.
 */
public class DemoLogUtil {

    private static final Logger log = LoggerFactory.getLogger(DemoLogUtil.class);

    private DemoLogUtil() {
    }

    public static void logOrderOperation(String operation, Long orderId, String orderNo, String message) {
        log.info("[订单操作] operation={}, orderId={}, orderNo={}, message={}",
                operation, orderId, orderNo, message);
    }

    public static void logStyleOperation(String operation, Long styleId, String styleCode, String message) {
        log.info("[款式操作] operation={}, styleId={}, styleCode={}, message={}",
                operation, styleId, styleCode, message);
    }

    public static void logOutsourceOperation(String operation, Long outsourceId, Long orderId, String message) {
        log.info("[外发操作] operation={}, outsourceId={}, orderId={}, message={}",
                operation, outsourceId, orderId, message);
    }

    public static void logScheduleOperation(String operation, Long scheduleId, Long orderId, String message) {
        log.info("[排程操作] operation={}, scheduleId={}, orderId={}, message={}",
                operation, scheduleId, orderId, message);
    }

    public static void logReportOperation(String operation, Long reportId, Long orderId, String message) {
        log.info("[报工操作] operation={}, reportId={}, orderId={}, message={}",
                operation, reportId, orderId, message);
    }

    public static void logCostCalculation(String operation, Long orderId, String styleCode, String message) {
        log.info("[成本计算] operation={}, orderId={}, styleCode={}, message={}",
                operation, orderId, styleCode, message);
    }

    public static void logError(String module, String operation, int errorCode, String errorMessage, Exception e) {
        log.error("[{}] operation={}, errorCode={}, errorMessage={}",
                module, operation, errorCode, errorMessage, e);
    }

    public static void logWarn(String module, String operation, String message) {
        log.warn("[{}] operation={}, message={}", module, operation, message);
    }

    public static void logDebug(String module, String operation, String message) {
        log.debug("[{}] operation={}, message={}", module, operation, message);
    }
}
