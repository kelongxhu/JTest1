package com.module.netty;

import io.netty.util.AttributeKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dongdong.zhang
 * @date 2019-5-5 14:32:59
 * 1.0
 */
public enum Attributes {
    REQUEST("httpRequest"),
    EXCEPTION("exception"),
    RESPONSE("response"),
    KEEPALIVE("keepAlive"),
    SERVER_CHANNEL("serverChannel"),
    CLIENT_POOL("clientPool"),
    REQUEST_HOLDER("requestHolder"),
    REQUEST_CONTEXT_DATA("requestContextData"),
    FILTER_CHAIN("filterChain"),
    X_CONTENT("xContent"),
    MONITOR_CONTEXT("monitorContext");

    private String key;

    private static final Map<Attributes, AttributeKey> CACHE_ATTRIBUTE_KEYS = new ConcurrentHashMap<>();

    Attributes(String key) {
        this.key = key;
    }

    public <T> AttributeKey<T> getAttributeKey() {
        AttributeKey<T> attributeKey = CACHE_ATTRIBUTE_KEYS.get(this);
        if (attributeKey == null) {
            synchronized (this) {
                attributeKey = CACHE_ATTRIBUTE_KEYS.get(this);
                if (attributeKey == null) {
                    attributeKey = AttributeKey.newInstance(this.key);
                    CACHE_ATTRIBUTE_KEYS.put(this, attributeKey);
                }
            }
        }
        return attributeKey;
    }
}
