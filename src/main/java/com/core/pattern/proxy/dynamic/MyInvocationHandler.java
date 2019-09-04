package com.core.pattern.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author ke.long
 * @since 2019/8/23 15:13
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("准备向数据库中插入数据");
        Object returnvalue = method.invoke(target, args);
        System.out.println("插入数据库成功");
        return returnvalue;
    }
}
