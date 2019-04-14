package com.module.hadoop.zookeeper.test3;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @author kelong
 * @date 12/17/14
 */
public class ZooKeeperEntry implements Watcher {
    private static final int SESSION_TIME = 2000000;
    protected CountDownLatch latch = new CountDownLatch(1);
    public ZooKeeper zooKeeper;
    String root = "/redis";

    public void connect(String hosts) throws Exception {
        zooKeeper = new ZooKeeper(hosts, SESSION_TIME, this);
        latch.await();
        Stat s = zooKeeper.exists(root, false);
        if (s == null) {
            zooKeeper.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
        }
    }

    public void process(WatchedEvent event) {
        if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
            latch.countDown();
        }
    }

    public void close() throws InterruptedException {
        zooKeeper.close();
    }
}
