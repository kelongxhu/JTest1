package com.module.zookeeper.curator.watch;

import com.module.zookeeper.curator.ClientFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @author ke.long
 * @since 2019/8/13 9:48
 */
public class CuratorWatcherDemo2 {

    private static CuratorFramework client = ClientFactory.newClient();

    public static void main(String[] args) throws Exception {

        //3、启动连接
        client.start();
        //4、建立一个Cache缓存 ,第三个参数是 dataIsCompressed 是否进行数据压缩  ,需要配置为true
        //如果第三个参数设置为 false,则不接受节点变更后的数据
        final PathChildrenCache cache = new PathChildrenCache(client, "/config", true);

        //5、设定监听的模式 ,异步初始化，初始化完成触发事件 PathChildrenCacheEvent.Type.INITIALIZED
        cache.start(StartMode.POST_INITIALIZED_EVENT);

        //创建监听器
        cache.getListenable().addListener(new PathChildrenCacheListener() {

            @Override
            public void childEvent(CuratorFramework cf, PathChildrenCacheEvent event) throws Exception {
                ChildData data = event.getData();
                if (data == null) {
                    System.out.println("=============:" + event.getType());
                    return;
                }
                System.out.println("=============:" + event.getType());
                System.out.println("路径\t" + data.getPath());
                System.out.println("更改数据\t" + new String(data.getData()));
                System.out.println("节点状态\t" + data.getStat());
                System.out.println(event.getType());
                switch (event.getType()) {
                    //初始化会触发这个事件
                    case INITIALIZED:
                        System.out.println("子类缓存系统初始化完成");
                        break;
                    case CHILD_ADDED:
                        System.out.println("添加子节点");
                        break;
                    case CHILD_UPDATED:

                        System.out.println("更新子节点");
                        break;
                    case CHILD_REMOVED:
                        System.out.println("删除子节点");
                        break;
                    default:
                        break;
                }

                System.out.println("----------------------------------");
            }
        });
        //
        Thread.sleep(Integer.MAX_VALUE);
    }
}
