package com.module.netty;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPoolMap;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.pool.SimpleChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author: wangqi
 * @description:
 * @date: Created in 2018-11-27 16:14
 */
@Slf4j
public class BackClientPool {

    private final EventLoopGroup group = new NioEventLoopGroup(8 * 4);
    private final Bootstrap bootstrap = new Bootstrap();
    private ChannelPoolMap<InetSocketAddress, SimpleChannelPool> poolMap;

    InetSocketAddress addr1 = new InetSocketAddress("127.0.0.1", 8080);
    InetSocketAddress addr2 = new InetSocketAddress("10.0.0.11", 8888);

    public static final BackClientPool INSTANCE = new BackClientPool();

    private BackClientPool() {
        bootstrap
                .group(group)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 500)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true);

        poolMap = new AbstractChannelPoolMap<InetSocketAddress, SimpleChannelPool>() {
            @Override
            protected SimpleChannelPool newPool(InetSocketAddress key) {
                return new FixedChannelPool(bootstrap.remoteAddress(key), new NettyChannelPoolHandler(), 2);
            }
        };
    }

    public void request(Channel serverChannel) throws InterruptedException {
        log.info("forward begin----");
        final SimpleChannelPool pool = poolMap.get(addr1);
        Future<Channel> f = pool.acquire().sync();
        f.addListener((FutureListener<Channel>) future -> {
            if (future.isSuccess()) {
                Channel clientChannel = future.getNow();
                try {

                    clientChannel.attr(Attributes.SERVER_CHANNEL.getAttributeKey()).set(serverChannel);
//                    clientChannel.attr(Attributes.CLIENT_POOL.getAttributeKey()).set(pool);
                    final String ECHO_REQ = "Hello Netty.$_";
                    clientChannel.writeAndFlush(ECHO_REQ);
                    log.info("forward end----");
                } catch (Throwable e) {
                    log.error("do future error", e);
                } finally {
                    pool.release(clientChannel);
                }
            }
        });
    }
}
