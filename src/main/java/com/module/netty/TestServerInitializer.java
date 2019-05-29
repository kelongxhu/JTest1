package com.module.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Date: 2018/10/16 16:00
 * @Description: 初始化器
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    //连接被创建之后会立刻执行 initChannel 回调方法
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //pipeline 是 根据业务请求完成处理
        ChannelPipeline pipeline = ch.pipeline();

        /**
         * 在最后添加 自定义的若干个处理器
         * HttpServerCodec 是 HttpRequestDecoder 和 HttpResponseEncoder 的组合 ，他可以得到更为简化的http端的实现。
         * 那么这两个是什么意思呢，就是请求从客户端发向给服务器端之后呢，会做一个解码。所谓解码就是把这些
         请求里的信息都提取出来，完成相应的编解码这样的一个动作。
         * ①、HttpRequestDecoder
         * ②、HttpResponseEncoder 是http响应的编码，就是向客户端输送响应的时候，要把这些响应做一个编码的工作。
         * 那么这些功能Netty底层提供了一些基础，帮助更好的完成这个功能。
         * httpServerCodec 相当于把 HttpRequestDecoder 和 HttpResponseEncoder 这两个主键合二为一了，
         通过一个主键就能完成相应的工作。
         *
         */
        pipeline.addLast("httpServerCodec ", new HttpServerCodec());
        //添加自定义的处理器
        pipeline.addLast("testHttpServerHandler", new TestHttpServerHandler());
    }
}
