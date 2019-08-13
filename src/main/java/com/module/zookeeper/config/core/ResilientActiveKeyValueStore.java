package com.module.zookeeper.config.core;

import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * ZK读写
 *
 * @author liaoqiqi
 * @version 2014-7-7
 */
public class ResilientActiveKeyValueStore extends ConnectionWatcher implements ZkOperate {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ResilientActiveKeyValueStore.class);

    private static final Charset CHARSET = Charset.forName("UTF-8");

    // 最大重试次数
    public static final int MAX_RETRIES = 3;

    // 每次重试超时时间
    public static final int RETRY_PERIOD_SECONDS = 2;

    /**
     * @param debug
     */
    public ResilientActiveKeyValueStore(boolean debug) {
        super(debug);
    }

    @Override
    public void connect(String hosts) throws IOException, InterruptedException {
        super.connect(hosts);
    }

    /**
     * @param path
     * @param value
     *
     * @return void
     *
     * @throws InterruptedException
     * @throws KeeperException
     * @Description: 写PATH数据, 是持久化的
     * @author liaoqiqi
     * @date 2013-6-14
     */
    @Override
    public void write(String path, String value) throws InterruptedException, KeeperException {

        int retries = 0;
        while (true) {

            try {

                Stat stat = zk.exists(path, false);

                if (stat == null) {

                    zk.create(path, value.getBytes(CHARSET), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

                } else {

                    zk.setData(path, value.getBytes(CHARSET), stat.getVersion());
                }

                break;

            } catch (KeeperException.SessionExpiredException e) {

                throw e;

            } catch (KeeperException e) {

                LOGGER.warn("write connect lost... will retry " + retries + "\t" + e.toString());

                if (retries++ == MAX_RETRIES) {
                    throw e;
                }
                // sleep then retry
                int sec = RETRY_PERIOD_SECONDS * retries;
                LOGGER.warn("sleep " + sec);
                TimeUnit.SECONDS.sleep(sec);
            }
        }
    }

    /**
     * @param path
     * @param value
     *
     * @return void
     *
     * @throws InterruptedException
     * @throws KeeperException
     * @Description: 创建一个临时结点，如果原本存在，则不新建, 如果存在，则更新值
     * @author liaoqiqi
     * @date 2013-6-14
     */
    @Override
    public String createEphemeralNode(String path, String value, CreateMode createMode)
        throws InterruptedException, KeeperException {

        int retries = 0;
        while (true) {

            try {

                Stat stat = zk.exists(path, false);

                if (stat == null) {

                    return zk.create(path, value.getBytes(CHARSET), Ids.OPEN_ACL_UNSAFE, createMode);

                } else {

                    if (value != null) {
                        zk.setData(path, value.getBytes(CHARSET), stat.getVersion());
                    }
                }

                return path;

            } catch (KeeperException.SessionExpiredException e) {

                throw e;

            } catch (KeeperException e) {

                LOGGER.warn("createEphemeralNode connect lost... will retry " + retries + "\t" + e.toString());

                if (retries++ == MAX_RETRIES) {
                    throw e;
                }
                // sleep then retry
                int sec = RETRY_PERIOD_SECONDS * retries;
                LOGGER.warn("sleep " + sec);
                TimeUnit.SECONDS.sleep(sec);
            }
        }
    }

    /**
     * 判断是否存在
     *
     * @param path
     *
     * @return
     *
     * @throws InterruptedException
     * @throws KeeperException
     */
    @Override
    public boolean exists(String path) throws InterruptedException, KeeperException {

        int retries = 0;
        while (true) {

            try {

                Stat stat = zk.exists(path, false);

                if (stat == null) {

                    return false;

                } else {

                    return true;
                }

            } catch (KeeperException.SessionExpiredException e) {

                throw e;

            } catch (KeeperException e) {

                LOGGER.warn("exists connect lost... will retry " + retries + "\t" + e.toString());

                if (retries++ == MAX_RETRIES) {
                    LOGGER.error("connect final failed");
                    throw e;
                }

                // sleep then retry
                int sec = RETRY_PERIOD_SECONDS * retries;
                LOGGER.warn("sleep " + sec);
                TimeUnit.SECONDS.sleep(sec);
            }
        }
    }

    /**
     * @param path
     * @param watcher
     *
     * @return String
     *
     * @throws InterruptedException
     * @throws KeeperException
     * @Description: 读数据
     * @author liaoqiqi
     * @date 2013-6-14
     */
    @Override
    public String read(String path, Watcher watcher, Stat stat) throws InterruptedException, KeeperException {

        byte[] data = zk.getData(path, watcher, stat);
        return new String(data, CHARSET);
    }

    /**
     * @return List<String>
     *
     * @Description: 获取子孩子数据
     * @author liaoqiqi
     * @date 2013-6-14
     */
    @Override
    public List<String> getRootChildren(String path) {

        List<String> children = new ArrayList<String>();
        try {
            children = zk.getChildren(path, false);
        } catch (KeeperException e) {
            LOGGER.error(e.toString());
        } catch (InterruptedException e) {
            LOGGER.error(e.toString());
        }

        return children;
    }

    /**
     * @param path
     *
     * @return void
     *
     * @Description: 删除结点
     * @author liaoqiqi
     * @date 2013-6-17
     */
    @Override
    public void deleteNode(String path) {

        try {

            zk.delete(path, -1);

        } catch (KeeperException.NoNodeException e) {

            LOGGER.error("cannot delete path: " + path, e);

        } catch (InterruptedException e) {

            LOGGER.warn(e.toString());

        } catch (KeeperException e) {

            LOGGER.error("cannot delete path: " + path, e);
        }
    }

    @Override
    public void close() throws InterruptedException {
        super.close();
    }

    @Override
    public void subscribeChildChanges(String path, PathChildrenCacheListener listener) throws Exception {

    }
}
