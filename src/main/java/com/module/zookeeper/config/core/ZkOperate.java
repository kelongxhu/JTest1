package com.module.zookeeper.config.core;

import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * @author ke.long
 * @since 2019/5/5 15:14
 */
public interface ZkOperate {
    void connect(String hosts) throws IOException, InterruptedException;

    void write(String path, String value) throws Exception;

    String createEphemeralNode(String path, String value, CreateMode createMode) throws Exception;

    boolean exists(String path) throws Exception;

    String read(String path, Watcher watcher, Stat stat) throws Exception;

    List<String> getRootChildren(String path);

    void deleteNode(String path);

    void close() throws Exception;

    void subscribeChildChanges(String path, PathChildrenCacheListener listener)throws Exception;
}
