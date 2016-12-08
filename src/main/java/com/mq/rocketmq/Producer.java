package com.mq.rocketmq;

/**
 * @author kelong
 * @date 3/11/16
 */
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

public class Producer {
    public static void main(String[] args){
        DefaultMQProducer producer = new DefaultMQProducer("Producer");
        producer.setNamesrvAddr("172.26.50.24:9876");
        try {
            producer.start();

            for(int i=1;i<2;i++){
                Message msg = new Message("A",
                    "tag4",
                    ("Just for test."+i).getBytes());
                SendResult result = producer.send(msg);
                System.out.println("id:" + result.getMsgId() +
                    " result:" + result.getSendStatus());
            }



//            msg = new Message("PushTopic",
//                "push",
//                "2",
//                "Just for test2.".getBytes());
//
//            result = producer.send(msg);
//            System.out.println("id:" + result.getMsgId() +
//                " result:" + result.getSendStatus());
//
//            msg = new Message("PullTopic",
//                "pull",
//                "1",
//                "Just for test.".getBytes());
//
//            result = producer.send(msg);
//            System.out.println("id:" + result.getMsgId() +
//                " result:" + result.getSendStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
//            producer.shutdown();
        }
    }
}
