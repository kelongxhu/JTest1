package com.core.pattern.proxy.dynamic;

import com.core.pattern.proxy.ConcreteSubject;
import com.core.pattern.proxy.ISubject;

import java.lang.reflect.Proxy;

/**
 * @author ke.long
 * @since 2019/8/23 15:47
 */
public class MyInvocationHandlerTest {
    public static void main(String[] args) {
        ISubject iSubject=new ConcreteSubject();
        MyInvocationHandler handler=new MyInvocationHandler(iSubject);
        ISubject proxy=(ISubject) Proxy.newProxyInstance(MyInvocationHandlerTest.class.getClassLoader(),
                iSubject.getClass().getInterfaces(),handler);
        proxy.action();
    }
}
