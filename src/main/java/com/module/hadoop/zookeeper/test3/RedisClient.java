package com.module.hadoop.zookeeper.test3;

import org.apache.log4j.Logger;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kelong
 * @date 12/17/14
 */
public class RedisClient extends ZooKeeperEntry {
    Logger LOG = Logger.getLogger(RedisClient.class);

    List<String> urlList = new ArrayList<String>();

    public RedisClient() {
        connect();
        if (zooKeeper != null) {
            watchNode(zooKeeper); // 观察 /registry 节点的所有子节点并更新 urlList 成员变量
        }
    }

    public void connect() {
        try {
            super.connect("127.0.0.1:2181");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }


    private void watchNode(final ZooKeeper zk) {
        try {
            LOG.info("monit node start.....");
            List<String> nodeList = zk.getChildren("/redis", new Watcher() {
                public void process(WatchedEvent event) {
                    if (event.getType() == Event.EventType.NodeChildrenChanged) {
                        watchNode(zk); // 若子节点有变化，则重新调用该方法（为了获取最新子节点中的数据）
                    }
                }
            });
            List<String> dataList = new ArrayList<String>();
            for (String node : nodeList) {
                byte[] data = zk.getData(root + "/" + node, false, null); // 获取 /registry 的子节点中的数据
                dataList.add(new String(data));
            }
            LOG.debug("node data: {}" + dataList);
            urlList = dataList; // 更新最新的 RMI 地址
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
    public void handle() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args)throws Exception{
        RedisClient client=new RedisClient();
        client.handle();
    }
}
