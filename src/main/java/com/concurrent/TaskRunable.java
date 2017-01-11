package com.concurrent;

/**
 * @author kelong
 * @date 6/16/15
 */
public class TaskRunable implements Runnable {
    private int i;

    public TaskRunable(int i) {
        this.i = i;
    }


    public void run() {
        System.out.println("result:" + i);
    }
}
