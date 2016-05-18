package com.hadoop.zookeeper.test3;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * @author kelong
 * @date 12/17/14
 */
public class Hit extends ZooKeeperEntry implements Runnable {

    Logger LOG = Logger.getLogger(Hit.class);

    public static Hit hit = new Hit();

    private Hit() {
        try {
            super.connect("127.0.0.1:2181");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static Hit getInstance() {
        return hit;
    }

    public void run() {
        while (true) {
            try {
                List<RedisConfig> configs = RedisConfig.getConfig();
                for (RedisConfig config : configs) {
                    Stat s = zooKeeper.exists(root + "/element" + config.getPort(), false);
                    if (config.alived()) {
                        if (s == null) {
                            zooKeeper.create(root+"/element"+config.getPort(), config.getBytes(),
                                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                            LOG.info("loading node:"+config.toString());
                        }
                    }else{
                        if(s!=null){
                            LOG.info("down:" + config.toString());
                            zooKeeper.delete(root+"/element"+config.getPort(), 0);
                        }
                    }
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    public void start() {
        new Thread(new Hit()).start();
    }
}
