package com.util.guava;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: ke.long
 * @Date: 2019/11/12 11:04
 */
@Slf4j
public class SimpleListener {
    @Subscribe
    public void doAction(final SimpleEvent event) {
        log.info("hello,{}", JSON.toJSONString(event));
    }
}
