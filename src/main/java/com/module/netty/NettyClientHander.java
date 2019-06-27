package com.module.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by YuQi on 2017/7/31.
 */
public class NettyClientHander extends ChannelInboundHandlerAdapter {
    static AtomicInteger count = new AtomicInteger(1);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(count.getAndIncrement() + ":" + msg+"forward channelID:"+ctx.channel().id());

            //构造响应的字符串
            ByteBuf content = Unpooled.copiedBuffer("helloooo", CharsetUtil.UTF_8);

            //构造响应
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

            //设置 Response 头信息
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text_plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());


            AttributeKey<Channel> channelAttributeKey = Attributes.SERVER_CHANNEL.getAttributeKey();
             Channel serverChannel = ctx.channel().attr(channelAttributeKey).get();

            //返回给客户端的response对象
            ChannelFuture future=serverChannel.writeAndFlush(response);
             future.addListener(new DefaultChannelWriteFinishListener());


    }
}
