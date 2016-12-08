package com.concurrent;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @author kelong
 * @date 6/11/15
 */
public class CallableTest {
    public static void main(String[] args) throws InterruptedException,
        ExecutionException {
        ExecutorService exec = Executors.newCachedThreadPool();
        ArrayList<Future<String>> results = new ArrayList<Future<String>>();    //Future 相当于是用来存放Executor执行的结果的一种容器
        for (int i = 0; i < 10; i++) {
            results.add(exec.submit(new TaskWithResult(i)));
        }
//        for (Future<String> fs : results) {
////            if (fs.isDone()) {
////                System.out.println(fs.get());
////            } else {
////                System.out.println("Future result is not yet complete");
////            }
//            System.out.println(fs.get());
//        }

        exec.shutdown();//ExecutorService停止接受任何新的任务且等待已经提交的任务执行完成

        if(exec.isShutdown()){
            System.out.println("Completed");
        }

        while (!exec.awaitTermination(1, TimeUnit.SECONDS)) {
            System.out.println("线程池没有关闭");
        }



//        System.out.println("Completed");
//        exec.shutdown();
    }
}
