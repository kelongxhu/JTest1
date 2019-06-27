package com.core.pattern.chain.pipeline;

/**
 * @author ke.long
 * @since 2019/6/27 15:38
 */
public class Main {
    public static void main(String[] args){
        MyPipeline pipeline=new MyPipeline();
        pipeline.addFirst(new TestHandler2());//添加handler1
        pipeline.addFirst(new TestHandler1());//添加handler2
        for(int i=0;i<10;i++){//提交多个任务
            //pipeline.Request("hello"+i);
            pipeline.Response("end"+i);
        }
    }
}
