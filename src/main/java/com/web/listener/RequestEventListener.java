package com.web.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.web.context.support.ServletRequestHandledEvent;

/**
 * @author codethink
 * @date 12/14/16 4:52 PM
 */
public class RequestEventListener implements ApplicationListener<ServletRequestHandledEvent> {
    @Override
    public void onApplicationEvent(ServletRequestHandledEvent event) {
        System.out.println(event.getRequestUrl());
    }
}
