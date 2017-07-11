package com.service;

import com.BaseTest;
import com.mq.rabbitmq.Producer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kelong
 * @since 2017/7/11 14:12
 */
public class RabbitmqProducerTest extends BaseTest {
    @Autowired
    private Producer producer;

    @Test
    public void producer() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", "hello rabbitmq");
        for (int i = 0; i < 10; i++) {
            producer.sendQueue("test_mq_exchange", "test_mq_patt", map);
        }
    }
}
