package com.redis;

import com.BaseTest;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * @author ke.long
 * @since 2019/4/2 11:16
 */
@Slf4j
public class RedisTemplateTest extends BaseTest {

    @Autowired
    private RedisTemplate<String, String> valueTemplate;
    @Test
    public void test(){
        List<String> lists= Lists.newArrayList("a","b");
        List<String> values = valueTemplate.<String, String>opsForHash().multiGet("", lists);
        log.info("{}",values);
    }
}
