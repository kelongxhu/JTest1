package com.mq.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kelong
 * @since 2017/7/11 14:10
 */
//@Service
public class Producer {
    //@Autowired
    private AmqpTemplate amqpTemplate;

    public void sendQueue(String exchange_key, String queue_key, Object object) {
        amqpTemplate.convertAndSend(exchange_key, queue_key, object);
    }
}
