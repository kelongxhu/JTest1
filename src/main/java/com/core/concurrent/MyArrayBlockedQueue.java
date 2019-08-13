package com.core.concurrent;

import java.util.Random;

public class MyArrayBlockedQueue<T> {
    /**
     * 队列数量
     */
    private int count = 0;
    /**
     * 最终的数据存储
     */
    private Object[] items;
    /**
     * 队列满时的阻塞锁
     */
    private Object full = new Object();
    /**
     * 队列空时的阻塞锁
     */
    private Object empty = new Object();

    public MyArrayBlockedQueue(int size) {
        items = new Object[size];
    }

    /**
     * 从队列尾写入数据
     * @param t
     */
    public void put(T t) {
        synchronized (full) {
            while (count == items.length) {
                try {
                    System.out.println("队列已满，无法继续放入元素");
                    full.wait();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

        synchronized (empty) {
            items[count++] = t;
            System.out.println("放入元素");
            System.out.println("此时队列的元素个数为" + size());
            empty.notify();
        }

    }

    /**
     * 从队列头获取数据
     * @return
     */
    public T take() {

        synchronized (empty) {
            while (count == 0) {
                try {
                    System.out.println("队列为空，无元素可取");
                    empty.wait();
                } catch (InterruptedException e) {
                    return null;
                }
            }
        }
        synchronized (full) {
            Object result = items[--count];
            System.out.println("取出元素");
            System.out.println("此时队列的元素个数为" + size());
            full.notify();
            return (T) result;
        }
    }
    /**
     * 获取队列大小
     * @return
     */
    public int size() {
        return count;
    }


    /**
     * 判断队列是否为空
     * @return
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    public static void main(String[] args) throws InterruptedException {
        MyArrayBlockedQueue queue = new MyArrayBlockedQueue<>(5);
        Random r = new Random();
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    while (true) {
                        queue.take();
                        Thread.sleep(2000);
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
                        Thread.sleep(r.nextInt(500));
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
