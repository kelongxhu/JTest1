package com.module.zookeeper.curator.leader;

import com.module.zookeeper.curator.ClientFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author kelong
 * @date 1/8/16
 */
public class LearderTest {
    private static final String MASTER_PATH = "/job_master_path";

    public static void main(String[] args)throws Exception{
        for (int i=0;i<10;i++){
            leader(String.valueOf(i));
        }
        new BufferedReader(new InputStreamReader(System.in)).readLine();
    }

    public static void leader(final String name){
        CuratorFramework client = ClientFactory.newClient();
        if (client.getState() != CuratorFrameworkState.STARTED) {
            client.start();
        }

        new LeaderSelector(client, MASTER_PATH, new LeaderSelectorListenerAdapter() {

            public void takeLeadership(CuratorFramework client) throws Exception {
               System.out.println("本机被选择为了Leader，获取执行任务权限."+name);
               Thread.sleep(1000*5);
            }
        }).start();
    }
}
