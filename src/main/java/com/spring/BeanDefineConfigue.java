package com.spring;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelong on 8/29/14.
 */
public class BeanDefineConfigue  implements ApplicationListener<ContextRefreshedEvent> {

    public BeanDefineConfigue(){}

    List<String> list = new ArrayList<String>();

    /**
     * 当一个ApplicationContext被初始化或刷新触发
     */
    public void onApplicationEvent(ContextRefreshedEvent event) {
        list.add("aa");
        System.out.println(list.size());
    }
}
