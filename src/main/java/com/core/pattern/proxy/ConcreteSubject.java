package com.core.pattern.proxy;

/**
 * @author ke.long
 * @since 2019/6/21 15:37
 */
public class ConcreteSubject implements ISubject {
    @Override
    public void action() {
        System.out.println("action!");
    }
}
