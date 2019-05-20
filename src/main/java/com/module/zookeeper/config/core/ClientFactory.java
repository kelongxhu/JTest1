package com.module.zookeeper.config.core;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ClientFactory {

    public static CuratorFramework newClient(String hosts) {
        //1 重试策略：初试时间为1s 重试10次
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        return CuratorFrameworkFactory.builder()
                .connectString(hosts)
                .sessionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();
    }
}
