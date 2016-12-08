package com.hadoop.zookeeper.test3;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

import java.util.List;

/**
 * redis心跳检测
 *
 * @author kelong
 * @date 12/17/14
 */
public class RedisServer extends ZooKeeperEntry {

    Logger LOG = Logger.getLogger(RedisServer.class);

    public RedisServer() {
        connect();
    }

    public void connect() {
        try {
            super.connect("127.0.0.1:2181");
            init();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void init() throws Exception {
        LOG.info("初始化配置redis节点....");
        List<RedisConfig> configs = RedisConfig.getConfig();
        for (RedisConfig config : configs) {
            if (config.alived()) {
                String createdPath = zooKeeper.create(root + "/element" + config.getPort(), config.getBytes(),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        }
        Hit.getInstance().start();
    }

    public static void main(String[] args) {
        new RedisServer();
    }


}
