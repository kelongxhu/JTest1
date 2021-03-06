package com.core.pattern.chain.pipeline;

/**
 * @author ke.long
 * @since 2019/6/27 15:36
 */
public class TestHandler2 extends HandlerAdapter implements Handler {
    @Override
    public void channelRead(HandlerContext ctx, Object msg) {
        String result=(String)msg+"-handler2";//在字符串后面加特定字符串
        System.out.println(result);
        super.channelRead(ctx,result);
    }

    @Override
    public void write(HandlerContext ctx, Object msg) {
        String result=(String)msg+"-handler2";//在字符串后面加特定字符串
        System.out.println(result);
        super.write(ctx, result);
    }
}
