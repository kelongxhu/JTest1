package com.common.annotation;

/**
 * @author kelong
 * @since 2017/6/30 16:00
 */
public @interface CacheEvict {
    /**自定义key*/
    String key() default "";
}
