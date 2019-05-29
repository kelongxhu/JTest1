package com.module.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * @author: wangqi
 * @description:
 * @date: Created in 2018-11-27 18:39
 */
public class DefaultChannelWriteFinishListener implements ChannelFutureListener {

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
            Channel channel = future.channel();
            System.out.println("channelID:"+channel.id()+"write success!!!!!\n\n\n");
        }
    }
}
