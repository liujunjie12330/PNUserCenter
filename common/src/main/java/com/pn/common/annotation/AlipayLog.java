package com.pn.common.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)  // 作用于方法
@Retention(RetentionPolicy.RUNTIME)  // 运行时可用
public @interface AlipayLog {
    String value() default "";
}
