package com.module.job.quzrtz.hello;

import org.springframework.stereotype.Component;

/**
 * @author codethink
 * @date 12/21/16 4:48 PM
 */
@Component("sayHello")
public class SayHello {
    void say(String str) {
        System.out.println("say:" + str);
    }
}
