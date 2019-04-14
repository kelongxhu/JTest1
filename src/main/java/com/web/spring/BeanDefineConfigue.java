package com.web.spring;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author
 */
@Configuration
@Service
public class BeanDefineConfigue  implements ApplicationListener<ContextRefreshedEvent> {

    public BeanDefineConfigue(){}

    List<String> list = new ArrayList<String>();

    /**
     * 当一个ApplicationContext被初始化或刷新触发
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        list.add("aa");
        System.out.println(list.size());
        Map<String, BaseInterface> baseInterfaceBeans = event.getApplicationContext().getBeansOfType(BaseInterface.class);
        baseInterfaceBeans.values().stream().forEach(l->{
            try {
                Method init=l.getClass().getMethod("init");
                init.invoke(l);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });

    }
}
