package com.mq.rocketmq;

/**
 * @author kelong
 * @date 3/11/16
 */
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

public class Consumer {
    public static void main(String[] args){
        DefaultMQPushConsumer consumer =
            new DefaultMQPushConsumer("PushConsumer");
        consumer.setMessageModel(MessageModel.BROADCASTING);
        consumer.setNamesrvAddr("172.26.50.24:9876");
        try {
            //订阅PushTopic下Tag为push的消息
            consumer.subscribe("A", "*");
            //程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(
                ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(
                new MessageListenerConcurrently() {
                    public ConsumeConcurrentlyStatus consumeMessage(
                        List<MessageExt> list,
                        ConsumeConcurrentlyContext Context) {
                        Message msg = list.get(0);
                        System.out.println(new String(msg.getBody())+"=============="+msg.toString());
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                }
            );
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
