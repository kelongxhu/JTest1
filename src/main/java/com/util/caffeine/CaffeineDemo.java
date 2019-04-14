package com.util.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author ke.long
 * @since 2019/3/26 9:55
 */
@Slf4j
public class CaffeineDemo {
    public static void main(String[] args) {
        Cache<String, String> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .maximumSize(10)
                .build();
        cache.put("hello", "hello");
        log.info("{}", cache.getIfPresent("hello"));
        log.info("{}", cache.get("hello2", k -> "xxxx"));
        //手动失效
        cache.invalidate("hello");

    }
}
