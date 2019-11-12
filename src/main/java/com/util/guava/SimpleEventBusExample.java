package com.util.guava;

import com.google.common.eventbus.EventBus;

/**
 * @Author: ke.long
 * @Date: 2019/11/12 11:05
 */
public class SimpleEventBusExample {
    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();
        //注册Listener
        eventBus.register(new SimpleListener());
        eventBus.register(new SimpleListener2());
        System.out.println("post the simple event.");
        //向订阅者发送消息
        SimpleEvent event=new SimpleEvent();
        event.setMessage("hhahahaha");
        eventBus.post(event);


        SimpleEvent2 event2=new SimpleEvent2();
        event2.setMessage("wawawa");
        eventBus.post(event2);
    }
}
