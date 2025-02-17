package com.pn.web.handler;

import com.pn.common.base.BaseResponse;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常 BizException
     */
    @ExceptionHandler(BizException.class)
    public BaseResponse handleBizException(BizException e) {
        log.error("Business exception occurred: {}", e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理运行时异常 RuntimeException
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse handleRuntimeException(RuntimeException e) {
        log.error("Runtime exception occurred: {}", e.getMessage(), e);
        return ResultUtils.error(StatusCode.SYSTEM_ERROR.getCode(),  e.getMessage());
    }

    /**
     * 处理所有其他未处理的异常
     */
    @ExceptionHandler(Exception.class)
    public BaseResponse handleException(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return ResultUtils.error(StatusCode.SYSTEM_ERROR.getCode(), "An unexpected error occurred.");
    }
}
