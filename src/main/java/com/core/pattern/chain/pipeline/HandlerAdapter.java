package com.core.pattern.chain.pipeline;

/**
 * @author ke.long
 * @since 2019/6/27 17:31
 */
public class HandlerAdapter implements Handler {
    @Override
    public void channelRead(HandlerContext ctx, Object msg) {
        if (ctx != null) {
            ctx.doWork(msg);
        }
    }

    @Override
    public void write(HandlerContext ctx, Object msg) {
        if (ctx != null) {
            ctx.doWorkReverse(msg);
        }
    }
}
