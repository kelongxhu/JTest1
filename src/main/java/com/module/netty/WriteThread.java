package com.module.netty;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

public class WriteThread {
    private static final AttributeKey<Channel> ATTR_REQ_STATE = AttributeKey.newInstance("_accesslog_requeststate");



}
