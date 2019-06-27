package com.core.pattern.proxy;

/**
 * @author ke.long
 * @since 2019/6/21 15:39
 */
public class ProxyClient {

    public static void main(String[] args) {
        ISubject subject = new ProxySubject();
        subject.action();
    }
}
