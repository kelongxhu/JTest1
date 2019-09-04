package com.core.basics;

/**
 * cglib proxy
 * 不能代理final方法
 * @author ke.long
 * @since 2019/8/23 14:59
 */
public class CglibProxyTest {
    public static void main(String[] args) {
        CglibProxy proxy = new CglibProxy();
        UserManager userManager = (UserManager) proxy.getProxy(UserManager.class);
        userManager.method();
    }
}
