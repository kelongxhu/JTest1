package com.module.netty;



import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @Date: 2018/10/16 16:08
 * @Description: 定义自己的处理器
 * Inbound : 对进来请求的处理
 * Outbound : 对返回响应的处理
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * Netty本身并不是根据servlet规范进行的，它在处理的时候其实有很多地方都是需要手动处理的；
     * curl 工具是一个完整的网络命令工具，当它请求完之后，紧接着这个请求就会结束，结束程序就会关闭；服务器端就会搜到
     * 响应的通知。
     * 1、对于浏览器来说，却不是这样的。对于http协议，它是基于请求、响应模式的。无状态的协议。对于Metty来说，当请求来的时候，
     * 它本身是监听的，监听TCP的端口号；http协议底层是基于socket,TCP这种带连接的协议，他的最底层最底层是serversocket，
     * 依然是它进行死循环。
     * 2、对于应用来说，当获取客户端的请求之后，首先要判断一下，请求是基于http1.1 还是1.0协议。
     * ①、如果是1.1 ，那么它会有一个时间keepline.比如说是 3s ，那么3s到了之后，客户端没有再发出新的请求，那么服务器端
     * 会把连接给主动的关闭掉。
     * ②、如果是1.0 ，那就是一个短连接的协议，请求端发送消息过来之后，服务器端就把这个连接给关闭掉。
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */

    //channelRead0 ---》 读取客户端发送过来真正的请求，并且向客户端返回响应
    //相当于 messageReceived
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        //输出的是远程连接的IP地址
        System.out.println(ctx.channel().remoteAddress()+":"+msg.getClass()+";channelId:"+ctx.channel().id());
        if (msg instanceof HttpRequest) {

            //强制的向下类型转换
            HttpRequest httpRequest = (HttpRequest) msg;

            System.out.println("请求方法名：" + httpRequest.method().name());

            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求favicon.ico");
                return;
            }


            BackClientPool.INSTANCE.request(ctx.channel());

//
//            //构造响应的字符串
//            ByteBuf content = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
//
//            //构造响应
//            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
//
//            //设置 Response 头信息
//            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text_plain");
//            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
//
//            //返回给客户端的response对象
//            ctx.writeAndFlush(response);
//
//            //调用close方法
//            ctx.channel().close();
        }
    }

    /**
     * 通道添加
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler added");
        super.handlerAdded(ctx);
    }

    /**
     * 通道注册
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel registered");
        super.channelRegistered(ctx);
    }

    /**
     * 通道处于活动状态
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active");
        super.channelActive(ctx);
    }

    /**
     * 通道不活跃状态
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel Inavtive");
        super.channelInactive(ctx);
    }

    /**
     * 通道取消注册
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel unregistered");
        super.channelUnregistered(ctx);
    }
}