package com.module.zookeeper.curator.watch;

import com.module.zookeeper.curator.ClientFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @author ke.long
 * @since 2019/8/13 9:52
 */
public class CuratorWatcherClientDemo2 {

    private static CuratorFramework client = ClientFactory.newClient();

    public static void main(String[] args) throws Exception{
        client.start();
        //判断节点是否存在，然后删除掉
        Stat stat = client.checkExists().forPath("/config/nodecache2");
        if (stat != null) {
            Thread.sleep(1000);
            client.delete().deletingChildrenIfNeeded().forPath("/config/nodecache2");
        }

        //创建节点
        Thread.sleep(1000);
        client.create().withMode(CreateMode.PERSISTENT).forPath("/config/nodecache2", "nodecache  test".getBytes());

        //数据修改
        Thread.sleep(1000);
        client.setData().forPath("/config/nodecache2", "update".getBytes());

        //获取节点数据
        Thread.sleep(1000);
        byte[] data = client.getData().forPath("/config/nodecache2");
        System.out.println(new String(data));

        //删除节点
        Thread.sleep(1000);
        client.delete().deletingChildrenIfNeeded().forPath("/config/nodecache2");
    }
}
