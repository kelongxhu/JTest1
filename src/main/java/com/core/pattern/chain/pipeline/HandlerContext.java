package com.core.pattern.chain.pipeline;

/**
 * @author ke.long
 * @since 2019/6/27 15:20
 */
public class HandlerContext {
    private Handler handler;
    /***/
    private HandlerContext previous;
    /**后继节点*/
    private HandlerContext next;

    public HandlerContext(Handler handler) {
        this.handler = handler;
    }

    public void setPrevious(HandlerContext previous) {
        this.previous = previous;
    }

    public void setNext(HandlerContext ctx) {
        this.next = ctx;
    }

    public void doWork(Object msg) {
        handler.channelRead(next, msg);
    }

    public void doWorkReverse(Object msg){
        handler.write(previous,msg);
    }

}
