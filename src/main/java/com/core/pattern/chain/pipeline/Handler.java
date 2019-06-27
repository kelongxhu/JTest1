package com.core.pattern.chain.pipeline;

/**
 * @author ke.long
 * @since 2019/6/27 15:22
 */
public interface Handler {
    void channelRead(HandlerContext ctx, Object msg);
    void write(HandlerContext ctx, Object msg);
}
