package com.jayson.spzx.common.exception;

import com.jayson.spzx.model.vo.common.Result;
import com.jayson.spzx.model.vo.common.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: Jayson_Y
 * @date: 2024/8/25
 * @project: spzx-parent
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 全局异常处理
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e) {
        log.error("businessException", e);
        return Result.build(null, ResultCodeEnum.SYSTEM_ERROR, e.getMessage());
    }

    // 自定义异常处理
    @ExceptionHandler(BusinessException.class)
    public Result businessExceptionHandler(BusinessException e) {
        log.error("businessException" + e.getMessage(), e);
        return Result.build(null, e.getResultCodeEnum());
    }
}
