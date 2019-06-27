package com.module.netty;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Date: 2018/10/16 15:35
 * @Description: 服务器启动代码
 */
public class TestServer {

    public static void main(String[] args) throws Exception {

        //定义两个事件循环组 （两个线程组）
        //NioEventLoopGroup()可以理解为死循环。不断的接受客户端发起的连接，连接进来之后对连接进行处理，紧接着循环继续运行
        // boosGroup --》线程组不断的从客户端那边接受连接，但是不对连接做任何的处理，直接传给worker
        // workerGroup --》线程组接收到boosGroup传过来的连接，真正的完成对连接的处理。例如获取连接的参数，进行实际的业务处理，把结果返回给客户端
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //ServerBootstrap 服务端启动，他实际上是一个比较方便的轻松的启动服务端的类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // childHandler 子处理器 自己编写的处理器，请求到来时，有我们编写的处理器进行处理。
            serverBootstrap.group(boosGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new TestServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            //优雅的关闭
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

