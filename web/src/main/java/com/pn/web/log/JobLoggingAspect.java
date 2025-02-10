package com.pn.web.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Slf4j
@Component
@Order()
public class JobLoggingAspect {

    /**
     * 自定义特殊地方的日志打印
     */
    @Pointcut("@annotation(com.pn.common.annotation.JobLogging)")
    private void jobMethod() {
    }

    @Around("jobMethod()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = new Date().getTime();
        Object result = null;
        try {
            // 执行完方法的返回值：调用proceed()方法，就会触发切入点方法执行
            result = joinPoint.proceed();
        } catch (Exception e) {
            //如果有异常继续抛
            throw e;
        } finally {
            String name = joinPoint.getSignature().getName();
            log.info("method name：{},start time :{} ,end time:{}", name,startTime,new Date().getTime());
        }
        return result;
    }
}