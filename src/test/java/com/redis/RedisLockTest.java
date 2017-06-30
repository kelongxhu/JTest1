package com.redis;

import com.redis.lock.RedisLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

/**
 * @author codethink
 * @date 12/27/16 3:04 PM
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class RedisLockTest {

    @Autowired
    private RedisTemplate<String, String> valueTemplate;


    @Test
    public void lockTest() {
        System.out.println(Thread.currentThread().getName()+" Calling");
        String name = "hello";
        Lock lock = new RedisLock(valueTemplate,"lockKey");
        lock.lock();
        for (int i = 0; i < name.length();i++) {
            System.out.print(name.charAt(i));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("执行完毕:"+Thread.currentThread().getName());
        lock.unlock();
    }

    @Test
    public void test()throws Exception{
        for (int i=0;i<5;i++){
            new Thread(new Runnable() {
                public void run() {
                    lockTest();
                }
            }).start();
        }

        CountDownLatch latch=new CountDownLatch(1);
        latch.await();
    }

    @Test
    public void t()throws Exception{
        long nano=System.nanoTime();
        System.out.println(nano);
        Thread.sleep(3,100);
        System.out.println(System.nanoTime()-nano);
    }

}
