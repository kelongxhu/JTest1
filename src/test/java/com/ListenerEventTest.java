package com;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by kelong on 8/29/14.
 */
public class ListenerEventTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
//        BeanDefineConfigue beanDefineConfigue = (BeanDefineConfigue)context.getBean("testListener");
        //在ApplicationContext中发布一个 ApplicationEvent
//        context.publishEvent(beanDefineConfigue);
//        context.
        
    }
}
