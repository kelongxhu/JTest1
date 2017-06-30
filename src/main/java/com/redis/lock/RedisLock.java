package com.redis.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author kelong
 * @since 2017/5/19 14:12
 */
public class RedisLock implements Lock {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private RedisTemplate<String, String> valueTemplate;

    private static final String LOCKED_VALUE = "TRUE";
    //10s
    private static final long   TIME_OUT     = 1000L * 5;
    //失效时间(机器时间差)
    private static final int    EXPIRE       = 60 * 2;
    //循环获取间隔,5秒
    private static final long   INTERVAL     = 1000L * 5;
    //lock key
    private String lockKey;

    // state flag
    private volatile boolean locked = false;

    public RedisLock(RedisTemplate<String, String> valueTemplate, String lockKey) {
        this.valueTemplate = valueTemplate;
        this.lockKey = lockKey;
    }

    /**
     * 获取锁
     *
     * @param timeout
     */
    private void lock(long timeout) {
        //纳秒
        long nano = System.nanoTime();
        timeout *= 1000000;
        int loopNum = 0;
        try {
            while ((System.nanoTime() - nano) < timeout) {
                if (setNX(lockKey, LOCKED_VALUE)) {
                    // TODO 假设setNx之后，Redis崩了, 恢复后的redis这里会产生死锁, 该锁永远不会超时
                    valueTemplate.expire(lockKey, EXPIRE, TimeUnit.SECONDS);
                    locked = true;
                    logger.info("Get lock success:{},expire:{}", lockKey, EXPIRE);
                    break;
                } else {
                    loopNum++;
                    logger.info("Loop get lock,{}", loopNum);
                }
                //timeout
                Thread.sleep(INTERVAL);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * setNX
     *
     * @param key
     * @param value
     * @return
     */
    private boolean setNX(final String key, final String value) {
        Object obj = null;
        try {
            obj = valueTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    Boolean success = connection.setNX(serializer.serialize(key), serializer.serialize(value));
                    connection.close();
                    return success;
                }
            });
        } catch (Exception e) {
            logger.error("setNX redis error, key : {}", key);
        }
        return obj != null ? (Boolean) obj : false;
    }

    @Override
    public void lock() {
        if (logger.isDebugEnabled()) {
            logger.debug("lock..");
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        if (logger.isDebugEnabled()) {
            logger.debug("lock interrupt..");
        }
    }

    @Override
    public boolean tryLock() {
        lock(TIME_OUT);
        return locked;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        if (locked) {
            valueTemplate.delete(lockKey);
            locked = false;
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
