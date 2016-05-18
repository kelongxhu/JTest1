package com.hadoop.zookeeper.test2;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;

/**
 *
 * @description 使用ZooKeeper实现分布式锁
 * @author zhangchaoyang
 * @date 2014-6-22
 */
public class ZooKeeperLock {

    private static Logger logger = Logger.getLogger(ZooKeeperLock.class);

    private static ZooKeeper zk = null;
    private static final int TIMEOUT = 1000 * 60;
    private static String connStr = null;

    public static void setServerPath(String path) {
        connStr = path + "/app/bqas/lock";
        logger.info("ZooKeeperLock zookeeper node:" + connStr);
    }

    public static boolean getLock(String lockname) throws KeeperException,
            InterruptedException, IOException {
        connect(connStr, TIMEOUT);
        if (lockname.contains("-")) {
            throw new RuntimeException("锁名称不能包含'-'");
        }
        boolean lock = false;
        String path = zk.create("/" + lockname + "-", new byte[0],
                Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        int selfIndex = getIndex(path);
        List<String> children = zk.getChildren("/", false);
        int min = getMinIndex(children);
        if (min == selfIndex) {
            lock = true;
        }

        return lock;
    }

    public static void releaseLock(String lockname)
            throws InterruptedException, KeeperException {
        disconnect();
    }

    private static int getIndex(String str) {
        int index = -1;
        int pos = str.lastIndexOf("-");
        if (pos >= 0) {
            try {
                index = Integer.parseInt(str.substring(pos + 1));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return index;
    }

    private static int getMinIndex(List<String> list) {
        int min = Integer.MAX_VALUE;
        for (String ele : list) {
            int index = getIndex(ele);
            if (index < 0) {
                throw new RuntimeException("SEQUENTIAL节点名中不包含数字：" + ele);
            }
            if (index < min) {
                min = index;
            }
        }
        return min;
    }

    private static void waitUntilConnected(CountDownLatch connectedLatch) {
        if (States.CONNECTING == zk.getState()) {
            try {
                connectedLatch.await();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public static boolean connect(String hostPath, int sessionTimeout) {
        if (zk == null || zk.getState() == States.CLOSED) {
            try {
                CountDownLatch connectedLatch = new CountDownLatch(1);
                Watcher watcher = new ConnectedWatcher(connectedLatch);
                zk = new ZooKeeper(hostPath, sessionTimeout, watcher);
                waitUntilConnected(connectedLatch);
            } catch (Exception e) {
                logger.error("Connect to Zookeeper failed:", e);
                return false;
            }
        }
        return true;
    }

    public static boolean disconnect() {
        if (zk != null) {
            if (States.CLOSED != zk.getState()) {
                try {
                    zk.close();
                } catch (InterruptedException e) {
                    logger.error("Disconnect from Zookeeper failed:", e);
                    return false;
                }
            }
        }
        return true;
    }

    static class ConnectedWatcher implements Watcher {

        private CountDownLatch connectedLatch;

        ConnectedWatcher(CountDownLatch connectedLatch) {
            this.connectedLatch = connectedLatch;
        }

        @Override
        public void process(WatchedEvent event) {
            // 事件状态为SyncConnected时，说明与服务端的连接已建立好
            if (event.getState() == KeeperState.SyncConnected) {
                connectedLatch.countDown();
            }
        }
    }

    public static void main(String[] args) {
        String lockname = "writeHitCount2DBlock";
        System.out.println("begin to run.");
        ZooKeeperLock.setServerPath("192.168.119.96:2181");
        try {
            boolean havelock = ZooKeeperLock.getLock(lockname);
            if (havelock) {
                Date date = new Date();
                System.out
                        .println("I got the lock,and I will write DB!" + date);
                Thread.sleep(1000);// 休息一段时间之后再释放锁
            }
            System.out.println("Job done, I will release the lock.");
            ZooKeeperLock.releaseLock(lockname);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
