package com.core.concurrent;

/**
 * @author ke.long
 * @since 2019/7/4 11:20
 */

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

//我们自己来实现个阻塞队列,主要实现方法是size(),take(),put()即可
public class MyBlockedQueue<E> {
    private ReentrantLock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    //下面是队列的属性
    private Object[] items;
    public volatile int count = 0;

    MyBlockedQueue(int size) {
        items = new Object[size];
    }

    public void put(Object obj) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                System.out.println("队列已满，无法继续放入元素");
                notFull.await();
            }
            items[count++] = obj;
            System.out.println("放入元素");
            System.out.println("此时队列的元素个数为" + size());
            notEmpty.signal();

        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        Object obj;
        lock.lock();
        try {
            if (count == 0) {
                System.out.println("队列为空，无元素可取");
                notEmpty.await();
            }

            obj = items[--count];
            System.out.println("取出元素");
            System.out.println("此时队列的元素个数为" + size());
            notFull.signal();

            return obj;

        } finally {
            lock.unlock();
        }

    }

    public int size() {
        return this.count;
    }

    public static void main(String[] args) throws InterruptedException {
        MyBlockedQueue queue = new MyBlockedQueue<>(10);
        Random r = new Random();
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    while (true) {
                        queue.take();
                        Thread.sleep(r.nextInt(900));

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    while (true) {
                        queue.put(1);
                        Thread.sleep(r.nextInt(1000));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }

}
