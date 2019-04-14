package com.core.pattern.observer;

/**
 * @author codethink
 * @date 12/20/16 2:55 PM
 */
public class ObserverTest {

    public static void main(String[] args) {
        Subject sub = new MySubject();
        sub.add(new Observer1());
        sub.add(new Observer2());

        sub.operation();
    }

}
