package com.pn.web.handler;


import com.pn.common.base.BaseResponse;
import com.pn.common.enums.StatusCode;
import com.pn.common.exception.BizException;
import com.pn.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author:liujunjie
 * @version:1.0 Time:16:50
 * CreatedBy:IntelliJ IDEA
 * ClassName:GlobalExceptionHandler
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 自定义异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BizException.class)
    public BaseResponse BizException(BizException e) {
        log.info(e.getMessage());
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    /**
     * 服务器内部错误处理
     */
    @ExceptionHandler({RuntimeException.class})
    public BaseResponse runtimeException(RuntimeException e) {
        log.info(e.getMessage());
        return ResultUtils.error(null,500,e.getMessage());
    }
}
