package com.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author kelong
 * @since 2017/7/11 17:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class RabbitmqConsumerTest{
    @Test
    public void consumer()throws Exception{
        System.out.println("start------------------");
        Thread.sleep(1000000000000L);
    }
}
