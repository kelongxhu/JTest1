package com.core.concurrent;

import java.util.concurrent.*;

/**
 * @author codethink
 * @date 1/9/17 11:03 AM
 */
public class ThreadPoolExcutorTest {
    public static void main(String[] args)throws Exception {
        int  corePoolSize  =    2;
        int  maxPoolSize   =   4;
        long keepAliveTime = 5000;

        ExecutorService threadPoolExecutor =
            new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1),new ThreadRejectedExecutionHandler());

        for (int i=0;i<8;i++){
            Future f=threadPoolExecutor.submit(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("xxxxxxxxxxxxx");
                }
            });
        }
    }
}
