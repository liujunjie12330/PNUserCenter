package com.pn.common.annotation;


import java.lang.annotation.*;

/**
 * 鉴权注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthCheck {
    /**
     * 必须含有的权限
     * @return
     */
    String[] mustPermission() default "";

    /**
     * 任一权限
     * @return
     */
    String[] anyPermission() default "";
}
