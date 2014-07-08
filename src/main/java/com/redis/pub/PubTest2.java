package com.redis.pub;

import redis.clients.jedis.Jedis;

/**
 * Created by kelong on 7/7/14.
 */
public class PubTest2 {
    public void pubInfo() {
        Jedis jr = null;

        try {

            jr = new Jedis("172.26.35.130", 6389);
            final MyListener listener = new MyListener();
            //可以订阅多个频道
            //订阅得到信息在lister的onMessage(...)方法中进行处理
            //jedis.subscribe(listener, "foo", "watson");

            //也用数组的方式设置多个频道
            //jedis.subscribe(listener, new String[]{"hello_foo","hello_test"});

            //这里启动了订阅监听，线程将在这里被阻塞
            //订阅得到信息在lister的onPMessage(...)方法中进行处理
            jr.subscribe(listener, new String[]{"hello_foo"});//使用模式匹配的方式设置频

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (jr != null) {

                jr.disconnect();

            }

        }
    }

    public static void main(String[] args) {
        PubTest2 pt=new PubTest2();
        pt.pubInfo();

    }
}
