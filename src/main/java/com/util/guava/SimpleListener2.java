package com.util.guava;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.Subscribe;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: ke.long
 * @Date: 2019/11/12 11:26
 */
@Slf4j
public class SimpleListener2 {
    @Subscribe
    public void doAction2(final SimpleEvent2 event) {
        log.info("hello2,{}", JSON.toJSONString(event));
    }
}
