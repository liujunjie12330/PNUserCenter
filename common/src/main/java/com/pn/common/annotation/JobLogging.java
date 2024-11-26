package com.pn.common.annotation;

import java.lang.annotation.*;

/**
 * 任务日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JobLogging {
    String value() default "";
}
