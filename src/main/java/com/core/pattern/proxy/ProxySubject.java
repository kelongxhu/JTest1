package com.core.pattern.proxy;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @author ke.long
 * @since 2019/6/21 15:37
 */
@Slf4j
public class ProxySubject implements ISubject {
    private ISubject subject;

    public ProxySubject() {
        subject = new ConcreteSubject();
    }

    /**
     * 并且代理对象可能限制对被代理对象的访问。
     */
    @Override
    public void action() {
        preAction();
        if((new Random()).nextBoolean()){
            subject.action();
        } else {
            log.info("Permission denied");
        }
        postAction();
    }

    private void preAction() {
        log.info("ProxySubject.preAction()");
    }

    private void postAction() {
        log.info("ProxySubject.postAction()");
    }
}
