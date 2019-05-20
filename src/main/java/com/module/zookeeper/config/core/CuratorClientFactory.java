package com.module.zookeeper.config.core;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author ke.long
 * @since 2019/5/5 15:12
 */
public class CuratorClientFactory implements ZkOperate {

    private CuratorFramework client;

    @Override
    public void connect(String hosts) throws IOException, InterruptedException {
        client = ClientFactory.newClient(hosts);
        client.start();
    }

    @Override
    public void write(String path, String value) throws Exception {
        Stat stat = client.checkExists().forPath(path);
        if (stat == null) {
            client.create().forPath(path, value.getBytes());
        }else {
            client.setData().forPath(path, value.getBytes());
        }
    }

    @Override
    public String createEphemeralNode(String path, String value, CreateMode createMode) throws Exception {
        return client.create().withProtection().withMode(createMode).forPath(path, value.getBytes());
    }

    @Override
    public boolean exists(String path) throws Exception {
        return client.checkExists().forPath(path)!=null;
    }

    @Override
    public String read(String path, Watcher watcher, Stat stat) throws Exception {
        byte[] data= client.getData().usingWatcher(watcher).forPath(path);
        return new String(data);
    }

    @Override
    public List<String> getRootChildren(String path) {
        try {
            return client.getChildren().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void deleteNode(String path) {
        try {
            client.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        CloseableUtils.closeQuietly(client);
    }
}
