package com.job;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;

/**
 * @author codethink
 * @date 1/10/17 4:38 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class ElasticJob {

    @Test
    public void test()throws Exception{
        System.out.println("=====test.");
        CountDownLatch latch=new CountDownLatch(1);
        latch.await();
    }
}
