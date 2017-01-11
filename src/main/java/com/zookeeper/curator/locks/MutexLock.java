package com.zookeeper.curator.locks;

import com.zookeeper.curator.ClientFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author kelong
 * @date 12/28/16.
 */
public class MutexLock {
    public static void main(String[] args) {
            CuratorFramework curator = ClientFactory.newClient();
            curator.start();
            final InterProcessMutex lock = new InterProcessMutex(curator, "/global_lock");

            Executor pool = Executors.newFixedThreadPool(10);
            for (int i = 0; i < 10; i ++) {
                pool.execute(new Runnable() {
                    public void run() {
                        try {
                            lock.acquire();
                            String message=String.format("execute-----%s",Thread.currentThread().getName());
                            System.out.println(message);
                            TimeUnit.SECONDS.sleep(5);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }finally{
                            try {
                                lock.release();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
    }
}
