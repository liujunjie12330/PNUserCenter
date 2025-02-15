package com.pn.common.annotation;

import com.pn.common.enums.IntegralType;

import java.lang.annotation.*;

/**
 * 自定义积分注解
 * @author zhaoyun
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Integral {
    /**
     * 加分类型
     */
    IntegralType actionName() default IntegralType.LOGIN;

    /**
     * 加分用户
     */
    String userId() default "";

    /**
     * 行为类型
     */
    String type() default "";
}
