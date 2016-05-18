package com.zookeeper.curator.watch;

import com.zookeeper.curator.ClientFactory;
import org.apache.curator.framework.CuratorFramework;

import java.util.List;

/**
 * @author kelong
 * @date 3/24/16
 */
public class Test {
    public static void main(String[] args)throws Exception{
        CuratorFramework client = ClientFactory.newClient();
        client.start();
        System.out.println(new String(client.getData().forPath("/chat")));
        List<String> child=client.getChildren().forPath("/chat");
        for (String c:child){
            System.out.println(new String(client.getData().forPath("/chat"+"/"+c)));
        }

    }
}
