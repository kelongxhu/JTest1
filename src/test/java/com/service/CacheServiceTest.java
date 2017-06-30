package com.service;

import com.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author kelong
 * @since 2017/6/30 15:03
 */
public class CacheServiceTest extends BaseTest {

    @Resource
    private CacheProvider cacheProvider;

    @Test
    public void getValueTest() {
        System.out.println(cacheProvider.getValue("1000","Hello"));
    }
}
