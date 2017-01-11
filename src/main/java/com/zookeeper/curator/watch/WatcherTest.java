package com.zookeeper.curator.watch;

import com.zookeeper.curator.ClientFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

/**
 * @author kelong
 * @date 3/24/16
 */
public class WatcherTest {
    private static CuratorFramework client = ClientFactory.newClient();
    private static final String PATH = "/chat";

    public static void main(String[] args) {
        try {
            client.start();

//         client.create().forPath(PATH, "I love messi".getBytes());

            client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/chat/chat4","chat4".getBytes());

            //            byte[] bs = client.getData().forPath(PATH);
//            System.out.println("新建的节点，data为:" + new String(bs));

//            client.setData().forPath(PATH, "I love football".getBytes());

//            client.delete().forPath(PATH);
            Stat stat = client.checkExists().forPath(PATH);
            // Stat就是对zonde所有属性的一个映射， stat=null表示节点不存在！
            System.out.println(stat);


            client.getChildren().usingWatcher(new Watcher() {
                public void process(WatchedEvent event) {
                    System.out.println(event.getPath());
                    System.out.println(event.getType().getIntValue());
                    System.out.println("node is changed");
                }
            }).inBackground().forPath(PATH);

            Thread.sleep(Integer.MAX_VALUE);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(client);
        }
    }
}
