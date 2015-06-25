package com.concurrent;

import org.junit.Test;

import java.util.concurrent.FutureTask;

/**
 * @author kelong
 * @date 6/16/15
 */
public class TaskTest {
    @Test
    public void funtureRunableTest()throws Exception{
        FutureTask task=new FutureTask(new TaskRunable(10),"success");
        Thread thread = new Thread(task);
        thread.start();
        Object result=task.get();
        System.out.println(result);
    }

    @Test
    public void funtureCallableTest()throws Exception{
        FutureTask task=new FutureTask(new TaskWithResult(10));
        Thread thread=new Thread(task);
        thread.start();
        System.out.println(task.get());
    }
}
