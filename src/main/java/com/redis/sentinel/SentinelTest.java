package com.redis.sentinel;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author kelong
 * @date 12/10/14
 */
public class SentinelTest {

    @Test
    public void testJedis() throws InterruptedException {

        Set<String> sentinels = new HashSet<String>();
        sentinels.add("127.0.0.1:26379");
        sentinels.add("127.0.0.1:26389");

        JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster1",
                sentinels);

        Jedis jedis = sentinelPool.getResource();

        System.out.println("current Host:"
                + sentinelPool.getCurrentHostMaster());

        String key = "a";

        String cacheData = jedis.get(key);

        if (cacheData == null) {
            jedis.del(key);
        }

        jedis.set(key, "aaa");// 写入

        System.out.println(jedis.get(key));// 读取

        System.out.println("current Host:"
                + sentinelPool.getCurrentHostMaster());// down掉master，观察slave是否被提升为master

        jedis.set(key, "bbb");// 测试新master的写入

        System.out.println(jedis.get(key));// 观察读取是否正常

        sentinelPool.destroy();
        jedis.close();

    }
}
