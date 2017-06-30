package com.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 是否可缓存
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {
    /**缓存key的前缀*/
    String prefix() default "";

    /**自定义key*/
    String key() default "";

    /**缓存有效期（默认5分钟）*/
    long expire() default 5 * 60;

    /**如果是集合需要指定返回值类型*/
    Class<?> type() default Object.class;

}
