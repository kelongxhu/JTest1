package com.module.btrace;
import java.util.Random;

/**
 * @author ke.long
 * @since 2019/8/23 10:54
 */
public class HelloWorld {
    public static void main(String[] args) throws Exception {
        while (true) {
            Random random = new Random();
            execute(random.nextInt(4000));
        }

    }
    public static Integer execute(int sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (Exception e) {
        }
        System.out.println("sleep time is=>"+sleepTime);
        return 0;
    }
}
