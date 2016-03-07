package com.redis;

import redis.clients.jedis.*;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kelong on 7/7/14.
 */
public class RedisShardPoolTest {

    static ShardedJedisPool pool;

    static {

        JedisPoolConfig config = new JedisPoolConfig();//Jedis池配置

        //        config.setMaxActive(500);//最大活动的对象个数

        config.setMaxIdle(1000 * 60);//对象最大空闲时间

        //        config.setMaxWait(1000 * 10);//获取对象时最大等待时间

        config.setTestOnBorrow(true);

        String hostA = "172.26.50.24";

        int portA = 6379;

        String hostB = "172.26.50.24";

        int portB = 6399;

        List<JedisShardInfo> jdsInfoList = new ArrayList<JedisShardInfo>(2);

        JedisShardInfo infoA = new JedisShardInfo(hostA, portA);

        //        infoA.setPassword("redis.360buy");

        JedisShardInfo infoB = new JedisShardInfo(hostB, portB);

        //        infoB.setPassword("redis.360buy");

        jdsInfoList.add(infoA);

        jdsInfoList.add(infoB);



        pool = new ShardedJedisPool(config, jdsInfoList, Hashing.MURMUR_HASH,

            Sharded.DEFAULT_KEY_TAG_PATTERN);

    }



    /**
     * @param args
     */

    public static void main(String[] args) {

        for (int i = 0; i < 10000; i++) {

            String key = generateKey();

            //key += "{aaa}";

            ShardedJedis jds = null;

            try {

                jds = pool.getResource();

//                                System.out.println(key+":"+jds.getShard(key).getClient().getHost()+":"+jds.getShard(key).getClient().getPort());
//
//                                System.out.println(jds.set(key,"1111111111111111111111111111111"+i));
                Collection<Jedis> jedises = jds.getAllShards();
                Iterator<Jedis> iterator=jedises.iterator();
                while (iterator.hasNext()){
                    Jedis jedis=iterator.next();
                    System.out.println(jedis.flushAll());
                }
            } catch (Exception e) {

                e.printStackTrace();

            } finally {

                pool.returnResource(jds);

            }

        }

    }



    private static int index = 1;

    public static String generateKey() {

        return String.valueOf("test" + Thread.currentThread().getId()) + "_" + (index++);

    }
}
