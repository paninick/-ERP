package com.ruoyi.demo.exception;

import com.ruoyi.common.core.domain.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * 
 * @author ruoyi
 * @date 2026-04-01
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public AjaxResult handleBusinessException(BusinessException e) {
        log.error("业务异常：{}", e.getMessage());
        return AjaxResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理系统异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e) {
        log.error("系统异常：", e);
        return AjaxResult.error("系统异常，请联系管理员");
    }

    /**
     * 处理空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public AjaxResult handleNullPointerException(NullPointerException e) {
        log.error("空指针异常：", e);
        return AjaxResult.error("系统异常：数据为空");
    }

    /**
     * 处理数字转换异常
     */
    @ExceptionHandler(NumberFormatException.class)
    public AjaxResult handleNumberFormatException(NumberFormatException e) {
        log.error("数字转换异常：", e);
        return AjaxResult.error("系统异常：数字格式错误");
    }

    /**
     * 处理数组越界异常
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public AjaxResult handleIndexOutOfBoundsException(IndexOutOfBoundsException e) {
        log.error("数组越界异常：", e);
        return AjaxResult.error("系统异常：数据索引错误");
    }
}
