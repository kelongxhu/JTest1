package com.core.pattern.observer;

/**
 * @author codethink
 * @date 12/20/16 2:54 PM
 */
public interface Subject {

    /*增加观察者*/
    void add(Observer observer);

    /*删除观察者*/
    void del(Observer observer);

    /*通知所有的观察者*/
    void notifyObservers();

    /*自身的操作*/
    void operation();
}
