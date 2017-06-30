package com.service.impl;

import com.alibaba.fastjson.JSON;
import com.service.CacheService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis服务实现
 *
 */
@Service
public class CacheServiceImpl implements CacheService {

    //失效时间 单位: 分钟
    private static final int REDIS_KEY_FOR_EXPIRE = 5;

    private RedisTemplate<String, String> valueTemplate;

    @Override
    public <T> T getValue(String key, Class<T> valueClass) {
        String redisValue = valueTemplate.opsForValue().get(key);
        return JSON.parseObject(redisValue, valueClass);
    }

    @Override
    public <T> List<T> getListValue(String key, Class<T> valueClass) {
        String redisValue = valueTemplate.opsForValue().get(key);
        List<T> value = JSON.parseArray(redisValue, valueClass);
        return value;
    }

    @Override
    public void setValue(String key, String value, boolean expire) {
        valueTemplate.opsForValue().set(key, value);
        if (expire) {
            valueTemplate.expire(key, REDIS_KEY_FOR_EXPIRE, TimeUnit.MINUTES);
        }
    }

    @Override
    public void setValue(String key, String value, long timeout) {
        valueTemplate.opsForValue().set(key, value);
        if (timeout > 0) {
            valueTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    @Override
    public String getValue(String key) {
        return valueTemplate.opsForValue().get(key);
    }

    @Override
    public void delete(String key) {
        valueTemplate.delete(key);
    }

    @Override
    public boolean containsKey(String key) {
        return valueTemplate.hasKey(key);
    }

    @Override
    public Long getExpire(String key) {
        return valueTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    @Override
    public Long rightPush(String key, String value) {
        return valueTemplate.opsForList().rightPush(key, value);
    }

    @Override
    public List<String> rangeFromQueue(String key, long start, long end) {
        List<String> result = valueTemplate.opsForList().range(key, start, end);
        if (CollectionUtils.isEmpty(result)) {
            return Collections.emptyList();
        }
        return result;
    }

    @Override
    public Long sizeFromQueue(String key) {
        return valueTemplate.opsForList().size(key);
    }

    @Override
    public String leftPop(String key) {
        return valueTemplate.opsForList().leftPop(key);
    }

    @Override
    public Long increment(String key, long delta) {
        return valueTemplate.opsForValue().increment(key,delta);
    }

    public RedisTemplate<String, String> getValueTemplate() {
        return valueTemplate;
    }

    public void setValueTemplate(RedisTemplate<String, String> valueTemplate) {
        this.valueTemplate = valueTemplate;
    }
}
