package com.module.zookeeper.config.server;

import com.alibaba.fastjson.JSON;
import com.module.zookeeper.config.core.CuratorClientFactory;
import com.module.zookeeper.config.core.ZkClient;

/**
 * @author ke.long
 * @since 2019/8/13 17:38
 */
public class WatcherTest {
    public static void main(String[] args) throws Exception {
        ZkClient zk = new CuratorClientFactory();
        zk.connect("192.168.56.101:2181");
        zk.subscribeChildChanges("/config",(cf, event)->{
            switch (event.getType()) {
                //初始化会触发这个事件
                case INITIALIZED:
                    System.out.println("子类缓存系统初始化完成,路径:" +event.getData()+event.getType()+ JSON.toJSONString(event.getInitialData()));
                    break;
                case CHILD_ADDED:
                    System.out.println("添加子节点,路径:" + event.getData().getPath()+"更改数据:"+new String(event.getData().getData())+"节点状态:"+event.getData().getStat());
                    break;
                case CHILD_UPDATED:
                    System.out.println("更新子节点,路径:" + event.getData().getPath()+"更改数据:"+new String(event.getData().getData())+"节点状态:"+event.getData().getStat());
                    break;
                case CHILD_REMOVED:
                    System.out.println("删除子节点,路径:" + event.getData().getPath()+"更改数据:"+new String(event.getData().getData())+"节点状态:"+event.getData().getStat());
                    break;
                default:
                    break;
            }
        });
        Thread.sleep(Integer.MAX_VALUE);
    }
}
