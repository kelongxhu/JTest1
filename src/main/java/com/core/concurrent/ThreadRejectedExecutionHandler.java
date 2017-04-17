package com.core.concurrent;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author codethink
 * @date 1/9/17 11:16 AM
 */
public class ThreadRejectedExecutionHandler implements RejectedExecutionHandler {

    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        if (!e.isShutdown()) {
            e.getQueue().poll();
            e.execute(r);

//            System.out.println("Reject.....");
        }
    }
}
