package com.pattern.observer;

/**
 * @author codethink
 * @date 12/20/16 2:55 PM
 */
public class MySubject extends AbstractSubject {

    public void operation() {
        System.out.println("update self!");
        notifyObservers();
    }

}
