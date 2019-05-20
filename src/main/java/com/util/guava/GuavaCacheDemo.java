package com.util.guava;

import com.google.common.base.Joiner;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author ke.long
 * @since 2019/4/29 0:11
 */
public class GuavaCacheDemo {
    Cache<String, String> cache=null;


    public GuavaCacheDemo(){
        CacheBuilder.newBuilder()
                .refreshAfterWrite(Duration.ofMinutes(10))
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        // 缓存加载逻辑
                        return Joiner.on("-").join(key,"hello");
                    }
                });
    }
}
