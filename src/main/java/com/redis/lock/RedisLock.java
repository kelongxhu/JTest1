package com.redis.lock;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author codethink
 * @date 12/27/16 2:41 PM
 */

public class RedisLock implements Lock {

    private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);

    // lock flag stored in redis
    private static final String LOCKED = "TRUE";

    // timeout(ms)
    private static final long TIME_OUT = 300000;

    // lock expire time(s)
    public static final int EXPIRE = 60000;

    private Jedis jedis;

    private String key;

    // state flag
    private volatile boolean locked = false;


    private static ConcurrentMap<String, RedisLock> map = Maps.newConcurrentMap();


    public RedisLock(String key) {
        this.key = "lock_" + key;
        jedis = new Jedis("127.0.0.1", 6379);
    }

    public static RedisLock getInstance(String key) {
        if (map.get(key) == null) {
            map.put(key, new RedisLock(key));
        }
        return map.get(key);
    }

    public void lock(long timeout) {
        long nano = System.nanoTime();
        final Random r = new Random();
        try {
            long time=System.nanoTime() - nano;
            timeout*=1000000;//转换为拉秒
            while (time < timeout) {
                long value = jedis.setnx(key, LOCKED);
                if (value == 1) {
                    jedis.expire(key, EXPIRE);
                    locked = true;
                    logger.info("add RedisLock[" + key + "].");
                    break;
                }
                Thread.sleep(3, r.nextInt(500));
            }
        } catch (Exception e) {
        }
    }

    public void unlock() {
        if (locked) {
            logger.info("release RedisLock[" + key + "].");
            jedis.del(key);
        } else {
            logger.info("not get RedisLock");
        }
    }

    public void lock() {
        lock(TIME_OUT);
    }

    public void lockInterruptibly() throws InterruptedException {

    }

    public Condition newCondition() {
        return null;
    }

    public boolean tryLock() {
        return false;
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }
}

