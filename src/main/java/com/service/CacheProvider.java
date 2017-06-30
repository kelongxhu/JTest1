package com.service;

import com.common.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author kelong
 * @since 2017/6/30 15:29
 */
@Service
public class CacheProvider {

    private static final String CACHE_KEY = "CACHE_";

    //#id.concat(#userName)
    @Cacheable(prefix = CACHE_KEY, key = "#id + '_'+ #userName")
    public String getValue(String id, String userName) {
        return String.format("值%S", id);
    }
}
