package com.core.pattern.chain.pipeline;

/**
 * @author ke.long
 * @since 2019/6/27 15:30
 */
public class MyPipeline {
    private HandlerContext head;//链表头
    private HandlerContext tail;
    private int length = 0;

    public MyPipeline() {
    }

    /**
     * 链表的头部插入一个handler
     * @param handler
     */
    public void addFirst(Handler handler) {
        if (head == null) {
            HandlerContext ctx = new HandlerContext(handler);
            this.head = ctx;
            this.tail = ctx;
            length++;
        } else {
            HandlerContext ctx = new HandlerContext(handler);
            head.setPrevious(ctx);
            HandlerContext tmp = head;
            head = ctx;
            head.setNext(tmp);
            length++;
        }
    }

    public void Request(Object msg) {//封装了外部调用接口
        head.doWork(msg);
    }

    public void Response(Object msg){
        tail.doWorkReverse(msg);
    }
}
