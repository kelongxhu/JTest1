package com.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RUNTIME)
public @interface Validate {

}
