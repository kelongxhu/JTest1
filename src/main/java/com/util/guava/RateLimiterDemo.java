package com.util.guava;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

public class RateLimiterDemo {
    public static void main(String[] args)throws Exception {
//        testNoRateLimiter();
//        testWithRateLimiter();
        fourTest();
    }

    public static void testNoRateLimiter() {
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            System.out.println("call execute.." + i);

        }
        Long end = System.currentTimeMillis();

        System.out.println(end - start);

    }

    public static void testWithRateLimiter() {
        Long start = System.currentTimeMillis();
        RateLimiter limiter = RateLimiter.create(100.0); // 每秒不超过10个任务被提交
        for (int i = 0; i < 300; i++) {
            limiter.acquire(); // 请求RateLimiter, 超过permits会被阻塞
            System.out.println("call execute...." + i);

        }
        Long end = System.currentTimeMillis();

        System.out.println(end - start);

    }

    public static void fourTest() throws InterruptedException {
        /**
         * permitsPerSecond表示每秒新增的令牌数
         * warmupPeriod表示在从冷启动速率过渡到平均速率的时间间隔。
         * unit 时间单位 毫秒
         */
        RateLimiter limiter = RateLimiter.create(5, 2000, TimeUnit.MILLISECONDS);
        for(int i = 1; i < 5;i++) {
            System.out.println(limiter.acquire());
        }
        //线程暂停1秒
        Thread.sleep(1000L);
        for(int i = 1; i < 5;i++) {
            System.out.println(limiter.acquire());
        }
    }

    public static void threeTest() throws Exception{
        //创建了一个桶容量为2且每秒新增2个令牌
        RateLimiter limiter = RateLimiter.create(2);
        //消费一个令牌，此时令牌桶可以满足（返回值为0）
        System.out.println(limiter.acquire());
        //线程暂停2秒
        Thread.sleep(2000L);
        for (int i = 0; i < 5; i++) {
            System.out.println(limiter.acquire());
        }
    }

}



