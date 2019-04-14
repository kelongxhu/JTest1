package com.module.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.concurrent.TimeUnit;

/**
 * Created by _think on 2017/4/17.
 */
//重载HystrixCommand 的getFallback方法实现逻辑
public class HelloWorldCommand2 extends HystrixCommand<String> {
    private final String name;
    public HelloWorldCommand2(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"))
                /* 配置依赖超时时间,500毫秒*/
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationThreadTimeoutInMilliseconds(500)));
        this.name = name;
    }
    @Override
    protected String getFallback() {
        return "exeucute Falled";
    }
    @Override
    protected String run() throws Exception {
        //sleep 1 秒,调用会超时，强制打断，抛出异常
        TimeUnit.MILLISECONDS.sleep(1000);
        return "Hello " + name +" thread:" + Thread.currentThread().getName();
    }
    public static void main(String[] args) throws Exception{
        HelloWorldCommand2 command = new HelloWorldCommand2("test-Fallback");
        String result = command.execute();
        System.out.println("=========result:"+result);
    }
}
