package com.util.guava.event;

import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: ke.long
 * @Date: 2019/11/12 11:38
 */
@Slf4j
public class EventBusFacade {
    private final static EventBus eventBus = new EventBus();

    public static void post(BaseEvent event) {
        eventBus.post(event);
    }
    public static void register(EventAdapter<? extends BaseEvent> handler) {
        if(handler == null){
            return ;
        }
        eventBus.register(handler);
        log.info("Registered eventAdapter class: {}", handler.getClass());
    }


    public static void unregister(EventAdapter<? extends BaseEvent> handler) {
        if(handler == null){
            return ;
        }
        eventBus.unregister(handler);
        log.info("Unregisted eventAdapter class: {}", handler.getClass());
    }

}
