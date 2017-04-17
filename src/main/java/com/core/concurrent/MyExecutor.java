package com.core.concurrent;
import java.util.concurrent.*;

public class MyExecutor extends Thread {
    private int index;
    public MyExecutor(int i){
        this.index=i;
    }
    public void run(){
        try{
            System.out.println("["+this.index+"] start....");
            Thread.sleep((int)(10*1000));
            System.out.println("["+this.index+"] end.");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String args[])throws Exception{
//        ExecutorService service=Executors.newFixedThreadPool(4);
//        for(int i=0;i<10;i++){
//            service.execute(new MyExecutor(i));
//            //service.submit(new MyExecutor(i));
//        }
////
//        System.out.println("submit finish");
//        service.shutdown();


        // 构造一个线程池
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 3,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        for (int i = 1; i <= 5; i++) {
            try {
                String task = "task@ " + i;
                System.out.println("创建任务并提交到线程池中：" + task);
                threadPool.execute(new MyExecutor(i));

                Thread.sleep(1000*5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.print(threadPool.getCompletedTaskCount());
        Thread.sleep(1000*10);
        if(threadPool.getCompletedTaskCount()==5){
            System.out.println("complete");
    }
}
}