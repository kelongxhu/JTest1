package com.mq.rabbitmq;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @author kelong
 * @since 2017/7/11 14:07
 */
public class RabbitmqService implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("消息消费者 = " + JSON.toJSONString(message));
    }
}
