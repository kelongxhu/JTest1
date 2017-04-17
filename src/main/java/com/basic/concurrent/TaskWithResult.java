package com.basic.concurrent;

import java.util.concurrent.Callable;

/**
 * @author kelong
 * @date 6/11/15
 */
public class TaskWithResult implements Callable<String> {
    private int id;

    public TaskWithResult(int id) {
        this.id = id;
    }


    public String call() throws Exception {
        Thread.sleep(id*1000);
        System.out.println("result of TaskWithResult "+id);
        return "result of TaskWithResult " + id;
    }
}
