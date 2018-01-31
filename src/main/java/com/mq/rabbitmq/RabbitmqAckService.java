package com.mq.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

/**
 * @author kelong
 * @since 2017/7/11 14:07
 */
public class RabbitmqAckService implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("消息消费者 = " + JSON.toJSONString(message));
        Thread.sleep(2000);
        //消息的标识，false只确认当前一个消息收到，true确认所有consumer获得的消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        //ack返回false，并重新回到队列，api里面解释得很清楚
      //  channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        //拒绝消息
       // channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
    }
}
