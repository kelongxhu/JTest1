package com.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author kelong
 * @since 2017/6/22 14:11
 */
public class ProxyUtil {
    /**
     * JDK代理类的原始类的属性名
     */
    private static final String JDK_PROXY_USER_CLASS_FIELD_KEY = "type";

    /**
     * 通过JDK代理类对象获取其原始类
     * @param target	JDK代理对象
     * @return
     */
    public static Class<?> getJDKProxyUserClass(Object target) {
        try {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(target);

            Field field = invocationHandler.getClass().getDeclaredField(JDK_PROXY_USER_CLASS_FIELD_KEY);
            field.setAccessible(true);

            return (Class<?>)field.get(invocationHandler);
        } catch (Exception e) {
            return null;
        }
    }
}
